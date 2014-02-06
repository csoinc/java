package com.oyou.common.spring.advice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;
import java.lang.reflect.Method;

public class TracingExceptionsAdvice implements ThrowsAdvice {
	private static final Log log = LogFactory.getLog(TracingAfterAdvice.class);

	public void afterThrowing(Method method, Object[] args, Object target, RuntimeException throwable) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(target.getClass().getName());
		buffer.append(".");
		buffer.append(method.getName());
		buffer.append(", ");
		buffer.append(throwable);
		buffer.append(" Exception was thrown, ");
		buffer.append(throwable.getMessage());
		log.error(buffer);
	}

}
