package fr.improve.struts.taglib.layout.field.ajax.select;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.field.SelectTag;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * allows to create double-combo linked list with Ajax.
 * @author nsoule
 *
 */
public class SelectDependentTag extends SelectTag {
	
	/**
	 * The second selection list name (target).
	 */
	protected String targetName;
    /**
     * The struts action instance to which we are attached.
     */
	protected String fillDropDownAction;
	
	/**
	 * Indicate if we have double-combo linked list.
	 */
	protected String doubleCombo;
		
	public static final String LOADED = "fr.improve.struts.taglib.layout.field.ajax.select.SelectDependentTag.LOADED";

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.field.SelectTag#doBeforeValue()
	 */
	protected boolean doBeforeValue() throws JspException {
		
		if (doubleCombo != null && doubleCombo.equals("true")) {		
			loadScript();
		}		
		return super.doBeforeValue();
	}
		
	/**
	 * Load the required Javascript code.
	 */
	protected void loadScript() throws JspException {
		
		if (pageContext.getRequest().getAttribute(LOADED)==null) {
			TagUtils.write(pageContext, "<script src=\"");
			TagUtils.write(pageContext, LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
			TagUtils.write(pageContext, "/selectDependent.js\"></script>");
			pageContext.getRequest().setAttribute(LOADED, "");
		}		
	}

	/**
	 * @return Returns the fillDropDownAction.
	 */
	public String getFillDropDownAction() {
		return fillDropDownAction;
	}

	/**
	 * @param fillDropDownAction The fillDropDownAction to set.
	 */
	public void setFillDropDownAction(String fillDropDownAction) {
		this.fillDropDownAction = fillDropDownAction;
	}


	/**
	 * @return Returns the targetName.
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param targetName The targetName to set.
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	/**
	 * @return Returns the doubleCombo.
	 */
	public String getDoubleCombo() {
		return doubleCombo;
	}


	/**
	 * @param doubleCombo The doubleCombo to set.
	 */
	public void setDoubleCombo(String doubleCombo) {
		this.doubleCombo = doubleCombo;
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.field.AbstractFieldTag#initDynamicValues()
	 */
	protected void initDynamicValues() {	
		super.initDynamicValues();
		
		String url = null;

		try {
			url = LayoutUtils.computeURL(pageContext, null, null, null,
					                     this.getFillDropDownAction(), null, null, 
					                     null, false, "");
		}
		catch (JspException e) {
			TagUtils.saveException(pageContext, e);
			throw new RuntimeException(e.getMessage());
		}
				
		if (doubleCombo != null && doubleCombo.equals("true")) {			
			onchange =(onchange != null ? (onchange +";") : "") 
			          + "updateDropDownList(this,'"+ getName() +"', '" 
			          + getTargetName() + "', '" + url +  "' )";		
		}		
	}

	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.field.SelectTag#reset()
	 */
	protected void reset() {
		super.reset();		
		onchange = null;		
		onkeypress = null;		
		targetName = null;
		fillDropDownAction = null;
		doubleCombo = null;
	}
	

}
