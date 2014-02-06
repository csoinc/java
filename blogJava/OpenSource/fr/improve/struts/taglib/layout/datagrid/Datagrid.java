/*
 * Created on 5 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * The class Datagrid allows to easily manipulate a list of objects.
 * The user simply set the object list, the class of the object, 
 * and get back the list of added, removed or modified objects.
 * 
 * @author jnribette
 */
public abstract class Datagrid implements Serializable {
	// --------------- public interface -----------------------
	
	/**
	 * Predefined state REMOVED. 
	 */
	public static final String REMOVED = "removed";
	
	/**
	 * Predefined state SELECTED.
	 */
	public static final String SELECTED = "selected";
	
	/**
	 * Get a new datagrid.
	 */
	public static Datagrid getInstance() {
		return new DatagridImpl();
	}			
	
	/**
	 * Set the data of the datagrid.
	 */
	public abstract void setData(List in_list);
	
	/**
	 * Set the class of the object in the datagrid. 
	 * Used to create the new object.
	 * The class must have a public non argument constructor.
	 */
	public abstract void setDataClass(Class in_class);
	
	/**
	 * Set the state of the object in the datagrid at the specified position.  
	 * @param in_index the object position in the datagrid.
	 * @param in_state the object state
	 */
	public abstract void setDataState(int in_index, String in_state);
	
	/**
	 * Return the data that have been deleted.
	 */
	public abstract Collection getDeletedData(); 

	/**
	 * Return the new data.
	 */
	public abstract Collection getAddedData();
	
	/**
	 * Return the modified data.
	 */
	public abstract Collection getModifiedData();
	
	/**
	 * Return the selected data.
	 */
	public abstract Collection getSelectedData();
	
	/**
	 * Return the data having the specified state.
	 */
	public abstract Collection getDataWithState(String in_state);
	
	/** 
	 * Prepare the datagrid for an update.
	 */
	public abstract void preUpdate();
}
