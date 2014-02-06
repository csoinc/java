package fr.improve.struts.taglib.layout.event;

import javax.servlet.jsp.JspException;

/**
 * Interface implemented by struts-layout tags that want to collaborate.
 * @author jnribette
 */
public interface ComputeLayoutSpanEventListener {

	public Integer computeColspan(ComputeLayoutSpanEvent in_event) throws JspException;
	
}
