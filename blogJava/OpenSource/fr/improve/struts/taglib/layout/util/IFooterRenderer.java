/*
 * Created on 17 janv. 2005
 *
 * Copyright Improve SA 2005
 */
package fr.improve.struts.taglib.layout.util;

/**
 * @author JN Ribette
 */
public interface IFooterRenderer {
	public void startFooter(StringBuffer in_buffer);
	public void endFooter(StringBuffer in_buffer);
	public void printFooterElement(StringBuffer in_buffer, String in_element, int in_span);
}
