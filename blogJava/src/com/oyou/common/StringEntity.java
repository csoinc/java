package com.oyou.common;

import java.io.Serializable;

public abstract class StringEntity implements Serializable {
	static final long serialVersionUID = 1; 

	/**
	 * the id, normaly equal the primary key
	 */
	private String id;

	/**
	 * constructor
	 */
	public StringEntity() {
		super();
	}

	/**
	 * constructor and set the id
	 * @param b
	 */
	public StringEntity(String b) {
		super();
		id = b;
	}

	/**
	 * object clone
	 */
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * object equal
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof StringEntity))
			return false;
		if (id.equals(obj))
			return true;
		return false;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return hash code 
	 */
	public int hashCode() {
		return id.hashCode();
	}

	/**
	 * @param integer
	 */
	public void setId(String integer) {
		id = integer;
	}

	/**
	 * toString help the debug of this entity
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID: " + id);
		return buffer.toString();
	}

	/**
	 * sub class may need implement when have business rule of this entity
	 * @throws Exception
	 */
	public void validate() {
	}

}
