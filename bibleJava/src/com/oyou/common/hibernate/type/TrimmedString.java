package com.oyou.common.hibernate.type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class TrimmedString extends org.hibernate.type.StringType {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis();

	/**
	 * get value from result set
	 * @param rs
	 * @param name
	 */
	public Object get(ResultSet rs, String name) throws SQLException {
		Object obj = super.get(rs, name);
		if (obj != null) {
			obj = ((String)obj).trim();
		}
		return obj;
	}

	/**
	 * get type name
	 */
	public String getName() {
		return "TrimString";
	}

}
