package fr.improve.struts.taglib.layout.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.BodyLayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.header.CollectionItemEvent;
import fr.improve.struts.taglib.layout.collection.header.MultiLevelHeader;
import fr.improve.struts.taglib.layout.collection.header.MultiLevelTitleHandler;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
import fr.improve.struts.taglib.layout.pager.PagerContainer;
import fr.improve.struts.taglib.layout.sort.JavascriptSortUtil;
import fr.improve.struts.taglib.layout.sort.SortUtil;
import fr.improve.struts.taglib.layout.util.CollectionInterface;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.IMultiLevelHeaderRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.NestedHelper;
import fr.improve.struts.taglib.layout.util.TagUtils;
import fr.improve.struts.taglib.layout.util.WidgetUtils;

/**
 * Base class for tags that can display the content of a collection.
 * Support the iteration on two collections.
 *
 * @see CollectionActionTag CollectionActionTag
 * @see CollectionLineTag CollectionLineTag
 * @see CollectionTag CollectionTag
 * @see NewsTag NewsTag
 *
 * @author: Jean-Noel Ribette
 */
public abstract class BaseCollectionTag extends BodyLayoutTagSupport implements PagerContainer, MultiLevelTitleHandler {
	
	/**
	 * This class is here for compatibility reason only and will be removed.
	 * @deprecated use ItemContext instead.
	 */
	public static class Header extends SimpleItemContext {
		public String key;
		/**
		 * @param in_context
		 */
		public Header(ItemContext in_context) {
			super(in_context);
			key = in_context.getTitle();
		}
	}
	
	protected ArrayList headers = new ArrayList();

	/**
	 * Panel class to use.
	 */
	protected static CollectionInterface defaultPanel;
	/**
	 * Instance if the panel interface.
	 */
	protected CollectionInterface panel;
	/**
	 * Name of the bean containing the first collection
	 */	
	protected String name = Constants.BEAN_KEY;
	/**
	 * Property of the bean giving the first collection
	 */
	protected String property;
	
	/**
	 * Name of the bean containing the second collection
	 */	
	protected String name2;
	/**
	 * Property of the bean giving the second collection
	 */
	protected String property2;
	
	protected CollectionsIterator iterator;
	protected Iterator iterator2;
	protected String id;
	protected String id2;
	protected String mathOperationId;

	protected String indexId;
	protected String oddId;
	protected String offsetIndexId;

	/**
	 * Title to display
	 */	
	protected String title;
	protected String arg0Name;
	
	/**
	 * The bundle to use.
	 */
	protected String bundle = Globals.MESSAGES_KEY;

	/**
	 * The css class to use
	 */
	protected String styleClass;
	
	/**
	 * Alternative styleClass
	 */
	protected String styleClass2;
	
	protected boolean first = true;

	protected Object bean;
	protected Object bean2;
	
	/**
	 * When iteration are done on nested collections, this map contains 
	 * the id of the changed elements and their span.
	 */
	protected Map span = new HashMap();

	protected int index = 0;
	
	protected int offset = 0;
	
	protected int length = 0;
	
	protected int size = 0;
	
	protected int nbOfColumns = 0;
	protected int column = 0;
	
	/**
	 * No collection sorting.
	 */
	public static final int SORT_NO = 0;
	/**
	 * Struts-layout default collection sorting.
	 */
	public static final int SORT_LAYOUT = 1;
	/**
	 * Custom collection sorting.
	 */
	public static final int SORT_CUSTOM = 2;
	/**
	 * Client collection sorting with javascript.
	 */
	public static final int SORT_JAVASCRIPT = 3;
	/**
	 * Used by the pager tag to indicate a sort is required,
	 * but that it is up to the collection tag to choose the sort type. 
	 */
	public static final int SORT_PAGER = 4;
	
	/**
	 * Current sorting mode.
	 */ 
	protected int sortType = SORT_NO;
	/**
	 * The action to invoke to sort the table
	 */
	protected String sortAction;
	/**
	 * The param to add to specify which column to sort
	 */
	protected String sortParam;
	/**
	 * The pictogram key to use to display the sort action
	 */
	protected String sortPictogram ="layout.sort";
	/**
	 * The pictogram alt key
	 */
	protected String sortLabel = "layout.sort.label";
	
