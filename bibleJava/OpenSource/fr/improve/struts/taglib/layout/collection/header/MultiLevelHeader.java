/*
 * Created on 18 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.improve.struts.taglib.layout.collection.header;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JN RIBETTE
 */
public class MultiLevelHeader {
	protected String title;
	protected String arg0;
	protected String arg1;
	protected String styleClass;
	protected List childHeaders;
	
	protected String tooltip;
	
	protected int colSpan = 0;
	protected int level = 0;
	
	protected String sortProperty;
	protected String width;
	
	public MultiLevelHeader(String in_title, String in_arg0, String in_arg1, String in_styleClass, boolean in_lastLevel) {
		title = in_title;
		arg0 = in_arg0;
		arg1 = in_arg1;
		styleClass = in_styleClass;
		colSpan = in_lastLevel?1:0;
		if (in_title==null) {
			// Skip null titles.
			level = -1;
		}
	}
	
	public void setSortProperty(String in_sortProperty) {
		sortProperty = in_sortProperty;
	}
	public void setWidth(String in_width) {
		width = in_width;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getColSpan() {
		return colSpan;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getStyleClass() {
		return styleClass;
	}
	
	public String getArg0() {
		return arg0;
	}
	public String getArg1() {
		return arg1;
	}
	
	public String getSortProperty() {
		return sortProperty;
	}
	public String getWidth() {
		return width;
	}
	
	public List getChildHeaders() {
		return childHeaders;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public void addHeader(MultiLevelHeader in_header) {
		if (childHeaders==null) {
			childHeaders = new ArrayList();
		}
		level = Math.max(level, in_header.getLevel()+1);
		childHeaders.add(in_header);
		colSpan += in_header.getColSpan();
	}	
}
