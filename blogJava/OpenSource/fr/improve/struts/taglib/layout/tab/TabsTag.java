package fr.improve.struts.taglib.layout.tab;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.BodyLayoutTagSupport;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.ErrorEvent;
import fr.improve.struts.taglib.layout.event.ErrorEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TabsInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TabsTag extends BodyLayoutTagSupport implements ErrorEventListener {
	/**
	 * Special comparator for the tabs Map.
	 * For historic reasons, the Map keys are 
	 * String representation of numbers, which 
	 * causes sort problems when there are more than 10 tab.
	 * This comparator is used to sort the String correctly. 
	 */
	private class TabComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			int l1 = s1.length();
			int l2 = s2.length();
			if (l1 == l2) {
				return s1.compareTo(s2);
			} else {
				return l1 - l2;
			}
		}
	}
	
	/**
	 * List of the tab titleKeys.
	 */
	private Map tabs = new TreeMap(new TabComparator());
	
	/**
	 * Name of the last attributed tabs in the request.
	 */
	private String LAST_TAB_KEY = "fr.improve.struts.taglib.layout.tab.TabsTag.KEY";

	/**
	 * Id of the current tab group.
	 */
	private String currentId;

	/**
	 * Number of the current tab in the group.
	 */
	private int currentTab = -1;

	/**
	 * Tabs renderer.
	 */
	private TabsInterface tabsRenderer;
	
	/**
	 * StyleClass
	 */
	private String styleClass;
		
	/**
	 * Width
	 */
	private String width;
	
	/**
	 * Name in the pageContext of the selected tab key.
	 */
	private String selectedTabKeyName;
	
	
	/**
	 * Name in the pageContext of the selected tab key.
	 */
	private String selectedTabKey;
		
	/**
	 * true if a tab has already been selected.
	 */
	private boolean tabAlreadySelected = false;
	
	/**
	 * Called by the <layout:tab> tags to add a tab.
	 * @param in_tabKey the key of the tab title.
	 * @return the id of the tab.
	 */
	public String addTab(String in_titleKey, String in_title, String in_href, String in_reqCode, String in_width, boolean in_selected) {		
		currentTab++;
		StringBuffer lc_id = new StringBuffer("tabs");
		lc_id.append(currentId);
		lc_id.append("tab");
		lc_id.append(currentTab);
		TabHeader lc_header = new TabHeader();
		lc_header.width = in_width;
		lc_header.title = in_title;
		lc_header.titleKey = in_titleKey;
		lc_header.href = in_href;
		lc_header.reqCode = in_reqCode;
		if (in_selected) {
			lc_header.styleClass = tabsRenderer.getHeaderEnabledStyle();	
			tabAlreadySelected = true;
		} else {
			lc_header.styleClass = tabsRenderer.getHeaderDisabledStyle();
		}
		tabs.put(String.valueOf(currentTab), lc_header);		
		return lc_id.toString();
	}
	/**
	 * Used internally to compute a unique id for each tab in a request.
	 * @return An array containing the id of the tabs and the tab number.
	 */
	private String computeNewTabId() {
		Integer lc_id = (Integer) pageContext.getRequest().getAttribute(LAST_TAB_KEY);
		if (lc_id==null) {
			lc_id = new Integer(0);	
		}
		pageContext.getRequest().setAttribute(LAST_TAB_KEY, new Integer(lc_id.intValue()+1));
		return lc_id.toString();
	}
	
	/**
	 * Called by the inner input field tag to notify an error.
	 */
	public void processErrorEvent(ErrorEvent in_event) {
		TabHeader lc_header = (TabHeader) tabs.get(String.valueOf(currentTab));
		lc_header.styleClass = tabsRenderer.getHeaderErrorStyle();
	}

	/**
	 * Start the tag. Init the tabs renderer.
	 */
	public int doStartLayoutTag() throws JspException {
		try {
			tabsRenderer = (TabsInterface) LayoutUtils.getSkin(pageContext.getSession()).getTabsClass().newInstance();
			tabsRenderer.init(pageContext, styleClass, this);
			currentId = computeNewTabId();
		} catch (Exception e) {
			System.out.println(e);
			// PENDING
			//tabsRenderer = new fr.improve.struts.taglib.layout.util.BasicPanel();
		}
		
		new StartLayoutEvent(this, new StringBuffer("<td colspan=\"").append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber()).append("\">").toString()).send();
		
		return EVAL_BODY_TAG;	
	}

	/**
	 * End of the body. Print everything.
	 */	
	public int doAfterBody() throws JspException {						
		StringBuffer lc_buffer = new StringBuffer();		
		tabsRenderer.doStartPanel(lc_buffer, "center", width);
		tabsRenderer.doStartHeaders(lc_buffer);
		tabsRenderer.doPrintHeaders(lc_buffer, currentId, tabs);
		tabsRenderer.doEndHeaders(lc_buffer);
		tabsRenderer.doBeforeBody(lc_buffer, "center");
		TagUtils.writePrevious(pageContext, lc_buffer.toString());
		lc_buffer.setLength(0);

		if (bodyContent != null) {
			TagUtils.writePrevious(pageContext, bodyContent.getString());
			bodyContent.clearBody();
		}	
		tabsRenderer.doAfterBody(lc_buffer);
		tabsRenderer.doEndPanel(lc_buffer);
		TagUtils.writePrevious(pageContext, lc_buffer.toString());
						
		return SKIP_BODY;
	}
	
	/**
	 * End the tag. Reset the tag.
	 */
	public int doEndLayoutTag() throws JspException {
		
		new EndLayoutEvent(this, "</td>").send();
		
		tabsRenderer = null;
		currentId = null;
		currentTab = -1;	
		tabs.clear();
		tabAlreadySelected = false;
		return EVAL_PAGE;
	}	
	public void release() {
		super.release();
		selectedTabKeyName = null;
		styleClass = null;
		width = null;
	}
	
	/**
	 * Returns the styleClass.
	 * @return String
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Returns the width.
	 * @return String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * Returns the selectedTabKeyName.
	 * @return String
	 */
	public String getSelectedTabKeyName() {
		return selectedTabKeyName;
	}

	/**
	 * Sets the selectedTabKeyName.
	 * @param selectedTabKeyName The selectedTabKeyName to set
	 */
	public void setSelectedTabKeyName(String selectedTabKeyName) {
		this.selectedTabKeyName = selectedTabKeyName;
	}

	/**
	 * Returns the tabAlreadySelected.
	 * @return boolean
	 */
	public boolean isTabAlreadySelected() {
		return tabAlreadySelected;
	}
	
	public String getSelectedTabKey(){	
		return selectedTabKey;
	}
	public void setSelectedTabKey(String in_name){	
		selectedTabKey = in_name;
	}
}
