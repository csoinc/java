package fr.improve.struts.taglib.layout.field.ajax.select;

/**
 * <p>An <strong>Option</strong> is a JavaBean associated with an instance of 
 * <code>fr.improve.struts.taglib.layout.field.ajax.select.SelectOptionAction</code>. 
 * It holds value and label of an Option element.
 * 
 * @author nsoule
 */
public class Option {
	
	private String value;
	private String label;
	
	//---------------------------------------------------- getters / setters 
	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
