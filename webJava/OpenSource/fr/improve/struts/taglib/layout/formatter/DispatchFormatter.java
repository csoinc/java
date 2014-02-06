/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package fr.improve.struts.taglib.layout.formatter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Implementation of a formatter class calling a method corresponding of the format specified.
 * The called method must have the following signature:
 * 
 * myFormat(Object in_object, PageContext in_pageontext) : String
 * 
 * This formatter includes support for no error mode.
 * 
 * @version 	1.0
 * @author
 */
public abstract class DispatchFormatter extends AbstractFormatter {	
	static Class[] argv = { Object.class, PageContext.class };

	protected HashMap methods = new HashMap();
	
	protected Method getMethod(String name) throws NoSuchMethodException {

		synchronized (methods) {
			Method method = (Method) methods.get(name);
			if (method == null) {
				method = getClass().getMethod(name, argv);
				methods.put(name, method);
			}
			return (method);
		}

	}
	
	/*
	 * @see AbstractFormatter#format(Object, String, PageContext)
	 */
	public String format(
		Object in_value,
		String in_format,
		PageContext in_pageContext) throws FormatException {
		
		if (LayoutUtils.getNoErrorMode()) {
			return "(" + in_format + ")";
		}

		String lc_value = null;			
			try {
				Method lc_method = getMethod(in_format);
				Object[] lc_argv = new Object[2];
				lc_argv[0] = in_value;
				lc_argv[1] = in_pageContext;
				lc_value = (String) lc_method.invoke(this, lc_argv);
			} catch (NoSuchMethodException nsme) {
				TagUtils.saveException(in_pageContext, nsme);
				throw new FormatException("No method corresponds to the formatter " + in_format + " in the class " + getClass().getName());
			} catch (IllegalAccessException iae) {
				TagUtils.saveException(in_pageContext, iae);
				throw new FormatException(iae.getMessage());
			} catch (InvocationTargetException ite) {
				TagUtils.saveException(in_pageContext, ite.getTargetException());
				throw new FormatException("Invocation target exception: " + ite.getTargetException());
			}
		return lc_value;
	}

}
