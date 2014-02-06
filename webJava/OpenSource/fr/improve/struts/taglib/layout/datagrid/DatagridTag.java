/*
 * Created on 9 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.datagrid;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.collection.ItemContext;
import fr.improve.struts.taglib.layout.util.IDatagridRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * The datagrid tag is a special collection tag. 
 * 
 * @author jnribette
 */
public class DatagridTag extends CollectionTag {
	public static final String LOADED = "fr.improve.struts.taglib.layout.datagrid.DatagridTag.LOADED";
	
	protected String jspOnRowClick;
	protected String jspStyleId;
	protected DatagridImpl datagrid;	
	protected StringBuffer initCode = new StringBuffer(1000);
		
	protected boolean allowSelection = true;
	protected boolean allowMultipleSelection = true;	
	
	public int doStartLayoutTag() throws JspException {
		loadScript();
		
		// Set the onRowClick attribute.
		jspOnRowClick = onRowClick;
		if (allowSelection) {			
			onRowClick = "strutsLayoutDatagridData['" + property + "'].selectDatagridLine(this)";
		}
		
		// Set the styleId attribute if not set.
		jspStyleId = styleId;
		if (styleId==null) {
			styleId = property + "JsId";
		}
		
		// Get the datagrid object.
		datagrid = LayoutUtils.getDatagrid(pageContext, name, property);
		
		// Start the tag.
		int ret = super.doStartLayoutTag();
		
		// Define the JS datagrid object.
		checkRenderer();
		IDatagridRenderer lc_renderer = (IDatagridRenderer) panel;
		TagUtils.write(pageContext, "<script>strutsLayoutDatagridData['");
		TagUtils.write(pageContext, property);
		TagUtils.write(pageContext, "'] = new StrutsLayout.Datagrid('");
		TagUtils.write(pageContext, property);
		TagUtils.write(pageContext, "', '");
		TagUtils.write(pageContext, styleId);
		TagUtils.write(pageContext, "', '");
		TagUtils.write(pageContext, lc_renderer.getRowStyleClass());
		TagUtils.write(pageContext, "', '");
		TagUtils.write(pageContext, lc_renderer.getRowStyleClass2());
		TagUtils.write(pageContext, "', ");
		TagUtils.write(pageContext, String.valueOf(allowSelection));
		TagUtils.write(pageContext, ", ");
		TagUtils.write(pageContext, String.valueOf(allowMultipleSelection));
		TagUtils.write(pageContext, ");\n");
		
		// Define the styleClass.
		Iterator lc_it = lc_renderer.getRowStyleClassMap().entrySet().iterator();
		while (lc_it.hasNext()) {
			Map.Entry lc_entry = (Map.Entry) lc_it.next();
			TagUtils.write(pageContext, "strutsLayoutDatagridData['");
			TagUtils.write(pageContext, property);
			TagUtils.write(pageContext, "'].addStyleClass('");
			TagUtils.write(pageContext, lc_entry.getKey().toString());
			TagUtils.write(pageContext, "', '");
			TagUtils.write(pageContext, lc_entry.getValue().toString());
			TagUtils.write(pageContext,"');");
			TagUtils.write(pageContext, "\n");
		}
		TagUtils.write(pageContext, "</script>\n");	
		
		return ret;		
	}

	/**
	 * Check the renderer is an instaneof IDatagridRenderer.
	 */
	private void checkRenderer() throws JspException {
		if (!(panel instanceof IDatagridRenderer)) {
			throw new JspException("Bad use of <layout:datagrid> : the renderer " + panel.getClass().getName() + " does not implement IDatagridRenderer. Please use the model attribute and/or configure Struts-Layout properly");
		}
		
	}

