package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.PanelInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Render a panel element
 * This tag can be used as is or subclassed to insert a specific content.
 * The body/content of the panel must be the content of a table element (ie begin with a &lt;tr&gt; element)
 *
 * Method calls are:<br>
 * <li>doStartPanel()</li>
 * <li>doPrintTitle()</li>
 * <li>doBeforeBody()</li>
 * <li> - output of the tag content - </li>
 * <li>doAfterBody()</li>
 * @author: Jean-Noël Ribette
 */
public class PanelTag extends LabelledTag implements LayoutEventListener {	
    /**
     * Log object.
     */
    private static Log LOG = LogFactory.getLog(PanelTag.class);
        
	protected static PanelInterface defaultPanel = null;
	protected PanelInterface panel = null;
		
	protected int cols = 2;
	protected String width;
	protected String height;	
	protected String align = null; // "center";
	public final static String CENTER = "center";
	public final static String LEFT = "left";
	public final static String RIGHT = "rigth";

	// display modes
	protected short editMode = 2;
	protected short createMode = 2;
	protected short inspectMode = 1;

	protected short fieldDisplayMode;

	// dynamic labels
	protected String arg0Name;
	protected String arg0Property;
	
	protected String model;
	protected String jspStyleClass;
	
	
	protected void initDynamicValues() {
		super.initDynamicValues();
		jspStyleClass = styleClass;
		if (styleClass==null){
			styleClass = getSkin().getProperty("styleclass.panel",null);
		}
	}
	
	protected void reset() {
		styleClass = jspStyleClass;
		jspStyleClass = null;
		super.reset();
	}
	
