package com.oyou.common.exception;

final public class BusinessError {

    String key;

    Object values[] = null;

    public BusinessError() {
    }	 

    /**
     * @param key
     */
    public BusinessError(String key) {
        this.key = key;
    }

    /**
     * @param key
     * @param value
     */
    public BusinessError(String key, Object value) {
        this.key = key;
        this.values = new String[1];
        this.values[0] = value;
    }

    /**
     * @param key
     * @param values
     */
    public BusinessError(String key, Object values[]) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                values[i] = "";
            }    
        }
        this.key = key;
        this.values = values;
    }

    /**
     * @param key
     * @param value0
     * @param value1
     */
    public BusinessError(String key, Object value0, Object value1) {
        if (value0 == null) {
            value0 = "";
        }    
        if (value1 == null) {
            value1 = "";
        }    
        this.key = key;
        this.values = new String[2];
        this.values[0] = value0;
        this.values[1] = value1;
    }

    /**
     * @param key
     * @param value0
     * @param value1
     * @param value2
     */
    public BusinessError(String key, Object value0, Object value1, Object value2) {
        this(key, new Object[] { value0, value1, value2 });
    }

    /**
     * @param key
     * @param value0
     * @param value1
     * @param value2
     * @param value3
     */
    public BusinessError(String key, Object value0, Object value1, Object value2, Object value3) {
        this(key, new Object[] { value0, value1, value2, value3 });
    }

    /**
     * @return
     */
    public String getKey() {
        return key;
    }

    public Object[] getValues() {
        values = (values == null)? new String[1] : values;
        return values;
    }

    public void setKey(String key) {
		this.key = key;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getClass().getName());
        buffer.append("@");
        buffer.append(hashCode());
        buffer.append("[key=");
        buffer.append(String.valueOf(key));
        buffer.append(", values=");
        if (values == null)
            buffer.append("null");
        else {
            buffer.append("[");
            for (int i = 0; i < values.length;) {
                buffer.append(String.valueOf(values[i++]));
                if (i == values.length)
                    break;
                buffer.append(", ");
            }
            buffer.append("]");
        }
        buffer.append("]");
        return buffer.toString();
    }
    
}