	/**
	 * The panel width
	 */
	protected String width;
	
	/**
	 * The panel height
	 */
	protected String height;
	
	/**
	 * The panel alignment.
	 */
	protected String align = "CENTER";

	//----------------------------------------------------------------------------------
	// the following attributes are used to compute wich rows of the list are selected 

	/**
	 * If not null, add a column with checkboxes to select lines.
	 * the name of the checkboxes are set to the value of this field.
	 */
	protected String selectName;
	/**
	 * Property to use to set the value of the checkboxes
	 */
	protected String selectProperty;
	/**
	 * Type of select: default is checkbox, other possibility is radio
	 */
	protected String selectType = "checkbox"; 
	/**
	 * True if the checkboxes / radio buttons indicating if a row is selected should not be displayed.
	 */
	protected boolean selectHidden = false;
	/**
	 * Do we need to display a checkbox to select a line in this current processing of the tag body
	 */	
	protected boolean needSelect = false;
	/**
	 * Additional property to add after the index when selecting multiple values from checoboxes.<br>
	 * selectName="select" and selectInex="value" wil render names like name="select[5].value
	 */	
	protected String selectIndex;
	
	protected String selectId;
	
	//--------------------------------------------------

	protected short fieldDisplayMode = AbstractModeFieldTag.MODE_EDIT;

	/**
	 * Temp styleClass for designed columns / line.
	 */
	protected String tempStyleClass;
	
	/**
	 * Temp styles.
	 */
	protected List tempStyles = new ArrayList();

	/**
	 * Message to display of the collection is empty.
	 */
	protected String emptyKey;
	
	/**
	 * Javascript function to call when a row is selected.
	 */
	protected String onRowClick;
	
	/**
	 * Javascript function to call when a row is selected.
	 */
	protected String onRowDblClick;
	
	/**
	 * Javascript function to call when a row is selected. 
	 */	 
	protected String onRowMouseOver;
	protected String onRowMouseOut;
	
	protected String jspOnRowMouseOver;
	protected String jspOnRowMouseOut;
	protected String jspProperty;	
	protected String jspStyleClass;
	
	/**
	 * Javascript function to call when a row checkbox is selected.
	 */
	protected String onClick;
	
	/**
	 * Id of the table containing the data.
	 */
	protected String styleId;
	
	/**
	 * Model to use.
	 */
	protected String model;
	
	
	protected abstract void renderBlankCollection(StringBuffer out_buffer) throws JspException;
	protected abstract void renderStart(StringBuffer out_buffer) throws JspException;
	protected void renderHeader(StringBuffer buffer, ItemContext in_header) throws JspException {
		renderHeader(buffer, (Header)in_header);
	}
	
	/**
	 * @deprecated
	 */
	protected void renderHeader(StringBuffer buffer, Header in_header) throws JspException {
		// Shoud be overrided in CollectionTag for old implementation.
	}
	protected abstract void renderEnd(StringBuffer out_buffer) throws JspException;
	
	public void addItem(StringBuffer in_buffer, ItemContext in_context) throws JspException {
		if (first) {
			// This is the first time the collection is iterated over, display the table header label.
			
			// For compatibility reason.
			Header lc_header = new Header(in_context);
			lc_header.key = in_context.getTitle();			
			headers.add(lc_header);
			nbOfColumns++;			
			if (in_context.getSortProperty()!=null && (sortType==SORT_NO || sortType==SORT_PAGER)) {
				findSortType();
			}
		} else {
			addItem(in_buffer, in_context.getItem(), in_context.getUrl(), in_context.getTarget(), in_context.getOnclick());			
		}
		column++;
	}
	
	protected List multiLevelHeaders;
	protected int headersLevel;
	
	
	private void findSortType() {
		if (sortAction==null || sortAction.equals("") || sortType==SORT_PAGER && "client".equals(sortAction)) {
			sortType = SORT_LAYOUT;
		} else if (sortAction.equals("client")) {
			sortType = SORT_JAVASCRIPT;
			sortParam = JavascriptSortUtil.getId((HttpServletRequest)pageContext.getRequest());
		} else {
			sortType = SORT_CUSTOM;
		}
	}
	
	public void incrementColumn() {
		column++;
	}
	
