package com.oyou.common;

import java.io.Serializable;

public abstract class Entity implements Serializable {
	static final long serialVersionUID = 1; 

	/**
	 * the entity id, normal equal the primary key
	 */
	private Long id;

	/**
	 * default constructor
	 */
	public Entity() {
		super();
	}

	/**
	 * constructor and set the id
	 * 
	 * @param l
	 */
	public Entity(Long l) {
		super();
		id = l;
	}

	/**
	 * object clone()
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
		if (id == null) {
			return super.equals(obj);
		} else {
			if (obj == null)
				return false;
			if (obj == this)
				return true;
			if (!(this.getClass().isInstance(obj)))
				return false;
			return false;
		}
	}

	/**
	 * return the entity id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return object hash code
	 */
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	/**
	 * set id
	 * 
	 * @param l
	 */
	public void setId(Long l) {
		id = l;
	}

	/**
	 * implement toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ID: " + id);
		return buffer.toString();
	}

	/**
	 * validate the entity, the sub class may need
	 */
	public void validate() {
	}

}
