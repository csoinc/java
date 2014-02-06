package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
import fr.improve.struts.taglib.layout.policy.AbstractPolicy;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.FormUtils;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Base class for the submit, cancel and image action tags.
 * 
 * The action is display or not in function of the display mode, which can be N (not display) or D (display)
 * The action can be nested in a <layout:formActions> tag or a <layout:line> tag.
 * 
 * @author: Jean-Noël Ribette, F. BELLINGARD
 */
public abstract class ActionTag extends BaseHandlerTag implements LayoutEventListener {
    
	protected String property;
	protected boolean display = true;
	protected String valign;
	private String mode;
	protected String policy = null;
	protected boolean cell = false;
	
	protected String jspMode;
	protected boolean jspDisabled;
	protected String jspOnclick;
  
  public final int doStartTag() throws JspException {
    ParentFinder.registerTag(pageContext, this);    
    initDynamicValues();
    return doStartLayoutTag();
  }   
  
  public final int doEndTag() throws JspException {
    try {
      return doEndLayoutTag();
    } finally {      
      reset();
      ParentFinder.deregisterTag(pageContext);
    }
  }

	/**
	 * End the tag.
	 */
	public int doEndLayoutTag() throws JspException {
		// do nothing if the tag is no displayed
		if (!display) {
			display = true;
			return EVAL_PAGE;
		}
		
		if (cell) {
			cell = false;
			return EVAL_PAGE;
		}

		// end the Struts tag.
		int ret = tag.doEndTag();

		// end the layout
		StringBuffer lc_buffer = new StringBuffer("");
		endActionLayout(lc_buffer);
		new EndLayoutEvent(this, lc_buffer.toString()).send();		
		return ret;
	}

	/**
	 * Start the tag
	 */
	public int doStartLayoutTag() throws JspException {
        // if the action is about to be displayed, check the authorization first
        if (policy != null) {
            Skin lc_currentSkin = LayoutUtils.getSkin(getPageContext().getSession());
            AbstractPolicy lc_policy = lc_currentSkin.getPolicy();
            switch (lc_policy.getAuthorizedDisplayMode(getPolicy(), getReqCode(), getProperty(), getPageContext())) {
            case AbstractPolicy.MODE_EDIT:
                break;
            case AbstractPolicy.MODE_NODISPLAY:
                display = false;
                break;
            case AbstractPolicy.MODE_DISABLED:
               	display = true;
               	setDisabled(true);
               	break;
            case AbstractPolicy.MODE_CELL:
               	cell = true;
               break;
            default:
                throw new IllegalStateException(lc_policy.getClass().getName() + " returns an illegal value");                
            }           
        }
        
		// do nothing if the action is not displayed in this mode.
		if (!display) {
			return SKIP_BODY;
		}
		
		// just display an empty column if MODE_CELL
		if (cell) {
			return doCellMode();
		}
        
		// start the layout.		
		StringBuffer lc_buffer = new StringBuffer("");
		beginActionLayout(lc_buffer);
		new StartLayoutEvent(this, lc_buffer.toString()).send();

		// start the Struts tag.
		copyProperties();
		String onclick = null;
		if (reqCode != null) {
			onclick = getRequestCode();
		}
		if (onclick!=null) {
		String previousOnclick = getOnclick();
		if (previousOnclick != null)
			onclick += ";" + previousOnclick;

		tag.setOnclick(onclick);
		}
		return tag.doStartTag();
	}
	
	/**
	 * Display an empty column in cell mode.
	 */
	protected int doCellMode() throws JspException {
		new StartLayoutEvent(this, null).send();
		TagUtils.write(pageContext, "<th colspan=\"");
		TagUtils.write(pageContext, String.valueOf(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber()));
		if (getStyleClass()!=null) {
			TagUtils.write(pageContext, "\" class=\"");
			TagUtils.write(pageContext, getStyleClass());
		}
		TagUtils.write(pageContext, "\">&nbsp;</th>");
		new EndLayoutEvent(this, null).send();
		return SKIP_BODY;
	}
	
	
	/**
	 * Set in wich form modes the action should be displayed or not.
	 * The format form in_mode is X,Y,Z where allowed values are D (Displayed) and N (not displayed) in the same order as the input field tags.
	 */
	public void setMode(String in_mode) {
		mode = in_mode;
	}

	public String getProperty() {
		return property;
	}
	
	/**
	 * Initialize dynamic values.
	 */
	protected void initDynamicValues() {
		// Evaluate mode as an EL.
		jspMode = mode;
		mode = Expression.evaluate(mode, pageContext);
		
		// Determinate if the action should be displaued or not.
		jspDisabled = getDisabled();
		if (mode!=null) {
			int lc_visible = FormUtils.computeVisibilityMode(pageContext, mode);
			switch(lc_visible) {
				case AbstractModeFieldTag.MODE_EDIT:
					display = true;
					break;
				case AbstractModeFieldTag.MODE_NODISPLAY:
					display = false;
					break;
				case AbstractModeFieldTag.MODE_DISABLED:
					display = true;
					setDisabled(true);
					break;
				case AbstractModeFieldTag.MODE_CELL:
					cell = true;
					break;
			}
		}
		
		// Evaluate onlick as an EL
		jspOnclick = getOnclick();
		setOnclick(Expression.evaluate(jspOnclick, pageContext));
	}
	
	/**
	 * Reset dynamic values.
	 */
	protected void reset() {
		mode = jspMode;
		jspMode = null;
		setDisabled(jspDisabled);
		display = true;
		cell = false;
		
		setOnclick(jspOnclick);
		jspOnclick = null;
	}
	
	public void release() {
		super.release();
		tag.release();
        policy = null;
		property = null;
		valign = null;
		mode = null;
	}

	/**
	 * Prepare to display the action.
	 */
	protected void beginActionLayout(StringBuffer in_buffer) throws JspException {	
		in_buffer.append("<td>&nbsp;</td><td");
		if (valign!=null) {
			in_buffer.append(" valign=\"");
			in_buffer.append(valign);
			in_buffer.append("\"");	
		}
		in_buffer.append(">");
	}

	/**
	 * End the display of the action.
	 */
	protected void endActionLayout(StringBuffer in_buffer) {
		in_buffer.append("</td>");
	}

	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * Sets the valign.
	 * @param valign The valign to set
	 */
	public void setValign(String valign) {
		this.valign = valign;
	}

	/**
	 * @see fr.improve.struts.taglib.layout.event.LayoutEventListener#processEndLayoutEvent(fr.improve.struts.taglib.layout.event.EndLayoutEvent)
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}

	/**
	 * @see fr.improve.struts.taglib.layout.event.LayoutEventListener#processStartLayoutEvent(fr.improve.struts.taglib.layout.event.StartLayoutEvent)
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {	
		return Boolean.FALSE;
	}
	
	/**
	 * @return Returns the policy.
	 */
	public String getPolicy()
	{
		return policy;
	}
	/**
	 * @param policy The policy to set.
	 */
	public void setPolicy(String policy)
	{
		this.policy = policy;
	}
	
	protected void copyProperties() throws JspException {
		LayoutUtils.copyProperties(tag, this);
	}

}