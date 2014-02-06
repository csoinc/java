package com.oyou.common.hibernate.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.JDBCException;

import com.oyou.common.hibernate.HibernateSession;

/**
 * Helper class for JDBC call
 * @author  Owen Ou
 */
public class JDBCHelper {
	private static final Log log = LogFactory.getLog(JDBCHelper.class);

	/**
	 * execute sql 
	 * @param nsql
	 */
	public static void executeSQL(String nsql) {
		try {
			HibernateSession.getInstance().getSession().connection().createStatement().execute(nsql);
		} catch (SQLException se) {
			if (log.isErrorEnabled()) {
				log.error(se.getMessage());
			}
			JDBCException je = new JDBCException(se.getMessage(), se);
			throw je;
		}
	}

	/**
	 * get the column value by name and type
	 * @param rs
	 * @param column
	 * @param type
	 * @return
	 */
	public static Object getColumnValue(ResultSet rs, String column, int type) {
		Object obj = null;
		try {
			switch (type) {
				case Types.BIGINT :
					obj = rs.getLong(column);
					break;
				case Types.BIT :
					obj = new Boolean(rs.getBoolean(column));
					break;
				case Types.BOOLEAN :
					obj = rs.getBoolean(column);
					break;
				case Types.CHAR :
					obj = rs.getString(column);
					if (obj != null)
						obj = ((String) obj).trim();
					break;
				case Types.DATE :
					obj = rs.getDate(column);
					break;
				case Types.DOUBLE :
					obj = rs.getDouble(column);
					break;
				case Types.INTEGER :
					obj = new Integer(rs.getInt(column));
					break;
				case Types.LONGVARCHAR :
					obj = rs.getString(column);
					if (obj != null)
						obj = ((String) obj).trim();
					break;
				case Types.NUMERIC :
					obj = rs.getBigDecimal(column);
					break;
				case Types.SMALLINT :
					obj = rs.getShort(column);
					break;
				case Types.TIMESTAMP :
					obj = rs.getTimestamp(column);
					break;
				case Types.TINYINT :
					obj = rs.getShort(column);
					break;
				case Types.VARCHAR :
					obj = rs.getString(column);
					if (obj != null)
						obj = ((String) obj).trim();
					break;
				default :
					obj = "Unknow type: " + type;
					break;
			}
		} catch (SQLException se) {
			if (log.isErrorEnabled()) {
				log.error(se.getMessage());
			}
			JDBCException je = new JDBCException(se.getMessage(), se);
			throw je;
		}
		return obj;
	}

	/**
	 * Get one row data and save them to HashMay
	 * @param rs
	 * @return
	 */
	public static List<Map<String,Object>> getResultSetList(ResultSet rs) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			rs.beforeFirst();
			while(rs.next()) {
				list.add(getColumnValues(rs));
				if (rs.isLast()) break;
			}
		} catch (SQLException se) {
			if (log.isErrorEnabled()) {
				log.error(se.getMessage());
			}
			JDBCException je = new JDBCException(se.getMessage(), se);
			throw je;
		}
		return list;
	}

	/**
	 * Get one row data and save them to HashMay
	 * @param rs
	 * @return
	 */
	public static Map<String, Object> getColumnValues(ResultSet rs) {
		Map<String, Object> hm = new HashMap<String, Object>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			for (int i = 1; i <= columns; i++) {
				hm.put(rsmd.getColumnName(i).toUpperCase(),getColumnValue(rs, rsmd.getColumnName(i),rsmd.getColumnType(i)));
			}
		} catch (SQLException se) {
			if (log.isErrorEnabled()) {
				log.error(se.getMessage());
			}
			JDBCException je = new JDBCException(se.getMessage(), se);
			throw je;
		}
		return hm;
	}
	
}
