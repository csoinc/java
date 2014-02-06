/*
 * Created on 5 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Implementation of a Datagrid.</p>
 * 
 * <p>How it works:</p>
 * 
 * <ul>
 * <li>The data and dataclass are first set with the setData and setDataclass methods.</li>
 * <li>The datagrid tag uses the getData method to get the data, build an iterator and display the datagrid.</li>
 * <li>When the form is submitted, the set(String in_property, String in_index, Object in_value) method 
 * is called by BeanUtils to update the object properties and states.</li>
 * <li>The use calls the different get methods of the Datagrid to get back the objects.
 * </ul>
 * 
 * @author jnribette
 */
public class DatagridImpl extends Datagrid implements DynaBean {
    /**
     * Log object.
     */
    private static Log LOG = LogFactory.getLog(DatagridImpl.class);
    
    /**
     * Initial data list
     */
    private List data;
    
    /**
     * Added data.
     */
    private Map addedData;
    
    /**
     * Updated data.
     */
    private Map modifiedData;
    
    /**
     * Data with a specified state.
     * The Map contains (key/value) entries in the form (state (String) / objects having this state (List))   
     */
    private Map dataWithStates = new HashMap();
    
    /**
     * Data with a specified state.
     * The map contains (key/value) entries in the form (object index (Integer) / state (String))
     */
    private Map indexWithStates = new HashMap();
    
    /**
     * List of objects that have been added and removed at the same time.
     * Used to prevent to have the objects in both the added and removed lists. 
     */
    private Set addedAndRemoved = new HashSet();
    
    /**
     * Class of the data being manipulated.
     */
    private Class dataClass;
    
    /**
     * Dynaclass.
     * This dynaclass is created to have the same properties as the class "dataClass" plus a "dataState" property.  
     */
    private DynaClass dataDynaClass;
    
    /**
     * Set of editable boolean properties.
     */
    private Set booleans = new HashSet();
    
    /**
     * Map of originals boolean values.
     */
    private Map booleanStates = new HashMap();
    
    
    /**
     * A new datagrid, whose data are not defined yet.
     */
    private static final int STATUS_NEW = 0;
    
    /**
     * A datagrid whose data have just been set.
     */
    private static final int STATUS_SET = 1;
    
    /**
     * A datagrid whose state has been updated by Struts.
     */
    private static final int STATUS_DIRTY = 2;
    
    /**
     * A datagrid whose state has been read.
     */
    private static final int STATUS_READ = 3;
    
    /**
     * Current status of the datagrid.
     */
    private int status = STATUS_NEW;
    
   
    
    /**
     * Non public Constructor
     */
    DatagridImpl() {
        
    }
    
    // ----------------------------- PROTECTED METHODS ------------------------
    
    /**
     * Register an object in the modified Map.
     * @param in_index  the index of the object to modify.
     * @return          the object to modify.
     */
    protected Object getModifiedObject(int in_index) {
        // Get the object in the initial object list.
        Object o = data.get(in_index);
        
        // Put the object in the modified object map.
        modifiedData.put(new Integer(in_index), o);
        
        // Return the object.
        return o;
    }
    
    /**
     * Register an object in the new object Map.
     * 
     * If the object state is REMOVED, the new object is not registered in the Map.
     * 
     * @param in_index  the index of the new object.
     * @return          the new object. 
     */
    protected Object getAddedObject(int in_index) {
        Integer lc_int = new Integer(in_index);
        
        // Check if an object had not been already registered for this index.
        Object o = addedData.get(lc_int);
        
        if (o==null) {
            if (dataClass==null) {
                throw new RuntimeException("dataClass property is not set");
            }
            try {
                // No object registered, create a new one.
                o = dataClass.newInstance();
            } catch (InstantiationException e) {
                throwException(e);
            } catch (IllegalAccessException e) {
                throwException(e);
            }
            
            // If the object was removed (not very likely but possible), do not register it into the list.
            if (!addedAndRemoved.contains(lc_int)) {
                addedData.put(lc_int, o);
            }
        }
        
        // Return the new object.
        return o;
    }
    
    private void throwException(Exception e) {
        LOG.error("Fail to create an object of class " + dataClass.getName(), e);
        throw new RuntimeException("A " + e.getClass().getName() + " occured while creating an object of class " + dataClass.getName() + " : " + e.getMessage());
    }
    
    /**
     * Make sure an object has not been added and removed in the same transaction.
     * @param in_index  the object index
     * @param in_value  the object state
     * @return true if the object has not been added and removed 
     */
    protected boolean checkNotAddedAndRemoved(int in_index, Object in_value) {
        if (in_index >= data.size() && Datagrid.REMOVED.equals(in_value)) {
            Integer lc_int = new Integer(in_index);
            addedData.remove(lc_int);
            addedAndRemoved.add(lc_int);
            return false;
        } else {
            return true;
        }
    }   
    
    // ------------------------- PUBLIC METHODS USED BY BEANUTILS -------------
    
