package fr.improve.struts.taglib.layout.datagrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a column type.
 * 
 * @author dev
 */
public class ColumnType {
	/**
	 * Input text column type.
	 */
	public static final ColumnType TEXT = new ColumnType("text");
	
	/**
	 * Checkbox column type.
	 */
	public static final ColumnType CHECKBOX = new ColumnType("checkbox");
	
	/**
	 * Radio column type.
	 */
	public static final ColumnType RADIO = new ColumnType("radio");
	
	/**
	 * Select column type.
	 */
	static final String SELECT = "select";
	
	/**
	 * Select column type (use the values attribute).
	 */
	static ColumnType select() {
		ColumnType type =  new ColumnType(SELECT);
		type.values = new ArrayList();
		return type;
	}
	
	private String type;
	private List values;
	
	private ColumnType(String in_type) {
		type = in_type;
	}
	
	public String getType() {
		return type;
	}
	
	public List getValues() {
		return values;
	}
	
	public void setValues(List in_values) {
		values =in_values;
	}
}
