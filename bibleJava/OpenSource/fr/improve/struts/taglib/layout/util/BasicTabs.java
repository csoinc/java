package fr.improve.struts.taglib.layout.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.FormTag;
import fr.improve.struts.taglib.layout.tab.TabHeader;
import fr.improve.struts.taglib.layout.tab.TabsTag;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BasicTabs extends BasicPanel implements TabsInterface {
	// static css class name.
	// PENDING: move in the <layout:tabs> tag.
	protected static String STYLE_HEADER_ENA = "ongletTextEna";
	protected static String STYLE_HEADER_DIS = "ongletTextDis";
	protected static String STYLE_HEADER_ERR = "ongletTextErr";
	protected static String STYLE_TAB_SPACE = "ongletSpace";
	protected static String STYLE_TAB_MAIN = "ongletMain";
	protected static String STYLE_TAB_MIDDLE = "ongletMiddle";
	
	protected PageContext pageContext;
	
	protected String selectedTabKeyName;
	
	public String getHeaderEnabledStyle() {
		return STYLE_HEADER_ENA;	
	}
	public String getHeaderDisabledStyle() {
		return STYLE_HEADER_DIS;	
	}
	public String getHeaderErrorStyle() {
		return STYLE_HEADER_ERR;	
	}
	
	public void doPrintTitle() {
		// do nothing	
	}
	
	public void doStartPanel(StringBuffer buffer, String align, String width) {		
		buffer.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\"");
		if (width!=null) {
			buffer.append(" width=\"");
			buffer.append(width);
			buffer.append("\"");
		}
		buffer.append(">");
	}
	
	public void doStartHeaders(StringBuffer buffer) {
	buffer.append("<tr><td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tr>");	
	}
	
	public void doPrintHeaders(StringBuffer out_buffer, String in_tabId, Map in_headers) {
		
		// the headers.
		Iterator lc_it = in_headers.entrySet().iterator();;
		while (lc_it.hasNext()) {
			Map.Entry lc_entry = (Map.Entry) lc_it.next();
			String lc_id = (String) lc_entry.getKey();
			TabHeader lc_header = (TabHeader) lc_entry.getValue();			
			// 1 header
			out_buffer.append("<td id=\"tabs");
			out_buffer.append(in_tabId);
			out_buffer.append("head");
			out_buffer.append(lc_id);
			out_buffer.append("\" class=\"");
			out_buffer.append(lc_header.styleClass);						
			if (lc_header.width!=null) {
				out_buffer.append("\" width=\"");
				out_buffer.append(lc_header.width);	
			}
			out_buffer.append("\" onmouseover=\"onTabHeaderOver(");
			out_buffer.append(in_tabId);
			out_buffer.append(",");
			out_buffer.append(lc_id);
			out_buffer.append(",'");
			out_buffer.append(STYLE_HEADER_ENA);
			out_buffer.append("')\"");
			
			if (lc_header.href==null ){
				out_buffer.append(" onclick=\"");
				if (lc_header.reqCode==null) {
					out_buffer.append("selectTab(");
					out_buffer.append(in_tabId);
					out_buffer.append(",");
					out_buffer.append(in_headers.size());
					out_buffer.append(",");
					out_buffer.append(lc_id);
					out_buffer.append(",'");
					out_buffer.append(STYLE_HEADER_ENA);
					out_buffer.append("','");
					out_buffer.append(STYLE_HEADER_DIS);
					out_buffer.append("','");
					out_buffer.append(STYLE_HEADER_ERR);
					out_buffer.append("'");
					if (selectedTabKeyName!=null) {
						out_buffer.append(",'");
						out_buffer.append(selectedTabKeyName);
						out_buffer.append("','");
						out_buffer.append(FilterUtils.filterQuotes(lc_header.titleKey));
						out_buffer.append("'");					
					} else {
						out_buffer.append(",null,null");											
					}				
					out_buffer.append(");");
				}
			
				if (lc_header.reqCode!=null){
					String lc_formName = FormTag.computeFormName(pageContext);
					String lc_parameter = FormTag.computeActionParameter(pageContext);
					out_buffer.append(" document.forms[\'");
					out_buffer.append(lc_formName);
					out_buffer.append("\'].");
					out_buffer.append(lc_parameter);
					out_buffer.append(".value=\'");
					out_buffer.append(lc_header.reqCode);
					out_buffer.append("\';document.forms[\'");
					out_buffer.append(lc_formName);
					out_buffer.append("\'].submit();");				
				}
			}
			
			out_buffer.append("\">");
			
			if (lc_header.href!=null) {
				out_buffer.append("<a href=\"");
				out_buffer.append(lc_header.href);
				out_buffer.append("\" class=\"");
				out_buffer.append(lc_header.styleClass);
				out_buffer.append("\">");	
			}
			out_buffer.append(lc_header.title);
			if (lc_header.href!=null) {
				out_buffer.append("</a>");
			}
			out_buffer.append("</td>");
			
			// 1 separator
			out_buffer.append("<td width=\"5\" class=\"");
			out_buffer.append(STYLE_TAB_SPACE);
			out_buffer.append("\">&nbsp;</td>");
		}
		
		// 1 separator
		out_buffer.append("<td class=\"");
		out_buffer.append(STYLE_TAB_SPACE);
		out_buffer.append("\">&nbsp;</td></tr>");

		// 1 separator line.		
		out_buffer.append("<tr><td height=\"5\" colspan=\"");
		out_buffer.append(in_headers.size()*2 + 1);
		out_buffer.append("\" class=\"");
		out_buffer.append(STYLE_TAB_MIDDLE);
		out_buffer.append("\">&nbsp;</td></tr>");	
	}
	
	public void doEndHeaders(StringBuffer buffer) {
		buffer.append("</table></td></tr>");
	}		
		
	public void doBeforeBody(StringBuffer out_buffer, String in_align) {
		out_buffer.append("<tr><td class=\"");
		out_buffer.append(STYLE_TAB_MAIN);
		out_buffer.append("\">");
	}
	
	public void doAfterBody(StringBuffer out_buffer) {
		out_buffer.append("</td></tr>");	
	}
	
	public void doEndPanel(StringBuffer out_buffer) {
		out_buffer.append("</table>\n");	
	}
	public void init(PageContext pg, String in_styleClass, TagSupport in_tabs) throws JspException {		
		super.init(pg, in_styleClass, in_tabs);
		TabsTag lc_tabs = (TabsTag) in_tabs;
		selectedTabKeyName = lc_tabs.getSelectedTabKeyName();
		pageContext = pg;
	}	

}