    public DynaClass getDynaClass() {       
        return dataDynaClass;
    }   
    
    /**
     * Update the datagrid when a form is submitted. 
     */
    public void set(String in_propertyName, int in_index, Object in_value) {
    	moveToStatus(STATUS_DIRTY);
        if (in_index < 0 ) {
            // Illegal index.
            throw new IllegalArgumentException("Negative in_index !");
        }
        
        if ("dataState".equals(in_propertyName)) {
            // Update the state of an object.
            // Do nothing if the object has been both added and removed.
            if (in_value!=null) {
                if (checkNotAddedAndRemoved(in_index, in_value)) {
                    setDataState(in_index, in_value.toString());
                }
            }
            // Return : we don't want to register the object
            // in the added or modified object map.
            return;
        }
        
        // Ok, the property of an object is being updated.
        Object o;
        if (in_index >= data.size()) {          
            // Addition, register the object has a new object.
            o = getAddedObject(in_index);
        } else {
        	if (booleans.contains(in_propertyName)) {
        		// Special case for boolean properties : 
        		// can not determinate if there has been a change or not.
        		o = data.get(in_index);
        	} else {        		
	            // Update, register the object has a modified object.
	            if (in_value.equals(get(in_propertyName, in_index))) {
	                // Do nothing if the property has not changed.
	                return;             
	            }
	            o = getModifiedObject(in_index);
        	}
        }
        try {
            // Set the property value.
            PropertyUtils.setProperty(o, in_propertyName, in_value);
        } catch (IllegalAccessException e) {
            LOG.error("Could not set new object property value", e);
        } catch (InvocationTargetException e) {
            LOG.error("Could not set new object property value", e);
        } catch (NoSuchMethodException e) {
            LOG.error("Could not set new object property value", e);
        }
    }   

    /**
     * Data display.
     * Return the value of the property in_propertyName of the object at index in_index.
     */
    public Object get(String in_propertyName, int in_index) {
        Object o = data.get(in_index);
        Object ret = null;
        try {
            ret = PropertyUtils.getProperty(o, in_propertyName);
        } catch (IllegalAccessException e) {
            LOG.error("Could not get object property value", e);
        } catch (InvocationTargetException e) {
            LOG.error("Could not get object property value", e);
        } catch (NoSuchMethodException e) {
            LOG.error("Could not get object property value", e);
        }
        return ret;
    }
    
    // ----------------------------- PUBLIC METHODS ---------------------------

    /**
     * Set the data.
     */
    public void setData(List in_list) {
        data = in_list;     
        addedData = new TreeMap();
        modifiedData = new HashMap();
        dataWithStates.clear();
        indexWithStates.clear();
        addedAndRemoved.clear();
        status = STATUS_SET;
    }
    
    /**
     * Set the dataclass.
     * Build a Dynaclass from the class properties.
     */
    public void setDataClass(Class in_class) {
        // Keep the class.
        dataClass = in_class;
        
        // Build a fake DynaClass for this datagrid object.         
        PropertyDescriptor[] lc_descriptors = PropertyUtils.getPropertyDescriptors(in_class);
        List lc_list = new ArrayList(lc_descriptors.length+1);
        for (int i = 0; i < lc_descriptors.length; i++) {
            PropertyDescriptor lc_descriptor = lc_descriptors[i];
            if (lc_descriptor.getWriteMethod()!=null) {
                DynaProperty lc_property = new DynaProperty(lc_descriptor.getName(), lc_descriptor.getPropertyType());
                lc_list.add(lc_property);
            }
        }
        
        // Add a dataState property.
        DynaProperty lc_property = new DynaProperty("dataState", String.class);
        lc_list.add(lc_property);
        
        DynaProperty[] lc_properties = (DynaProperty[]) lc_list.toArray(new DynaProperty[lc_list.size()]);
        dataDynaClass = new BasicDynaClass(in_class.getName(), BasicDynaBean.class, lc_properties);             
    }
    
    /**
     * Set the an object state.
     */
    public void setDataState(int in_index, String in_state) {
        if (in_state==null) {
            throw new IllegalArgumentException("in_state null");
        }
        Integer lc_int = new Integer(in_index);
        
        // Clear the old state map.
        String lc_oldState = (String) indexWithStates.get(lc_int);
        if (lc_oldState!=null) {
            Collection lc_list = (Collection) dataWithStates.get(lc_oldState);
            if (lc_list!=null) {
                lc_list.remove(lc_int);
            }
        }
        
        // Update the new state map.
        Collection lc_list = (Collection) dataWithStates.get(in_state);
        if (lc_list==null) {
            lc_list = new HashSet();
            dataWithStates.put(in_state, lc_list);
        }       
        lc_list.add(lc_int);
        
        // Keep the states of the line. 
        indexWithStates.put(lc_int, in_state);
    }
    
    public Collection getData() {
        Collection lc_collection = new ArrayList();
        lc_collection.addAll(data);
        lc_collection.addAll(addedData.values());
        return lc_collection;
    }

