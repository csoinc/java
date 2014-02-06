package fr.improve.struts.taglib.layout.skin;

/**
 * @author jnribette
 *
 */
public class BadSkinConfigurationException extends RuntimeException {
	private Exception exception;
	public BadSkinConfigurationException(String in_string) {
		super(in_string);	
	}
	public BadSkinConfigurationException(Exception in_exception) {
		super("caused by");
		exception = in_exception;
	}
	public Exception getException() {
		return exception;	
	}
	public String getMessage() {
		if (exception==null) {
			return super.getMessage();
		} else {
			return super.getMessage() + " " + exception.toString(); 
		}
	}
}
