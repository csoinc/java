package fr.improve.struts.taglib.layout.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Dummy action doing nothing. <br>
 */
public class DefaultAction extends DispatchAction {
	public ActionForward execute(ActionMapping mapping,
				 ActionForm form,
				 HttpServletRequest request,
				 HttpServletResponse response)
	throws IOException, ServletException {

		StringBuffer buffer = new StringBuffer("<html><body><h1>DefaultAction</h1><br />Parameters:<br /><table>");	
		Enumeration parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			buffer.append("<tr><td>");
			String parameter = (String) parameters.nextElement();
			buffer.append(parameter);
			buffer.append("</td><td>");
			buffer.append(request.getParameter(parameter));
			buffer.append("</td></tr>\n");
		}
		buffer.append("</tr><tr><td colspan=\"2\">&nbsp;</td></tr>");
		
		buffer.append("<tr><td>Auth Type:</td><td>");
		buffer.append(request.getAuthType());
		buffer.append("</td></tr><tr><td>Character Encoding:</td><td>");
		buffer.append(request.getCharacterEncoding());
		buffer.append("</td></tr><tr><td>Content Type:</td><td>");
		buffer.append(request.getContentType());
		buffer.append("</td></tr><tr><td>Context Path:</td><td>");
		buffer.append(request.getContextPath());
		buffer.append("</td></tr></table>");

		buffer.append("<br />Headers:<br /><table>");
		Enumeration headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			buffer.append("<tr><td>");
			String header = (String) headers.nextElement();
			buffer.append(header);
			buffer.append("</td><td>");
			buffer.append(request.getHeader(header));
			buffer.append("</td></tr>\n");
		}
		buffer.append("</tr></table>");

		
		buffer.append("</body></html>");


		
		response.getWriter().println(buffer.toString());
		
		return null;
	}
}
