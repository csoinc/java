/*
 * Created on 13 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import fr.improve.struts.taglib.layout.collection.SimpleItemContext;

/**
 * Simplified collectionInput tag for the datagrid : only the property and key attributes are needed.
 * @author jnribette
 */
public class DatagridColumnTag extends AbstractDatagridColumnTag {
	
	protected SimpleItemContext createItemContext() {
		DatagridItemContext context = new DatagridItemContext();
		context.setColumnType(ColumnType.TEXT);
		return context;
	}	
}