	protected abstract void addItem(StringBuffer in_buffer, String in_item, String in_url, String in_target, String in_onlick) throws JspException;
	public int doAfterBody() throws JspException {		
		if (first) {				
			// Initialize the iteration
			
			if (sortType==SORT_LAYOUT) {
				// store any nested iterator : the creation of the iterator for paging/iteration loose the nested iterator. 
				CollectionsIterator lc_nestedIterator = iterator.getNestedIterator();				
				
				// get the first collection and iterator
				Collection lc_collection1 = LayoutUtils.getCollection( LayoutUtils.getBeanFromPageContext(pageContext, name, property), false);
				SortUtil.addCollection( lc_collection1, (javax.servlet.http.HttpServletRequest) pageContext.getRequest());
				SortUtil lc_sortUtil = (SortUtil) pageContext.getRequest().getAttribute(SortUtil.SORTUTIL_KEY);
				if (lc_sortUtil != null) {
					// sort util devrait ?tre null si on ne vient pas de la m?me page.
					lc_collection1 = lc_sortUtil.getSortedCollection();

				}				
				
				iterator = new CollectionsIterator(lc_collection1, null, id, indexId);

				// get the second collection and iterator
				if (name2 != null)
					iterator2 = LayoutUtils.getIterator(pageContext, name2, property2);
				
				// restore the nested iterator.
				iterator.addLastIterator(lc_nestedIterator);
				
				size = lc_collection1.size();
			}		
			
			Map lc_map = new HashMap();
			for (int i=0;i<offset;i++) {
				if (iterator.hasNext(pageContext)) iterator.skip(pageContext, lc_map);
				if (iterator2!=null && iterator2.hasNext()) iterator2.next();
			}

			TagUtils.write(pageContext, renderHeaders().toString());
		} else {
			// End the previous iteration.
			StringBuffer lc_buffer = new StringBuffer();
			renderEndLine(lc_buffer);
			TagUtils.write(pageContext, lc_buffer.toString());
		}
		
		// Flush the tag content.
		if (bodyContent != null) {
			TagUtils.writePrevious(pageContext, bodyContent.getString());
			bodyContent.clearBody();
		}
		
		// Prepare the next iteration.
		
		if (iterator.hasNext(pageContext) && (length==0 || index < length || iterator.getNestedIterator()!=null && iterator.getNestedIterator().hasNext(pageContext))) {
			
			span.clear();
			bean = iterator.next(pageContext, span);											

			if (iterator2!=null && iterator2.hasNext()) {
				bean2 = iterator2.next();
				if (id2!=null) {
					span.put(id2, new Integer(1));
					if (bean2!=null) pageContext.setAttribute(id2, bean2);
					else pageContext.removeAttribute(id2);
				}
			}
			
			int lc_oldIndex = index;
			index = iterator.getIndex();
			
			if (oddId!=null) pageContext.setAttribute(oddId, new Boolean((index-1)%2==0));
			if (offsetIndexId!=null) pageContext.setAttribute(offsetIndexId, new Integer(index-1+offset));
			if ((selectName!=null && selectName.length()>0 || selectProperty!=null && selectProperty.length()!=0)&& 
					(fieldDisplayMode!=FormUtilsInterface.CREATE_MODE) &&
					lc_oldIndex!=index) {
				needSelect = true;
			}
			
			// support for alternating row styleClass.
			if (styleClass2!=null) {
				String temp = styleClass2;
				styleClass2 = styleClass;
				styleClass = temp;	
			}
			
			StringBuffer lc_buffer = new StringBuffer();
			renderStartLine(lc_buffer);
			TagUtils.write(pageContext, lc_buffer.toString());

			first = false;		
			return EVAL_BODY_TAG;
			
		} else {
			first = false;		
			if (id!=null) pageContext.removeAttribute(id);
			if (id2!=null) pageContext.removeAttribute(id2);
			if (indexId!=null) pageContext.removeAttribute(indexId);		
			if (oddId!=null) pageContext.removeAttribute(oddId);
			if (offsetIndexId!=null) pageContext.removeAttribute(offsetIndexId);
			return SKIP_BODY;
		}
	}
	/**
	 * Returns the current bean in the first collection during an iteration.
	 */
	public Object getBean() {
		return bean;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (29/10/2001 10:59:59)
	 * @return java.lang.String
	 */
	public java.lang.String getEmptyKey() {
		return emptyKey;
	}
	/**
	 * Returns the index of the current bean when iterating.
	 */
	public int getIndex() {
		return index - 1;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/06/2001 18:33:28)
	 * @return java.lang.String
	 */
	public java.lang.String getIndexId() {
		return indexId;
	}
	/**
	 * Creation date: (15/06/01 13:23:16)
	 * @return java.lang.String
	 */
	public java.lang.String getName() {
		return name;
	}
	/**
	 * Creation date: (15/06/01 13:23:16)
	 * @return java.lang.String
	 */
	public java.lang.String getProperty() {
		return property;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (24/07/2001 18:19:23)
	 * @return java.lang.String
	 */
	public java.lang.String getSelectProperty() {
		return selectProperty;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (24/07/2001 19:08:15)
	 * @return java.lang.String
	 */
	public java.lang.String getSelectType() {
		return selectType;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 11:36:29)
	 * @return java.lang.String
	 */
	public java.lang.String getSortAction() {
		return sortAction;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 12:34:15)
	 * @return java.lang.String
	 */
	public java.lang.String getSortParam() {
		return sortParam;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 11:36:29)
	 * @return java.lang.String
	 */
	public java.lang.String getSortPictogram() {
		String lc_property = ((ItemContext)headers.get(getColumn())).getProperty();
		if (sortType==SORT_LAYOUT && !"none".equals(sortPictogram)) {
			switch (SortUtil.getSortState((HttpServletRequest)pageContext.getRequest(), lc_property)) {
				case -1:
					return sortPictogram + ".forward";
				case 1:
					return sortPictogram + ".reverse";
				default:
					return sortPictogram;
			}
		} else {
			return sortPictogram;
		}
	}
	public String getSortLabel() {
		return sortLabel;	
	}
	/**
	 * Creation date: (15/06/01 13:24:33)
	 * @return java.lang.String
	 */
	public java.lang.String getStyleClass() {
		return styleClass;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (09/10/2001 16:28:12)
	 * @return java.lang.String
	 */
	public java.lang.String getTempStyleClass() {
		return tempStyleClass;
	}
	public String getId() {
		return id;
	}
	public String getId2() {
		return id2;	
	}
	public String getName2() {
		return name2;	
	}
	public String getProperty2() {
		return property2;
	}

	public boolean isFirst() {
		return first;
	}
	public void release() {
		super.release();

		title = null;
		arg0Name = null;
		align = "CENTER";
		width = null;
		height = null;		
		bundle = Globals.MESSAGES_KEY;
		
		onClick = null;
		onRowClick = null;
		onRowDblClick = null;
		onRowMouseOver = null;
		onRowMouseOut = null;
		
		name = Constants.BEAN_KEY;
		property = null;
		id = null;
		
		name2 = null;
		property2 = null;
		id2 = null;
		
		styleClass = null;
		styleClass2 = null;	
		
		indexId = null;	
		oddId = null;	
		offsetIndexId = null;
		
		sortAction = null;
		sortPictogram = "layout.sort";
		sortLabel = "layout.sort.label";
		
		selectName = null;
		selectProperty = null;
		selectIndex = null;
		selectId = null;
		selectType = "checkbox";
		selectHidden = false;
		
		emptyKey = null;
		
		model = null;
	}
	/**
	 * Reset the variable used internally.
	 */
	protected void reset() {					
		iterator = null;
		iterator2 = null;
		
		first = true;
		
		bean = null;
		bean2 = null;
		
		index = 0;
		
		nbOfColumns = 0;
		column = 0;
		
		sortType = SORT_NO;
		
		needSelect = false;
		
		mathOperationId = null;
			
		// these last ones can also be set externally. This can lead to problems with tag pooling.
		sortParam = null;	
		onRowClick = null;
		styleId = null;	
		offset = 0;
		length = 0;
		
		headers.clear();
		tempStyles.clear();
		
		span.clear();
		panel = null;
		
		onRowMouseOver = jspOnRowMouseOver; jspOnRowMouseOver = null;
		onRowMouseOut = jspOnRowMouseOut; jspOnRowMouseOut = null;
		property = jspProperty; jspProperty = null;
		
		styleClass = jspStyleClass;
		jspStyleClass = null;
		multiLevelHeaders = null;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (29/10/2001 10:59:59)
	 * @param newEmptyKey java.lang.String
	 */
	public void setEmptyKey(java.lang.String newEmptyKey) {
		emptyKey = newEmptyKey;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setId2(String id2) {
		this.id2 = id2;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/06/2001 18:33:28)
	 * @param newIndexId java.lang.String
	 */
	public void setIndexId(java.lang.String newIndexId) {
		indexId = newIndexId;
	}
	/**
	 * Set the display mode
	 * format is XX,XX,XX where XX can be N (not displayed), E (editable), I (inspectable). Order is create mode, edit mode, inspect mode
	 */
	public void setMode(String mode) throws JspException {
		fieldDisplayMode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().computeFieldDisplayMode(pageContext, mode);		
	}
	/**
	 * Creation date: (15/06/01 13:23:16)
	 * @param newName java.lang.String
	 */
	public void setName(java.lang.String newName) {
		name = newName;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	/**
	 * Creation date: (15/06/01 13:23:16)
	 * @param newProperty java.lang.String
	 */
	public void setProperty(java.lang.String newProperty) {
		property = newProperty;
	}
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	public void setSelectName(String name) {
		selectName = name;
	}
	public String getSelectName() {
		return selectName;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (24/07/2001 18:19:23)
	 * @param newSelectProperty java.lang.String
	 */
	public void setSelectProperty(java.lang.String newSelectProperty) {
		selectProperty = newSelectProperty;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (24/07/2001 19:08:15)
	 * @param newSelectType java.lang.String
	 */
	public void setSelectType(java.lang.String newSelectType) {
		if (newSelectType == null) {
			selectType = "checkbox";
			selectHidden = false;	
		} else {
			String lc_lower = newSelectType.toLowerCase();
			if (lc_lower.startsWith("radio")) {
				selectType = "radio";
			} else {
				selectType = "checkbox";	
			}
			if (lc_lower.endsWith("hidden")) {
				selectHidden = true;	
			} else {
				selectHidden = false;	
			}
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 11:36:29)
	 * @param newSortAction java.lang.String
	 */
	public void setSortAction(java.lang.String newSortAction) {
		sortAction = newSortAction;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 12:34:15)
	 * @param newSortParam java.lang.String
	 */
	public void setSortParam(java.lang.String newSortParam) {
		sortParam = newSortParam;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (17/07/2001 11:36:29)
	 * @param newSortPictogram java.lang.String
	 */
	public void setSortPictogram(java.lang.String newSortPictogram) {
		sortPictogram = newSortPictogram;
	}
	public void setSortLabel(String newSortLabel) {
		sortLabel = newSortLabel;	
	}
	/**
	 * Creation date: (15/06/01 13:24:33)
	 * @param newStyleClass java.lang.String
	 */
	public void setStyleClass(java.lang.String newStyleClass) {
		styleClass = newStyleClass;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (09/10/2001 16:28:12)
	 * @param newTempStyleClass java.lang.String
	 */
	void setTempStyleClass(java.lang.String newTempStyleClass) {
		tempStyleClass = newTempStyleClass;
	}
	void addTempStyle(String in_tempStyle) {
		tempStyles.add(in_tempStyle);
	}
	void removeTempStyle() {
		tempStyles.remove(tempStyles.size()-1);
	}
	/**
	 * Creation date: (15/06/01 13:23:16)
	 * @param newTitle java.lang.String
	 */
	public void setTitle(java.lang.String newTitle) {
		title = newTitle;
	}
	public void setArg0Name(String in_arg0Name) {
		arg0Name = in_arg0Name;	
	}
	public String getArg0Name() {
		return arg0Name;
	}	
	public String getTitle() {
		return title;	
	}
	public void setWidth(String width) {	
		this.width = width;
	}
	public String getWidth() {
		return width;
	}
	/**
	 * Gets the styleClass2.
	 * @return Returns a String
	 */
	public String getStyleClass2() {
		return styleClass2;
	}

	/**
	 * Sets the styleClass2.
	 * @param styleClass2 The styleClass2 to set
	 */
	public void setStyleClass2(String styleClass2) {
		this.styleClass2 = styleClass2;
	}

	/**
	 * Gets the align.
	 * @return Returns a String
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * Sets the align.
	 * @param align The align to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}

	protected void initPanel(PageContext in_pageContext) {	
		try {
			defaultPanel = (CollectionInterface) LayoutUtils.getSkin(in_pageContext.getSession()).getCollectionClass(model).newInstance();
		} catch (Exception e) {
			defaultPanel = new fr.improve.struts.taglib.layout.util.BasicCollection();
		}
		panel = defaultPanel;	
	}

	/**
	 * Gets the length.
	 * @return Returns a int
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 * @param length The length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Gets the offset.
	 * @return Returns a int
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset.
	 * @param offset The offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getSize() {
		return size;
	}

	/**
	 * Gets the selectIndex.
	 * @return Returns a String
	 */
	public String getSelectIndex() {
		return selectIndex;
	}
	
	public String getSelectId() {
		return selectId;
	}

	/**
	 * Sets the selectIndex.
	 * @param selectIndex The selectIndex to set
	 */
	public void setSelectIndex(String selectIndex) {
		this.selectIndex = selectIndex;
	}
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	public int doStartLayoutTag() throws JspException {		
		
		if (fieldDisplayMode == AbstractModeFieldTag.MODE_NODISPLAY)
			return SKIP_BODY;
		
		// inform your parent layout tag we are starting.
		new StartLayoutEvent(this, new StringBuffer("<td colspan=\"").append(LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber()).append("\" valign=\"top\">")).send();

		// get the first collection and iterator
		Object lc_bean = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
		Collection lc_collection = LayoutUtils.getCollection(lc_bean);
		Object lc_mainBean = LayoutUtils.getBeanFromPageContext(pageContext, name, null);
		iterator = new CollectionsIterator(lc_mainBean, property, id, indexId); //lc_collection.iterator();

		size = lc_collection.size();
		
		// get the second collection and iterator
		if (name2 != null)
			iterator2 = LayoutUtils.getIterator(pageContext, name2, property2);

		StringBuffer buffer = new StringBuffer();

		// Init the panel
		if (panel==null) {
			initPanel(pageContext);
		}
		panel.init(pageContext, styleClass, this);

		// If the collection is empty, display the specified error message.
//	if (!iterator.hasNext(pageContext) && emptyKey!=null) {
//		renderBlankCollection(buffer);
//		TagUtils.write(pageContext, buffer.toString());		
//		return SKIP_BODY;
//	} else {
		renderStart(buffer);
		TagUtils.write(pageContext, buffer.toString());
		return EVAL_BODY_TAG;
//	}
	}
	
	/**	 
	 * Save and computed dynamic values (EL, nested context) 
	 */
	protected void initDynamicValues() {
		jspOnRowMouseOut = onRowMouseOut;
		jspOnRowMouseOver = onRowMouseOver;
		jspProperty = property;
		jspStyleClass = styleClass;
		if (styleClass==null) {
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty("styleclass.collection", null);
		}
		property = NestedHelper.getAdjustedProperty(jspProperty, pageContext);
		property = Expression.evaluate(property, pageContext);
		mathOperationId = WidgetUtils.generateId(pageContext.getRequest(),"MATHOP");
	}
	
	public int doEndLayoutTag() throws JspException {
		if (fieldDisplayMode==AbstractModeFieldTag.MODE_NODISPLAY) return SKIP_BODY;
		StringBuffer buffer = new StringBuffer();
		
		if (index==0 && emptyKey!=null) {
			renderBlankCollection(buffer);
		}	
		
		renderEnd(buffer);
		TagUtils.write(pageContext,buffer.toString());
		
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}
	protected StringBuffer renderHeaders() throws JspException {
		StringBuffer lc_buffer = new StringBuffer();
		if (panel instanceof IMultiLevelHeaderRenderer) {			
			renderMultiLevelHeaders(lc_buffer, multiLevelHeaders, headersLevel);
			column = 0;
		} else {
			column = 0;
			panel.doStartHeaders(lc_buffer);
			for (int i=0;i<headers.size();i++) {
				ItemContext lc_header = (ItemContext) headers.get(i);
				renderHeader(lc_buffer, lc_header);
				column++;
			}	
			column = 0;
			panel.doEndHeaders(lc_buffer);
		}
		column = 0;
		return lc_buffer;
	}
		
	/**
	 * @param lc_buffer
	 * @param multiLevelHeaders2
	 * @param headersLevel2
	 */
	protected abstract void renderMultiLevelHeaders(StringBuffer lc_buffer, List multiLevelHeaders2, int headersLevel2) throws JspException;

	public void renderEndLine(StringBuffer in_buffer) throws JspException{
		panel.doEndItems(in_buffer);
		column = 0;
	}
	public void renderStartLine(StringBuffer in_buffer) throws JspException {
		panel.doStartItems(in_buffer);
	}

	void addIterator(CollectionsIterator in_iterator) {
		iterator.addLastIterator(in_iterator);
	}
	/**
	 * Returns the column.
	 * @return int
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Returns the nbOfColumns.
	 * @return int
	 */
	public int getNbOfColumns() {
		return nbOfColumns;
	}

	/**
	 * Sets the onRowClick.
	 * @param onRowClick The onRowClick to set
	 */
	public void setOnRowClick(String onRowClick) {
		this.onRowClick = onRowClick;
	}

	/**
	 * Returns the onRowClick.
	 * @return String
	 */
	public String getOnRowClick() {
		return onRowClick;
	}

	/**
	 * Returns the styleId.
	 * @return String
	 */
	public String getStyleId() {
		return styleId;
	}

	/**
	 * Sets the styleId.
	 * @param styleId The styleId to set
	 */
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	/**
	 * Returns the height.
	 * @return String
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}	
	/**
	 * Returns the oddId.
	 * @return String
	 */
	public String getOddId() {
		return oddId;
	}

	/**
	 * Sets the oddId.
	 * @param oddId The oddId to set
	 */
	public void setOddId(String oddId) {
		this.oddId = oddId;
	}

	/**
	 * Returns the sortType.
	 * @return int
	 */
	public int getSortType() {
		return sortType;
	}

	/**
	 * Sets the sortType. Should be called before do start tag.
	 * @param sortType The sortType to set
	 */
	public void setSortType(int sortType) {
		this.sortType = sortType;
		if (sortType==SORT_PAGER) {
			findSortType();
		}
	}

	/**
	 * Returns the onRowDblClick.
	 * @return String
	 */
	public String getOnRowDblClick() {
		return onRowDblClick;
	}

	/**
	 * Sets the onRowDblClick.
	 * @param onRowDblClick The onRowDblClick to set
	 */
	public void setOnRowDblClick(String onRowDblClick) {
		this.onRowDblClick = onRowDblClick;
	}
	public List getHeaders() {
		return headers;	
	}

	/**
	 * Returns the onClick.
	 * @return String
	 */
	public String getOnClick() {
		return onClick;
	}

	/**
	 * Sets the onClick.
	 * @param onClick The onClick to set
	 */
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public Map getSpans() {
		return span;	
	}
	public boolean isNestedIteration() {
		return iterator.hasNestedIterator();
	}

	/**
	 * @return
	 */
	public String getOnRowMouseOver() {
		return onRowMouseOver;
	}

	/**
	 * @param string
	 */
	public void setOnRowMouseOver(String string) {
		onRowMouseOver = string;
	}

	/**
	 * Returns the model.
	 * @return String
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 * @param model The model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	public String getOnRowMouseOut() {
		return onRowMouseOut;
}
	public void setOnRowMouseOut(String in_onRowMouseOut) {
		onRowMouseOut = in_onRowMouseOut;
	}

	/**
	 * @param offsetIndexId The offsetIndexId to set.
	 */
	public void setOffsetIndexId(String offsetIndexId) {
		this.offsetIndexId = offsetIndexId;
	}
	/**
	 * @return Returns the mathOperationId.
	 */
	public String getMathOperationId() {
		return mathOperationId;
	}
	public Object addCollectionTitle(CollectionItemEvent in_event) {
		MultiLevelHeader lc_header = (MultiLevelHeader) in_event.getValue();
		if (multiLevelHeaders==null) {
			multiLevelHeaders = new ArrayList();
		}
		multiLevelHeaders.add(lc_header);
		headersLevel = Math.max(headersLevel, lc_header.getLevel());
		return null;
	}
	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return bundle;
	}
	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
}
