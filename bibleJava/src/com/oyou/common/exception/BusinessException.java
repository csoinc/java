package com.oyou.common.exception;

import java.util.Calendar;

import com.oyou.common.RecoverableException;

public class BusinessException extends RecoverableException {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis(); 
    
    BusinessErrors errors = null;
    
    public BusinessException() {  
    	super();
        this.errors = new BusinessErrors();
    }

    public BusinessException(String errMessage) {  
    	super(errMessage);
        this.errors = new BusinessErrors();
    }
    
    /**
     * @param errors
     */
    public BusinessException( BusinessErrors errors ) {
        super();
        this.errors = errors;
    }

    /**
     * @param error
     */
    public BusinessException( BusinessError error ) {
        super();
        this.errors = new BusinessErrors( error );
    }
    
    /**
     * @return
     */
    public BusinessErrors getBusinessErrors() { return errors; }

    /**
     * @param errors
     */
    public void setBusinessErrors(BusinessErrors errors) {
        this.errors = errors;
    }

    /**
     * @param key
     */
    public void addError(String key) {
        errors.add(new BusinessError(key));
    }
    
    /**
     * @param key
     * @param value0
     */
    public void addError(String key, Object value0) {
        errors.add(new BusinessError(key, new Object[] { value0 }));
    }

    /**
     * 
     * @param key
     * @param value0
     * @param value1
     */
    public void addError(String key, Object value0, Object value1) {
        errors.add(new BusinessError(key, new Object[] { value0, value1 }));
    }

    /**
     * 
     * @param key
     * @param value0
     * @param value1
     * @param value2
     */
    public void addError(String key, Object value0, Object value1, Object value2) {
        errors.add(new BusinessError(key, new Object[] { value0, value1, value2 }));
    }

    /**
     * @param key
     * @param value0
     * @param value1
     * @param value2
     * @param value3
     */
    public void addError(String key, Object value0, Object value1, Object value2, Object value3) {        
        errors.add(new BusinessError(key, new Object[] { value0, value1, value2, value3 }));
    }
    
    /**
     * @param key
     * @param values
     */
    public void addError(String key, Object[] values) {
        errors.add(new BusinessError(key, values));
    }
    
    public boolean hasErrors(){
        if (errors.getList().size() > 0) return true;
        else return false;
    }
    
}