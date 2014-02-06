package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.renderer.BasicPopupRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.PanelInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Useful tag to create a popup.
 * 
 * @author JN RIBETTE
 */
public class PopupTag extends PanelTag {
	/**
	 * Constants, indicating if the popup Js code has already been loaded or not.
	 */
	public static final String POPUP_KEY = "fr.improve.struts.taglib.layout.PopupTag.POPUP_KEY";
	
	/**
	 * Javascript id of the DIV element.
	 */
	private String styleId;
	
	public void doStartLayout() throws JspException {
		// Start TR/TD for Struts-Layout compatibility.
		super.doStartLayout();
		
		// Generate Javascript code.
		loadScript();
		
		// Generate DIV.
		TagUtils.write(pageContext, "<div id=\"");
		TagUtils.write(pageContext, styleId);
		TagUtils.write(pageContext, "\" style=\"position:absolute; left:100px; top:100px; z-index:10; visibility:hidden;\">\n");
	}
	
	public void doEndLayout() throws JspException {
		// GenerateDIV.
		TagUtils.write(pageContext, "</div>\n");
		
		// End TD/TR for Struts-Layout compatibility.
		super.doEndLayout();
	}
	
	/**
	 * Load the required Javascript code.
	 */
	protected void loadScript() throws JspException {
		if (pageContext.getRequest().getAttribute(POPUP_KEY)==null) {
			TagUtils.write(pageContext, "<script src=\"");
			TagUtils.write(pageContext, LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
			TagUtils.write(pageContext, "/popup.js\"></script>");
			TagUtils.write(pageContext, "<div id=\"slpdiv\" style=\"display:none;position:absolute;left:0px;top:0px; width:100%; height:100%; z-index:9;\"></div>");
			pageContext.getRequest().setAttribute(POPUP_KEY, "");
		}		
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public void init() {		
		try {
			panel = (PanelInterface) getSkin().getPopupClass(model).newInstance();
		} catch (Exception e) {
			panel = new BasicPopupRenderer();
		}
	}
}
