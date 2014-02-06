package com.oyou.common.struts;

/**
 * property reader interface
 * @author	Owen Ou
 *
 */
public interface PropertyReader {
	
	public void close();
	public Property getNextProperty();
	
}
