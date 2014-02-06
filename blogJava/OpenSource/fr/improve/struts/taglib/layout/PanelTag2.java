package fr.improve.struts.taglib.layout;

import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Render a panel element with handles
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
 * @deprecated
 */
public class PanelTag2 extends LabelledTag {
	protected int cols = 2;
	protected String width;
	protected String align = "center";
	public final static String CENTER = "center";
	public final static String LEFT = "left";
	public final static String RIGHT = "rigth";
	
	private ResourceBundle resBundle;
/**
 * End the body of the panel.
 */
protected void doAfterBody(StringBuffer buffer) {
	buffer.append("</table></td></tr>\n");
}
/**
 * Prepare to print HTML content in the body of the panel.
 */
protected void doBeforeBody(StringBuffer buffer) {
	doBeforeBody(buffer, align);
}
/**
 * Prepare to print content in the body of the panel.
 * @param align center/left/right
 */
protected void doBeforeBody(StringBuffer buffer, String align) {
	buffer.append("<tr><td class=");
	buffer.append(styleClass);
	buffer.append("><table align=" + align +" border=0>\n");
}
/**
 * Display the end of the panel in the buffer.
 */
protected void doEndPanel(StringBuffer buffer) throws JspException {
	buffer.append("</table></td>\n");

	String bg_width = resBundle.getString("width");
	String bg_height = resBundle.getString("height");
	
	
	// end of the middle line
	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" background=\"");
	buffer.append(resBundle.getString("right"));
	buffer.append("\" style=\"background-repeat:repeat-y;\">&nbsp</td>");
	buffer.append("</tr>");

	// last line
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_height);
	buffer.append("\"><img src=\"");
	buffer.append(resBundle.getString("bottom_LEFT"));
	buffer.append("\"></td>");
	
	buffer.append("<td height=\"");
	buffer.append(bg_height);
	buffer.append("\" background=\"");
	buffer.append(resBundle.getString("bottom"));
	buffer.append("\" style=\"background-repeat:repeat-x;\">&nbsp;</td>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_height);
	buffer.append("\"><img src=\"");
	buffer.append(resBundle.getString("bottom_RIGHT"));
	buffer.append("\"></td>");
	
	buffer.append("</tr>");

	//end table
	buffer.append("</table>");
}
public int doEndLayoutTag() throws JspException {
	StringBuffer buffer = new StringBuffer();
	doAfterBody(buffer);
	doEndPanel(buffer);
	TagUtils.write(pageContext, buffer.toString());
	return EVAL_PAGE;
}
/**
 * Insert a blank line in the body of the panel
 */
protected void doPrintBlankLine(StringBuffer buffer) {
	buffer.append("<tr><td colspan=" + cols + ">&nbsp</td></tr>\n");
}
/**
 * Display the title of the panel, using the key attribute.<br>
 * A call to this method must be done after doStartPanel() and doBeforeBody()
 * TO REMOVE
 */
protected void doPrintTitle(StringBuffer buffer) throws JspException {
	//if (key != null || name!=null) {
		//buffer.append("<tr><th height=16 align=center");
		//if (styleClass != null) {
			//buffer.append(" class=");
			//buffer.append(styleClass);
		//}
		//buffer.append(">");
		//buffer.append(getLabel());
		//buffer.append("</th></tr>\n");
	//}	
}
/**
 * Start to display a panel with the default alignment.
 */
protected void doStartPanel(StringBuffer buffer) throws JspException {
	doStartPanel(buffer, align);
}
/**
 * Start to display a panel with the specified alignment.
 * @param align left/center/right
 */
protected void doStartPanel(StringBuffer buffer, String align) throws JspException {

	String l_skin = LayoutUtils.getSkin(pageContext.getSession()).getCssFileName();
	if (l_skin.endsWith(".css")) l_skin = l_skin.substring(0,l_skin.length()-4);
	resBundle = ResourceBundle.getBundle("com.applicationservers.forum.client.struts.Layout",new java.util.Locale(l_skin,(styleClass==null?"":styleClass)));
	
	// table
	buffer.append("<table cellspacing=0 cellpadding=0 border=0 align=");
	buffer.append(align);
	if (width!=null) { 
		buffer.append(" width=");
		buffer.append(width);
	}
	buffer.append(">");

	String bg_width = resBundle.getString("width");
	String bg_height = resBundle.getString("height");
	
	// top line
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_height);
	buffer.append("\"><img src=\"");
	buffer.append(resBundle.getString("top_LEFT"));
	buffer.append("\"></td>");
	
	buffer.append("<td");

	String label = getLabel();
	if (label!=null) {
		buffer.append("><table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\" class=\"");
		buffer.append(styleClass);
		buffer.append("\"><tr><th class=\"");
		buffer.append(styleClass);
		buffer.append("\">");
		buffer.append(getLabel());
		buffer.append("</th></tr></table>");
	} else {
		buffer.append(" height=\"");
		buffer.append(bg_height);
		buffer.append("\" background=\"");
		buffer.append(resBundle.getString("haut"));
		buffer.append("\" style=\"background-repeat:repeat-x;\">&nbsp");
	}
	buffer.append("</td>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" height=\"");
	buffer.append(bg_height);
	buffer.append("\"><img src=\"");
	buffer.append(resBundle.getString("top_RIGHT"));
	buffer.append("\"></td>");
	
	buffer.append("</tr>");
	
	// middle
	buffer.append("<tr>");

	buffer.append("<td width=\"");
	buffer.append(bg_width);
	buffer.append("\" background=\"");
	buffer.append(resBundle.getString("left"));
	buffer.append("\" style=\"background-repeat:repeat-y;\">&nbsp</td>");
		
	buffer.append("<td><table cellspacing=0 cellpadding=0 border=0 width=100%>\n");	
}
public int doStartLayoutTag() throws JspException {
	StringBuffer buffer = new StringBuffer();
	doStartPanel(buffer);
	doBeforeBody(buffer);
	TagUtils.write(pageContext,buffer.toString());
	return EVAL_BODY_INCLUDE;
}
public void release() {
	super.release();
	cols = 2;
	width= null;
	align ="center";
}
/**
 * Set the alignment of the panel (left, center or right)
 */
public void setAlign(String align) {
	this.align = align;
}
/**
 * Set the numbers of columns<br>
 * Use when displaying the title and blank lines.
 */
public void setCols(int cols) {
	this.cols = cols;
}
/**
 * with of the panel tables
 * default: 100%
 */
public void setWidth(String width) {
	if (!width.equals("0")) this.width = width; 
	else this.width=null;
}
}