	/**
	 * Process a StartLayoutEvent.
	 * @return the type of layout tag.
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "<tr>");
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "</tr>");
	}

	protected void computeDisplayMode() {
	int formMode = getSkin().getFormUtils().getFormDisplayMode(pageContext);
	switch (formMode) {
		case FormUtilsInterface.CREATE_MODE :
			fieldDisplayMode = createMode;
			break;
		case FormUtilsInterface.EDIT_MODE :
			fieldDisplayMode = editMode;
			break;
		case FormUtilsInterface.INSPECT_MODE :
			fieldDisplayMode = inspectMode;
			break;
	}
}
/**
 * End the body of the panel.
 */
protected void doAfterBody(StringBuffer buffer) throws JspException {
	panel.doAfterBody(buffer);
}
/**
 * Prepare to print HTML content in the body of the panel.
 */
protected void doBeforeBody(StringBuffer buffer) throws JspException{
	doBeforeBody(buffer, align);
}
/**
 * Prepare to print content in the body of the panel.
 * @param align center/left/right
 */
protected void doBeforeBody(StringBuffer buffer, String align) throws JspException {
	panel.doBeforeBody(buffer, align);
}
/**
 * Print the end of the panel in the buffer.
 * Deleguate the call to the panel instance
 */
protected void doEndPanel(StringBuffer buffer) throws JspException {
	panel.doEndPanel(buffer);
}
protected void doEndLayout() throws JspException {
	new EndLayoutEvent(this, "</td>").send();
}
public int doEndLayoutTag() throws JspException {
	if (fieldDisplayMode==AbstractModeFieldTag.MODE_NODISPLAY) return EVAL_PAGE;
	
	StringBuffer buffer = new StringBuffer();
	doAfterBody(buffer);
	doEndPanel(buffer);
	TagUtils.write(pageContext, buffer.toString());
	doEndLayout();
	return EVAL_PAGE;
}
/**
 * Insert a blank line in the body of the panel
 */
protected void doPrintBlankLine(StringBuffer buffer) throws JspException {
	panel.doPrintBlankLine(buffer, cols);
}
/**
 * Display the title of the panel, using the key attribute.<br>
 * A call to this method must be done after doStartPanel() and doBeforeBody()
 * The call is delaguated to the panel instance.
 */
protected void doPrintTitle(StringBuffer buffer) throws JspException {
	String l_title = null;
	if (arg0Name!=null) {
		Object l_bean = pageContext.findAttribute(arg0Name);
		if (l_bean!=null) {
			Object l_value = fr.improve.struts.taglib.layout.util.LayoutUtils.getProperty(l_bean, arg0Property);
			if (l_value!=null) arg0 = l_value.toString();
		}
	}
	if (key==null && arg0!=null) l_title = arg0;
	if (key!=null || name!=null) l_title = getLabel();
	panel.doPrintTitle(buffer, l_title);
}
/**
 * Start to display a panel with the default alignment.
 */
protected void doStartPanel(StringBuffer buffer) throws JspException {
	doStartPanel(buffer, align);
}
/**
 * Start to display a panel with the specified alignment.
 * Deleguate the call to the panel instance.
 * @param align left/center/right
 */
protected void doStartPanel(StringBuffer buffer, String align) throws JspException {
	init();
	panel.init(pageContext, styleClass, this);
	panel.doStartPanel(buffer, align, width);
}
protected void doStartLayout() throws JspException {
	cols = getSkin().getFieldInterface().getColumnNumber();
	new StartLayoutEvent(this, "<td valign=\"top\" colspan=\"" + cols + "\">").send();
}
public int doStartLayoutTag() throws JspException {
	computeDisplayMode();
	if (fieldDisplayMode==AbstractModeFieldTag.MODE_NODISPLAY) return SKIP_BODY;
	
	doStartLayout();
	StringBuffer buffer = new StringBuffer();
	doStartPanel(buffer);
	doPrintTitle(buffer);
	doBeforeBody(buffer);
	TagUtils.write(pageContext,buffer.toString());
	return EVAL_BODY_INCLUDE;
}
/**
 * Insert the method's description here.
 * Creation date: (26/09/2001 11:33:21)
 * @return java.lang.String
 */
public java.lang.String getArg0Name() {
	return arg0Name;
}
/**
 * Insert the method's description here.
 * Creation date: (26/09/2001 11:33:21)
 * @return java.lang.String
 */
public java.lang.String getArg0Property() {
	return arg0Property;
}
	public PanelInterface getPanelInterface() {
		return panel;
	}
	public void init() {
		try {
			defaultPanel = (PanelInterface) getSkin().getPanelClass(model).newInstance();
		} catch (Exception e) {
            LOG.error("Unable to instanciate model " + model,e);
			defaultPanel = new fr.improve.struts.taglib.layout.util.BasicPanel();
		}
		panel = defaultPanel;
	}
public void release() {
	super.release();
	cols = 2;
	width= null;
	height = null;
	align ="center";
	panel = defaultPanel;
	model = null;

	editMode = 2;
	createMode = 2;
	inspectMode = 1;	
}
/**
 * Set the alignment of the panel (left, center or right)
 */
public void setAlign(String align) {
	this.align = align;
}
public String getAlign() {
	return align;
}
/**
 * Insert the method's description here.
 * Creation date: (26/09/2001 11:33:21)
 * @param newArg0Name java.lang.String
 */
public void setArg0Name(java.lang.String newArg0Name) {
	arg0Name = newArg0Name;
}
/**
 * Insert the method's description here.
 * Creation date: (26/09/2001 11:33:21)
 * @param newArg0Property java.lang.String
 */
public void setArg0Property(java.lang.String newArg0Property) {
	arg0Property = newArg0Property;
}
/**
 * Set the numbers of columns<br>
 * Use when displaying the title and blank lines.
 */
public void setCols(int cols) {
	this.cols = cols;
}
	/**
	 * Set the display mode
	 * format is XX,XX,XX where XX can be N (not displayed), E (editable), I (inspectable). Order is create mode, edit mode, inspect mode
	 */
	public void setMode(String mode) throws JspException {
		// Do some basic tests.
		if (mode==null) return;
		if (mode.length()!=5) throw new JspException("fieldTag mode " + mode + " is invalid.");
		
		// Compute the create mode.
		char c = mode.charAt(0);
		createMode = convertMode(c);
		
		//Compute the edit mode.
		c = mode.charAt(2);
		editMode = convertMode(c);		
		
		// Compute the inspect mode.
		c = mode.charAt(4);
		inspectMode = convertMode(c);		
	}

	/**
	 * Convert display mode from char to short. 
	 * @param c	
	 * @throws JspException
	 */
	private short convertMode(char c) throws JspException {
		short lc_mode;
		switch(c) {
			case 'N': lc_mode=0; break;
			case 'E': lc_mode=2; break;
			case 'I': lc_mode=1; break;
			default: throw new JspException("panelTag mode " + c + " is invalid. Valid mode are N, E and I");
		}
		return lc_mode;
	}
	
/**
 * with of the panel tables
 * default: 100%
 */
public void setWidth(String width) {
	if (!width.equals("0")) this.width = width; 
	else this.width=null;
}
public String getWidth() {
	return width;
}
	/**
	 * Returns the height.
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return Returns the model.
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model The model to set.
	 */
	public void setModel(String model) {
		this.model = model;
	}
}
