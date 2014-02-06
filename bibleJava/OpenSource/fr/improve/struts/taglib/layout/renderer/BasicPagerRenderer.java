package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.pager.PagerTag;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.IPagerRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

public class BasicPagerRenderer implements IPagerRenderer {
	
	
	public void doStartPager(PagerTag in_tag, StringBuffer in_buffer) {
		// Start
		in_buffer.append("<table border=\"0\" cellspacing=\"1\" cellpadding=\"1\"");
		if (in_tag.getWidth() != null) {
			in_buffer.append(" width=\"");
			in_buffer.append(in_tag.getWidth());
			in_buffer.append("\"");
		}
		if (in_tag.getStyleClass() != null) {
			in_buffer.append(" class=\"");
			in_buffer.append(in_tag.getStyleClass());
			in_buffer.append("\"");
		}
        in_buffer.append(" id=\"");
        in_buffer.append(in_tag.getPagerId());
		in_buffer.append("\"><tr>\n");		
	}

	public void doPrintPrevious(PagerTag in_tag, StringBuffer in_buffer, int l_currentPage) {
		// Prev section
	    in_buffer.append("<td");
	    if (in_tag.getAlign()==null) {
	    	in_buffer.append(" width=\"50%\" style=\"text-align : right\"");
	    }
		if (in_tag.getStyleClass() != null) {
			in_buffer.append(" class='");
			in_buffer.append(in_tag.getStyleClass());
			in_buffer.append("'");
		}
		in_buffer.append(">");
		if (l_currentPage >= 1) {
			PageContext pg = in_tag.getPageContext();
			in_buffer.append("<a href=\"");
			in_buffer.append(((HttpServletResponse)pg.getResponse()).encodeURL(in_tag.getURL(l_currentPage-1)));
			in_buffer.append("\">");
	
			String lc_previousImgPath = getSkin(pg).getProperty(in_tag.getPreviousImgKey());
			String lc_previousLabel = getSkin(pg).getProperty(in_tag.getPreviousMsgKey());
			if (lc_previousImgPath == null || lc_previousImgPath.length()==0) {
				in_buffer.append(lc_previousLabel);					
			} else {
				in_buffer.append("<img src='");					
				in_buffer.append(getSkin(pg).getImageDirectory(pg.getRequest()));
				in_buffer.append("/");
				in_buffer.append(lc_previousImgPath);					
				in_buffer.append("' border='0' alt='");					
				in_buffer.append(lc_previousLabel);					
				in_buffer.append("'>");					
			}
			in_buffer.append("</a>");					
		}
		in_buffer.append("</td>\n");
	}
	
	
	
	private Skin getSkin(PageContext in_pg) {
		return LayoutUtils.getSkin(in_pg.getSession());
	}

	public void doPrintMain(PagerTag in_tag, StringBuffer in_buffer, int l_maxPageItems, int l_currentPage) {
		// Main section
	    in_buffer.append("<td ");
			if (in_tag.getStyleClass() != null) {
				in_buffer.append(" class='");
				in_buffer.append(in_tag.getStyleClass());
				in_buffer.append("'");
			}
		in_buffer.append(">");
		
		// Get the maximum number of links to display.
		int l_maxLinks = in_tag.computeMaxLinks();		
		
		// Have we display a link previously ?
		boolean lc_previousDisplayed = true;
		
		// Iteration index.
		int i;
		for (i = 0; in_tag.getSize() > l_maxPageItems * i ; i++) {
			
			if (in_tag.shouldDisplay(i, l_currentPage, in_tag.getNumberOfPage(), l_maxLinks)) {
				// Ok, this page number must appear in the pager.
				if (i != l_currentPage) {
					in_buffer.append("<a href=\"");
					in_buffer.append(((HttpServletResponse)in_tag.getPageContext().getResponse()).encodeURL(in_tag.getURL(i)));
					if (in_tag.getStyleClass() != null) {
						in_buffer.append("\" class=\"");
						in_buffer.append(in_tag.getStyleClass());
					}
					in_buffer.append("\">");
				}
				in_buffer.append((i+1));
				if (i != l_currentPage) {
					in_buffer.append("</a>");
				}
				lc_previousDisplayed = true;
				in_buffer.append("&nbsp;");
			} else {
				// There is too many pages, and we're not displaying a link to this page number.
				if (lc_previousDisplayed) {
					in_buffer.append("...");
					in_buffer.append("&nbsp;");
				}
				lc_previousDisplayed = false;
			}			
		}
		in_buffer.append("</td>\n");
//		/return i;
	}

	public void doPrintNext(PagerTag in_tag, StringBuffer in_buffer, int l_maxPageItems, int l_currentPage) {
		// Next section
	    in_buffer.append("<td");
	    if (in_tag.getAlign()==null) {
	    	in_buffer.append(" width=\"50%\" style=\"text-align : left\"");
	    }
		if (in_tag.getStyleClass() != null) {
			in_buffer.append(" class='");
			in_buffer.append(in_tag.getStyleClass());
			in_buffer.append("'");
		}
		in_buffer.append(">");
		Skin lc_skin = getSkin(in_tag.getPageContext());
		if ((l_currentPage+1) * l_maxPageItems < in_tag.getSize()) {
			in_buffer.append("<a href=\"");
			in_buffer.append(((HttpServletResponse)in_tag.getPageContext().getResponse()).encodeURL(in_tag.getURL(l_currentPage+1)));
			in_buffer.append("\">");
	
			String lc_nextImgPath = lc_skin.getProperty(in_tag.getNextImgKey());
			String lc_nextLabel = lc_skin.getProperty(in_tag.getNextMsgKey());
			if (lc_nextImgPath == null || lc_nextImgPath.length()==0) {
				in_buffer.append(lc_nextLabel);					
			} else {
				in_buffer.append("<img src='");					
				in_buffer.append(lc_skin.getImageDirectory(in_tag.getPageContext().getRequest()));
				in_buffer.append("/");
				in_buffer.append(lc_nextImgPath);					
				in_buffer.append("' border='0' alt='");					
				in_buffer.append(lc_nextLabel);					
				in_buffer.append("'>");					
			}
			in_buffer.append("</a>");					
		}
		in_buffer.append("</td>\n");
	}

	public void doPrintDirect(PagerTag in_tag, StringBuffer in_buffer, int in_maxPageItems, int in_currentPage) throws JspException {
		// Direct go section							
		in_buffer.append("<th");
		if (in_tag.getStyleClass() != null) {
			in_buffer.append(" class=\"");
			in_buffer.append(in_tag.getStyleClass());
			in_buffer.append("\"");			
		}
		in_buffer.append(">");		
		in_buffer.append(LayoutUtils.getLabel(in_tag.getPageContext(), in_tag.getGotoProperty(), null));
		in_buffer.append(" <input type=\"text\" value=\"\" size=\"4\" maxlength=\"4\" onkeypress=\"pagerGoto(this, event, '");
		in_buffer.append(in_tag.getUrl());
		in_buffer.append("','");
		in_buffer.append(PagerTag.PAGE_NUMBER_KEY);
		in_buffer.append("',");
		in_buffer.append((int)Math.ceil((float)in_tag.getSize()/in_maxPageItems));
		in_buffer.append(")\">");
		
		in_buffer.append("</th>");
	}

	public void doEndPager(PagerTag in_tag, StringBuffer in_buffer) {
		in_buffer.append("</tr></table>");
	}

	
	
}
