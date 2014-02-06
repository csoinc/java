package com.oyou.common.exception;

import java.util.ArrayList;
import java.util.List;

final public class BusinessErrors {

	private List<BusinessError> list;

    public BusinessErrors() {
        list = new ArrayList<BusinessError>();
    }
	
    public BusinessErrors(BusinessError value) { 
        list = new ArrayList<BusinessError>();
        list.add(value);
    }

    public void add(BusinessError value) {
        list.add(value);
    }

    public void clear() { list.clear(); }

	public List<BusinessError> getList() {
		return list;
	}

	public void setList(List<BusinessError> list) {
		this.list = list;
	}

}