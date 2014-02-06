package fr.improve.struts.taglib.layout.tab;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LabelledTag;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TabTag extends LabelledTag implements LayoutEventListener, StaticCodeIncludeListener {
	/**
	 * The width of the tab title.
	 */
	private String width;
	
	/**
	 * The url to invoke when this tab is selected. Default is to show the tab.
	 */
	private String href;
	private String forward;
	private String page;	
	
	/**
	 * The reqCode to submit when this tab is selected. Default is to show the tab
	 */
	private String reqCode;
	
	private boolean include;
	
	private String staticCode = "";
			
	public int doStartLayoutTag() throws JspException {
		TabsTag lc_tabs = (TabsTag) findAncestorWithClass(this, TabsTag.class);
		if (lc_tabs==null) {
			throw new JspException("Invalid use of <layout:tab>");
		}
		
		// Should we include the tab body ?			
		include = false;			
		
		String lc_href = null;
		if (href!=null || forward!=null || page!=null) {
			// We should call an url when the tab is selected
			// We don't need to generate its content.
			lc_href = LayoutUtils.computeURL(pageContext, forward, Expression.evaluate(href, pageContext), page, null, null, null, null, false, null);
		} else if (reqCode!=null) {
			// We should submit the current form when the tab is selected.
			// We don't need to generate its content.
		} else {
			// We should display the tab content without any server request
			// when the tab is selected.
			// We need to generate its content.
			include = true;	
		}
		
		//	Is the tab selected ? No if a tab has already been selected.
		boolean lc_selected = isTabSelected(lc_tabs);
		if (lc_selected) {
			include = true;	
		}
		String lc_id = lc_tabs.addTab(key, getLabel(), lc_href, reqCode, width, lc_selected);
						
		if (include) {
			TagUtils.write(pageContext, "<div id=\"");
			TagUtils.write(pageContext, lc_id);
			if (!lc_selected) {
				TagUtils.write(pageContext, "\" style=\"display:none;");	
			}
			TagUtils.write(pageContext, "\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"clsAction\">");
						
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * Return true if the current tab is selected.
	 */
	protected boolean isTabSelected(TabsTag lc_tabs) {
		// Easy, if a tab has already been selected.
		boolean lc_selected = !lc_tabs.isTabAlreadySelected();
		
		// No tab selected yet.			 
		if (lc_selected && lc_tabs.getSelectedTabKeyName()!=null) {
			String lc_selectedTab;
			
			// Look for a cookie.
			lc_selectedTab = getSelectedTabNameFormCookie((HttpServletRequest)pageContext.getRequest(), lc_tabs.getSelectedTabKeyName());
			
			// Look for a request attribute.
			if (lc_selectedTab==null) {			
				lc_selectedTab = (String) pageContext.findAttribute(lc_tabs.getSelectedTabKeyName());
			}
			
			// Look for a request parameter.
			if (lc_selectedTab==null) {
				lc_selectedTab = pageContext.getRequest().getParameter(lc_tabs.getSelectedTabKeyName());
			}
			
			// Check the selected tab key value.
			if (lc_selectedTab!=null && !lc_selectedTab.equals(key)) {
				lc_selected = false;	
			}
			if (lc_selected){
				lc_tabs.setSelectedTabKey(key);
			} 			
		}				
		return lc_selected;
	}
	
	public static String getSelectedTabNameFormCookie(HttpServletRequest in_request, String in_keyName) {
		Cookie[] lc_cookies = in_request.getCookies();
		if (lc_cookies==null) {
			// no cookie at all..
			return null;
		}
				
		for (int i=0;i<lc_cookies.length;i++) {
			Cookie lc_cookie = lc_cookies[i];
			if (lc_cookie.getName().equals("selectedTab")) {
				String lc_value = lc_cookie.getValue();
				lc_value = URLDecoder.decode(lc_value);
				if (lc_value==null) {
					return null;
				}
				int lc_keyNameStart = 0;
				String lc_keyName = null;
				int lc_keyValueStart = -1;
				String lc_keyValue = null;
				for (int j=0; j < lc_value.length(); j++) {
					switch (lc_value.charAt(j)) {
						case '=':
							if (lc_keyNameStart!=-1) {
								// We found a key.
								lc_keyName = lc_value.substring(lc_keyNameStart, j).trim();
								lc_keyValueStart = j+1;
							}
							break;
						case ';':
							if (lc_keyName!=null) {
								// We found a key/value pair !
								lc_keyValue = lc_value.substring(lc_keyValueStart, j).trim();
								//System.out.println("found : " + lc_keyName + " = " + lc_keyValue);
								if (in_keyName.equals(lc_keyName)) {
									return lc_keyValue;
								}
							}
							lc_keyName = null;
							lc_keyValue = null;
							lc_keyNameStart = j+1;
							lc_keyValueStart = -1;
							break;
					}
				}				
			} 
		}		
		return null;	
	}
	
	public static void setSelectedTabNameFromCookie(HttpServletRequest in_request, HttpServletResponse in_response, String in_group, String in_keyName) {
		// Get cookie.
		Cookie[] lc_cookies = in_request.getCookies();
		Cookie lc_oldCookie = null;
		Map lc_tabsInformation = new HashMap();
		if (lc_cookies!=null) {
			for (int i=0;i<lc_cookies.length;i++) {
				Cookie lc_cookie = lc_cookies[i];
				if (lc_cookie.getName().equals("selectedTab")) {
					String lc_value = lc_cookie.getValue();
					StringTokenizer lc_tokenizer = new StringTokenizer(URLDecoder.decode(lc_value), ";=");
					while (lc_tokenizer. hasMoreTokens()) {
						String lc_keyName = lc_tokenizer.nextToken();
						String lc_keyValue = null;					
						if (lc_tokenizer.hasMoreTokens()) {
							lc_keyValue = lc_tokenizer.nextToken();						
						}
						lc_tabsInformation.put(lc_keyName, lc_keyValue); 
					}
					lc_oldCookie = lc_cookie;
					break;
				} 
			}	
		}
		lc_tabsInformation.put(in_group, in_keyName);
		StringBuffer lc_value = new StringBuffer();
		Iterator lc_it = lc_tabsInformation.keySet().iterator();
		while (lc_it.hasNext()) {
			String lc_tabName = (String) lc_it.next();
			String lc_tabValue = (String) lc_tabsInformation.get(lc_tabName);
			lc_value.append(lc_tabName).append('=').append(lc_tabValue).append(';');
		}
		String lc_newCookieValue = URLEncoder.encode(lc_value.toString());
		Cookie lc_cookie = new Cookie("selectedTab", lc_newCookieValue);
		lc_cookie.setPath(in_request.getContextPath());
		if (lc_oldCookie!=null) {
			// update old cookie : it will be used by the jsp.
			lc_oldCookie.setValue(lc_newCookieValue);
		}
		in_response.addCookie(lc_cookie);
	}
	
	public int doEndLayoutTag() throws JspException {		
		if (include) {
			TagUtils.write(pageContext, "</table></div>");
		}
		if (staticCode.length()!=0) {
			TagUtils.write(pageContext, staticCode);
			staticCode = "";
		}
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		width = null;
		href = null;
		forward = null;
		page = null;
		reqCode = null;
	}
	
	/**
	 * Deal with StartLayoutEvents.
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "<tr>");
	}
	/**
	 * Deal with EndLayoutEvents.
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return in_event.consume(pageContext, "</tr>");
	}
	
	/**
	 * Use to print the calendar static code in a visible layer.
	 */
	public Object processStaticCodeIncludeEvent(StaticCodeIncludeLayoutEvent in_event) throws JspException {
		staticCode += in_event.sendToParent(this);
		return "";
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
	 * Returns the forward.
	 * @return String
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * Returns the href.
	 * @return String
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Sets the forward.
	 * @param forward The forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * Sets the href.
	 * @param href The href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * Returns the page.
	 * @return String
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 * @param page The page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	public String getReqCode() {
		return reqCode;
	}
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
}
