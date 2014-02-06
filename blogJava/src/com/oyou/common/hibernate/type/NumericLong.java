package com.oyou.common.hibernate.type;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;

public class NumericLong extends org.hibernate.type.LongType {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis();

	/**
	 * 
	 */
	public NumericLong() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.type.NullableType#set(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	public void set(PreparedStatement arg0, Object arg1, int arg2) throws SQLException {
		arg0.setBigDecimal(arg2, new BigDecimal(((Long) arg1).doubleValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.type.NullableType#sqlType()
	 */
	public int sqlType() {
		return Types.NUMERIC;
	}

	/**
	 * get type name
	 */
	public String getName() {
		return "NumericLong";
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.type.NullableType#get(java.sql.ResultSet, java.lang.String)
	 */
	public Object get(ResultSet arg0, String arg1) throws SQLException {
		Object value = arg0.getObject(arg1);
		if (value != null && !(value instanceof BigDecimal)) {
			throw new RuntimeException("Column " + arg1 + " is improperly mapped to " + getClass().getName()
					+ ".  This custom type should only be mapped to Sybase Numeric Identifier columns.");
		}

		return super.get(arg0, arg1);
	}

}
