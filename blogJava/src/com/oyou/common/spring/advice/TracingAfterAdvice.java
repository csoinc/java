package com.oyou.common.spring.advice;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;

public class TracingAfterAdvice implements AfterReturningAdvice {
	private static final Log log = LogFactory.getLog(TracingAfterAdvice.class);

    public void afterReturning(Object object, Method method, Object[] args, Object target) throws Throwable {    	
    	//TODO: add log info after transaction
    	StringBuffer buffer = new StringBuffer();
        buffer.append("<<");
        buffer.append(target.getClass().getName());
        buffer.append(".");
        buffer.append(method.getName());
        log.info(buffer);
    }
	
}