	/**
	 * Load the required Javascript code.
	 */
	protected void loadScript() throws JspException {
		if (pageContext.getRequest().getAttribute(LOADED)==null) {
			TagUtils.write(pageContext, "<script src=\"");
			TagUtils.write(pageContext, LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
			TagUtils.write(pageContext, "/datagrid.js\"></script>");
			pageContext.getRequest().setAttribute(LOADED, "");
		}		
	}

	public void reset() {
		super.reset();		
		onRowClick = jspOnRowClick;		
		styleId = jspStyleId;
		datagrid = null;		
	}
	
	/**
	 * Override addItem in BaseCollectionTag to also define the columns in Javascript.
	 */
	public void addItem(StringBuffer in_buffer, ItemContext in_context) throws JspException {
		
		if (isFirst()) {
			defineColumn(in_context.getProperty(), ((DatagridItemContext)in_context).getColumnType());
		} else if (getColumn()==0) {
			renderLineState();
		}
		super.addItem(in_buffer, in_context);
	}

	/**
	 * Define a column in Javascript to make it possible to add columns in DHTML.
	 */
	protected void defineColumn(String in_property, ColumnType in_type) throws JspException {
		IDatagridRenderer lc_renderer = (IDatagridRenderer) panel;
		TagUtils.write(pageContext, "<script>");
		TagUtils.write(pageContext, "strutsLayoutDatagridData['");
		TagUtils.write(pageContext, property);
		TagUtils.write(pageContext, "'].addColumn('");
		TagUtils.write(pageContext, in_property);
		TagUtils.write(pageContext, "', '");
		TagUtils.write(pageContext, lc_renderer.getColumnStyleClass(getColumn()));
		TagUtils.write(pageContext, "', '");
		TagUtils.write(pageContext, in_type.getType());
		TagUtils.write(pageContext, "'");
		if (ColumnType.SELECT.equals(in_type.getType())) {
			TagUtils.write(pageContext,", [");
			Iterator it = in_type.getValues().iterator();
			while (it.hasNext()) {
				DatagridSelectTag.DatagridChoice choice = (DatagridSelectTag.DatagridChoice) it.next();
				TagUtils.write(pageContext, "new StrutsLayout.Option('");
				TagUtils.write(pageContext, ResponseUtils.filter(choice.label));
				TagUtils.write(pageContext, "','");
				TagUtils.write(pageContext, choice.value);
				TagUtils.write(pageContext, "')");
				if (it.hasNext()) {
					TagUtils.write(pageContext, ",");
				}
			}
			TagUtils.write(pageContext, "]");
		}
		TagUtils.write(pageContext,");");
		TagUtils.write(pageContext, "</script>\n");
		
		if (ColumnType.CHECKBOX.equals(in_type)) {
			// Need to reset all the checkbox.
			datagrid.addBooleanProperty(in_property);
		}
	}	
	
	public int doEndLayoutTag() throws JspException {
		int lc_ret = super.doEndLayoutTag();
		TagUtils.write(pageContext, "<script>\n");
		TagUtils.write(pageContext, initCode.toString());
		TagUtils.write(pageContext, "</script>\n");
		initCode.setLength(0);
		return lc_ret;
	}
	
	protected void renderLineState() {
		String lc_state = datagrid.getObjectState(getIndex());
		initCode.append("strutsLayoutDatagridData['");
		initCode.append(property);
		initCode.append("'].initState(");
		initCode.append(getIndex());		
		initCode.append(",'");
		initCode.append(lc_state);
		initCode.append("');\n");		
	}
	
	public void release() {
		super.release();
		allowSelection = true;
		allowMultipleSelection = true;
	}
	
	public void setSelectionAllowed(boolean in_allowSelection) {
		allowSelection = in_allowSelection;
	}
	public boolean isSelectionAllowed() {
		return allowSelection;	
	}
	public void setMultipleSelectionAllowed(boolean in_multipleSelectionAllowed) {
		allowMultipleSelection = in_multipleSelectionAllowed;
	}
	public boolean isMultipleSelectionAllowed() {
		return allowMultipleSelection;	
	}
}
