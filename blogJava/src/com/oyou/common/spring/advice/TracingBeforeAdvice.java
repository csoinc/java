package com.oyou.common.spring.advice;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class TracingBeforeAdvice implements MethodBeforeAdvice {
	private static final Log log = LogFactory.getLog(TracingAfterAdvice.class);

	private static final HashMap<Class, Object> types = new HashMap<Class, Object>();

	static {
		types.put(String.class, null);
		types.put(Byte.class, null);
		types.put(Boolean.class, null);
		types.put(Character.class, null);
		types.put(Short.class, null);
		types.put(Integer.class, null);
		types.put(Long.class, null);
		types.put(Float.class, null);
		types.put(Double.class, null);
		types.put(java.util.Date.class, null);
		types.put(Date.class, null);
		types.put(Time.class, null);
		types.put(Timestamp.class, null);
		types.put(BigDecimal.class, null);
		types.put(BigInteger.class, null);
	}

	public void before(Method method, Object[] args, Object target) throws Throwable {
		StringBuffer buffer = new StringBuffer(256);
		buffer.append(">>");
		buffer.append(target.getClass().getName());
		buffer.append(".");
		buffer.append(method.getName());
		if (args != null) {
			buffer.append("(");
			for (int i = 0; i < args.length; i++) {
				if (i > 0)
					buffer.append(", ");
				Object object = args[i];
				if (object != null) {
					Class type = object.getClass();
					buffer.append(type.getName());
					if (type.isPrimitive() || types.containsKey(type)) {
						buffer.append("=");
						buffer.append(String.valueOf(object));
					} else {
						if (type.isArray()) {
							buffer.append(type.getComponentType().getName());
						}
						buffer.append("@");
						buffer.append(String.valueOf(System.identityHashCode(object)));
					}
				} else
					buffer.append("null");
			}
			buffer.append(")");
		}
		log.info(buffer);
		buffer = null;
	}

}