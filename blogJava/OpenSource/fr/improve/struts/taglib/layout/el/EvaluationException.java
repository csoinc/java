package fr.improve.struts.taglib.layout.el;

/**
 * @author jer80876
 */
public class EvaluationException extends RuntimeException {	
	public EvaluationException() {
		super();
	}
	public EvaluationException(String in_reason) {
		super(in_reason);
	}
}
