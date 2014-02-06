package fr.improve.struts.taglib.layout.el;

import javax.servlet.jsp.PageContext;

/**
 * @author jer80876
 */
public interface Block {
	public String evaluate(PageContext in_pg) throws EvaluationException;
}
