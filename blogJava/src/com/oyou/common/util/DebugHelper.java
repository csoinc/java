package com.oyou.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.Entity;

/**
 * Debug view helper 
 * @author  Owen Ou
 */
public class DebugHelper {
	private static final Log log = LogFactory.getLog(DebugHelper.class);

	public static String getJSONString(Object obj) {
		if (obj == null) return null;
		StringBuffer sb = new StringBuffer();
		sb.append(getClassShortName(obj.getClass().getName()) + "[");
		sb.append(getJSONString(new StringBuffer(), obj, obj.getClass()));
		sb.append("]");
		return sb.toString();	
	}	
	
	private static String getJSONString(StringBuffer sb, Object obj, Class clz) {
		Method[] methods = clz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String name = method.getName();
			try {
				if (method.getModifiers() == Modifier.PUBLIC && name.startsWith("get") && methodFilter(name)) {
					sb.append(name.substring("get".length()));			
					sb.append(":");
					Object value = method.invoke(obj, new Object[]{});
					sb.append(getObjectValueByType(value));
					sb.append(",");
				} 
			} catch (IllegalAccessException iae) {
				iae.printStackTrace();
			} catch (InvocationTargetException ite) {
				ite.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(">>Error when invoke method " + name);
			}
		}
		if (clz.getSuperclass() != null) getJSONString(sb, obj, clz.getSuperclass());
		return sb.toString();	
	}

	public static String getClassShortName(String clzName) {
		StringTokenizer st = new StringTokenizer(clzName, ".");
		String shortName = null;
		while (st.hasMoreTokens()) {
			shortName = st.nextToken();
		}
		if (shortName.indexOf("$") != -1) {
			shortName = shortName.substring(0, shortName.indexOf("$"));
		}
		return shortName;
	}

    public static boolean methodFilter(String name) {
    	if (name == null || name.equals(" ")) return false;
    	String[] noMethods = {"getCallback", "getCallbacks"}; 
		for (int i = 0; i < noMethods.length; i++)
		{
			String method = noMethods[i];
			if (name.equalsIgnoreCase(method)) return false;
		}    	
    	return true;
    }

	public static String getObjectValueByType(Object obj) {
		StringBuffer sb = new StringBuffer();
		if (obj != null) {
			if (obj instanceof String)
				sb.append((String)obj);
			else if (obj instanceof Long)
				sb.append(((Long)obj).toString());
			else if (obj instanceof Boolean)
				sb.append(((Boolean)obj).booleanValue());
			else if (obj instanceof Date)
				sb.append(((Date)obj).toString());
			else if (obj instanceof Timestamp)
				sb.append(((Timestamp)obj).toString());
 			else if (obj instanceof BigDecimal)
				sb.append(((BigDecimal)obj).longValue());
			else if (obj instanceof Integer)
				sb.append(((Integer)obj).intValue());
			else if (obj instanceof Entity) {	
				//sb.append(getClassShortName(obj.getClass().getName()));
				//sb.append("[");
				sb.append(((Entity)obj).getId().toString());
				//sb.append("]");
			} else {
				sb.append("");
			}
		} else {
			sb.append("null");	
		}
		return sb.toString();
	}

    
}
