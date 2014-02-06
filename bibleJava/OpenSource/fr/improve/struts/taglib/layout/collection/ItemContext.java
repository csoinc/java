/*
 * Created on 14 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.collection;

/**
 * @author jnribette
 */
public interface ItemContext {
	public String getTitle();
	public String getArg0();
	public String getArg1();
	public String getWidth();
	
	public String getFooter();
	public String getFooterArg0();
	public String getFooterArg1();
	
	public String getProperty();
	public String getSortProperty();
	
	public String getTarget();
	public String getOnclick();
	public String getUrl();
	public String getItem();

	public String getMathOperation();
	public String getMathPattern();
}
