/*
 * Created on 13 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.util;

import java.util.Map;

/**
 * @author jnribette
 */
public interface IDatagridRenderer extends CollectionInterface {
	/**
	 * Default style of a row.
	 */
	public String getRowStyleClass();
	
	/**
	 * Style of a row when row color alernance is used.
	 */
	public String getRowStyleClass2();
	
	/**
	 * Style of a specific cell.
	 */
	public String getColumnStyleClass(int in_columnNumber);
	
	/**
	 * Style of specific rows.
	 */
	public Map getRowStyleClassMap();	
}
