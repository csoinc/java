package fr.improve.struts.taglib.layout.datagrid;

import fr.improve.struts.taglib.layout.collection.SimpleItemContext;

/**
 * ItemContext for the datagrid.
 * 
 * @author jribette
 */
public class DatagridItemContext extends SimpleItemContext {
	private ColumnType columnType;

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	
}