    /**
     * Return the deleted data.
     */
    public Collection getDeletedData() {
    	moveToStatus(STATUS_READ);
        return getDataWithState(REMOVED);
    }
    
    /**
     * Return the object having the specified state.
     */
    public Collection getDataWithState(String in_state) {
    	moveToStatus(STATUS_READ);
    	
        // Get the list of the number of the lines we are interested in.        
        Collection goodStateList = (Collection) dataWithStates.get(in_state);
                
        if (goodStateList==null || goodStateList.isEmpty()) {
            // No lines with the specified state.
            return Collections.EMPTY_LIST;
        } else {
            // Ok, get each object in "data" whose index is specified in "goodStateList", and put it into a list.
            ArrayList lc_list = new ArrayList();
            Iterator lc_it = goodStateList.iterator();
            Object o;
            while (lc_it.hasNext()) {
                Integer lc_int = (Integer)lc_it.next();
                if (lc_int.intValue()<data.size()) {
                     o = data.get(lc_int.intValue());
                     lc_list.add(o);
                } else {
                    o = addedData.get(lc_int);
                    lc_list.add(o);
                }               
            }
            return lc_list;         
        }       
    }
    
    /**
     * Return the selected data.
     */
    public Collection getSelectedData() {
    	moveToStatus(STATUS_READ);
        return getDataWithState(SELECTED);      
    }

    /**
     * Return the added data.
     */
    public Collection getAddedData() {
    	moveToStatus(STATUS_READ);
        return addedData.values();      
    }

    /**
     * Return the modified data.
     */
    public Collection getModifiedData() {
    	moveToStatus(STATUS_READ);
        return modifiedData.values();
    } 
    
    public String getObjectState(int in_index) {
        String lc_state = (String) indexWithStates.get(new Integer(in_index));      
        return lc_state == null ? "" : lc_state;
    }   
    
    // -------------------------- Useless DynaBean interface -----------------------------// 
    
    /**
     * Do nothing  
     */
    public boolean contains(String name, String key) {
        // Do nothing.
        return false;
    }

    /**
     * Do nothing  
     */
    public Object get(String name, String key) {
        // Do nothing
        return null;
    }

    /**
     * Do nothing  
     */
    public Object get(String name) {
        // Do nothing
        return null;
    }
    
    /**
     * Do nothing  
     */
    public void remove(String name, String key) {
        // Do nothing
    }
    
    /**
     * Do nothing  
     */
    public void set(String name, Object value) {
        // Do nothing
    }
    
    /**
     * Do nothing  
     */
    public void set(String name, String key, Object value) {
        // Do nothing.
    }
    
    /**
     * Prepare for an update.
     */
    public void preUpdate() {
    	Iterator lc_it = booleans.iterator();
    	Boolean falseValue = Boolean.FALSE;
    	while (lc_it.hasNext()) {
    		String lc_booleanProperty = (String) lc_it.next();
    		Map objects = (Map) booleanStates.get(lc_booleanProperty);
    		if (objects==null) {
    			objects = new HashMap();
    			booleanStates.put(lc_booleanProperty, objects);
    		}
    		Iterator lc_elements = data.iterator();
    		while (lc_elements.hasNext()) {
    			Object lc_element = lc_elements.next();
    			try {
    				objects.put(lc_element, PropertyUtils.getProperty(lc_element, lc_booleanProperty));
					PropertyUtils.setProperty(lc_element, lc_booleanProperty, falseValue);
				} catch (Exception e) {
					LOG.error("Fail to reset property " + lc_booleanProperty, e);
				}
    		}
    	}
    	status = STATUS_DIRTY;
    }
    
    private void postUpdate() {
    	Iterator lc_it = booleans.iterator();
    	while (lc_it.hasNext()) {
    		String lc_booleanProperty = (String) lc_it.next();
    		Map objects = (Map) booleanStates.get(lc_booleanProperty);
    		Iterator lc_elements = data.iterator();
    		int i = 0;
    		while (lc_elements.hasNext()) {    			
    			try {
    				Object lc_element = lc_elements.next();
        			Boolean oldValue = (Boolean) objects.get(lc_element);
					Boolean newValue = (Boolean) PropertyUtils.getProperty(lc_element, lc_booleanProperty);
					if (!oldValue.equals(newValue)) {
						getModifiedObject(i);
					}
				} catch (Exception e) {
					LOG.error("Fail to check property " + lc_booleanProperty, e);
				}
				i++;
    		}
    	}
    }
    
    private void moveToStatus(int in_status) {
    	if (status!=in_status) {
    		switch (in_status) {
    			case STATUS_DIRTY:
    				preUpdate();
    				break;
    			case STATUS_READ:
    				postUpdate();
    				break;
    		}
    		status = in_status;
    	}
    	
    }
    
    void addBooleanProperty(String in_property) {
    	booleans.add(in_property);				
    }
}
