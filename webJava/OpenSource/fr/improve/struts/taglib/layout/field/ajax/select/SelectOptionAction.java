package fr.improve.struts.taglib.layout.field.ajax.select;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * The base select Action which creates a double-combo linked list with Ajax.
 * 
 * @author nsoule
 *
 */
public abstract class SelectOptionAction extends Action {
	
	public ActionForward execute(ActionMapping in_mapping, 
			                     ActionForm in_form, 
			                     HttpServletRequest in_request, 
			                     HttpServletResponse in_response) 
	     throws Exception {
		
		in_response.addHeader("Content-Type", "text/xml");
    	
		// r�cup�re la liste des param�tres de la requ�te
    	String valueSelected = in_request.getParameter("valueSelected");
    	String formName = in_request.getParameter("formName");
    	String elementName = in_request.getParameter("elementName");
    	    	
    	String suggestions = getXMLOptionList(in_request, valueSelected, 
    			                              formName, elementName, 
    			                              "ISO-8859-1");
    	
    	in_response.getOutputStream().print(suggestions);
    	
    	return null;	
	}
	
	/**
	 * Return an XML document corresponding to a list of options.
	 * @param in_request the HTTP request we are processing
	 * @param in_value    the value of a selected item from the first selection list
	 * @param in_formName the name of the form that whe need to update
	 * @param elementName the name of the second selection list to be filled
	 * @param in_encoding  the XML document encoding. 
	 */
	protected String getXMLOptionList(HttpServletRequest in_request, 
			                          String in_value, 
			                          String in_formName, 
			                          String elementName, 
			                          String in_encoding) {
		
		// get options list by the selected item and current HTTP request
		Option[] options = getOptionList(in_request, in_value);
		
		StringBuffer res = new StringBuffer("<?xml version=\"1.0\" encoding=\"")
		                               .append(in_encoding).append("\" ?>\n");
						
		res.append("<selectChoice>");
			res.append("<selectElement>");
			res.append("<formName>").append(in_formName).append("</formName>");
			res.append("<formElem>").append(elementName).append("</formElem>");
		res.append("</selectElement>");
		
		if (options!=null) {			
			for (int i=0; i<options.length; i++) {
				Option op = options[i];	
				res.append("<entry>");
				res.append("<optionText>").append(op.getLabel()).append("</optionText>");
				res.append("<optionValue>").append(op.getValue()).append("</optionValue>");		
				res.append("</entry>");
			}	
		}
		
		res.append("</selectChoice>");		
		
		return res.toString();		
	}
		
	/**
	 * Return an array of <code>fr.improve.struts.taglib.layout.field.ajax.select.Option</code>
	 * according to a selected item.
	 * @param in_request the HTTP request we are processing
	 * @param in_item  a selected item of the first selection list.	
	 */
	public abstract Option[] getOptionList(HttpServletRequest in_request, String in_item);

}
