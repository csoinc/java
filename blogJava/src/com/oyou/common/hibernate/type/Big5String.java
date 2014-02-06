package com.oyou.common.hibernate.type;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.oyou.common.reader.Encoder;

/**
 * @author	Owen Ou
 */
public class Big5String extends org.hibernate.type.StringType {
	static final long serialVersionUID = Calendar.getInstance().getTimeInMillis();

	/**
	 * get value from result set
	 * @param rs
	 * @param name
	 */
	public Object get(ResultSet rs, String name, boolean deprecated) throws SQLException {
		Object obj = super.get(rs, name);
		try {
			if (obj != null) {
				obj = new String(((String)obj).getBytes(Encoder.BIG5));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * get type name
	 */
	public String getName() {
		return "Big5String";
	}

}
