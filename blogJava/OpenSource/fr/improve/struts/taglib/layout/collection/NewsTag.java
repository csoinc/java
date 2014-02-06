package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.util.NewsCollection;

/**
 * Render any kind of collection object as an HTML table
 * Differs from Collection because it display each member of the collection separatly
 * 
 * This clas exists for historic reason.
 * @author: Jean-Noel Ribette
 **/
public class NewsTag extends CollectionTag {
	protected void initPanel(PageContext in_pageContext) {	
		defaultPanel = new NewsCollection();
		panel = defaultPanel;
	}
}
