package com.oyou.web.taglib;

import java.text.MessageFormat;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.TagUtils;

import com.oyou.common.struts.MessagesFactory;
import com.oyou.common.util.StringHelper;
import com.oyou.common.util.StrutsSession;

/**
 * Globals.MESSAGE_KEY - info Globals.ERROR_KEY - error
 */
public final class ErrorsTag extends TagSupport {
	//private static final Log log = LogFactory.getLog(ErrorsTag.class);
	private static final String errorImg = "/images/error.gif";
	private static final String infoImg = "/images/info.gif";

	protected String bundle;
	
	protected String locale;

	public final int doStartTag() throws JspException {
		//log.debug("==doStartTag");
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String ctxPath = request.getContextPath();
		ActionMessages messages;
		boolean hasErrors = false;
		
		messages = (ActionMessages) pageContext.getAttribute(StrutsSession.KEY_BUSINESS, PageContext.REQUEST_SCOPE);
		if (messages != null && !messages.isEmpty()) hasErrors = true;
		messages = (ActionMessages) pageContext.getAttribute(Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);
		if (messages != null && !messages.isEmpty()) hasErrors = true;

		//errors
		if (hasErrors) {
			boolean isPresentHeader = false;
			boolean isPresentFooter = false;
			
			if (StringHelper.isEmpty(this.bundle))
				isPresentHeader = MessagesFactory.getInstance().getProperty("errors.header") == null? false : true;
			else 
				isPresentHeader = MessagesFactory.getInstance().getProperty(this.bundle, "errors.header") == null? false : true;
			
			StringBuffer sb = new StringBuffer();
			String imgUrl = ctxPath + errorImg;

			sb.append("<table>\n");
			if (isPresentHeader) {
				String html = "";
				
				if (StringHelper.isEmpty(this.bundle)) 
					html = MessagesFactory.getInstance().getProperty("errors.header");
				else 
					html = MessagesFactory.getInstance().getProperty(this.bundle, "errors.header");
						
				if (html != null) {
					sb.append("<tr>");
					sb.append("<td colspan=2>");
					sb.append(html);
					sb.append("</td>");
					sb.append("</tr>\n");
				}
				sb.append("<tr><td colspan=2></td></tr>\n");
			}

			Iterator it;
			messages = (ActionMessages) pageContext.getAttribute(StrutsSession.KEY_BUSINESS, PageContext.REQUEST_SCOPE);
			if (messages != null && !messages.isEmpty()) {
				it = messages.get(StrutsSession.KEY_BUSINESS);
				sb.append(processActionMessages(it, imgUrl));	
			}
			messages = (ActionMessages) pageContext.getAttribute(Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);
			if (messages != null && !messages.isEmpty()) {
				it = messages.get();
				sb.append(processActionMessages(it, imgUrl));	
			}
			pageContext.removeAttribute(StrutsSession.KEY_BUSINESS, PageContext.REQUEST_SCOPE);
			pageContext.removeAttribute(Globals.ERROR_KEY, PageContext.REQUEST_SCOPE);
			
			if (isPresentFooter) {
				String html = "";
				html = MessagesFactory.getInstance().getProperty("errors.footer");
				if (html != null) {
					sb.append("<tr>");
					sb.append("<td><br></td>");
					sb.append("<td>");
					sb.append(html);
					sb.append("</td>");
					sb.append("</tr>\n");
				}
			}
			sb.append("<tr><td colspan=2></td></tr>\n");
			sb.append("</table>\n");
			TagUtils.getInstance().write(pageContext, sb.toString());
		}

		//info
		messages = (ActionMessages) pageContext.getAttribute(Globals.MESSAGE_KEY, PageContext.REQUEST_SCOPE);
		if (messages != null && !messages.isEmpty()) {
			String imgUrl = ctxPath + infoImg;
			StringBuffer sb = new StringBuffer();
			sb.append("<table>\n");
			Iterator it = messages.get();
			sb.append(processActionMessages(it, imgUrl));	
			sb.append("<tr><td colspan=2></td></tr>\n");
			sb.append("</table>\n");
			TagUtils.getInstance().write(pageContext, sb.toString());
		}
		pageContext.removeAttribute(Globals.MESSAGE_KEY, PageContext.REQUEST_SCOPE);

		return SKIP_BODY;
	}

	private String processActionMessages(Iterator it, String imgUrl) throws JspException {
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			ActionMessage message = (ActionMessage) it.next();
			sb.append("<tr>\n");
			sb.append("<td><img src=\"" + imgUrl + "\" height=\"13\" width=\"13\" border=\"0\"></td>\n");
			sb.append("<td>\n");
			String content = "";
			if (StringHelper.isEmpty(this.bundle))
				content = MessageFormat.format(MessagesFactory.getInstance().getProperty(message.getKey()), message.getValues());
			else 
				content = MessageFormat.format(MessagesFactory.getInstance().getProperty(this.bundle, message.getKey()), message.getValues());
			if (content != null) {
				sb.append(content);
			} else {
				sb.append(message.getKey());
			}
			sb.append("</td>\n");
			sb.append("</tr>\n");
		}
		return sb.toString();
	}

	public final String getBundle() {
		return this.bundle;
	}

	public final String getLocale() {
		return this.locale;
	}

	public final void release() {
		super.release();
		this.bundle = null;
		this.locale = null;
	}

	public final void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public final void setLocale(String locale) {
		this.locale = locale;
	}

}
