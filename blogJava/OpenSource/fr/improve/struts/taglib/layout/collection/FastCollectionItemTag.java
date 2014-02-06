package fr.improve.struts.taglib.layout.collection;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.header.CollectionItemEvent;
import fr.improve.struts.taglib.layout.collection.header.MultiLevelHeader;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.formatter.FormatException;
import fr.improve.struts.taglib.layout.sort.JavascriptSortUtil;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.NestedHelper;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * This tag is a simpler version of the CollectionItemTag
 * with not bodycontent support.
 * 
 * This makes the tag faster with some versions of Jasper which allocate a 7k buffer for each bodycontent. 

 * @author: Jean-Noel Ribette
 **/
public class FastCollectionItemTag extends LayoutTagSupport implements LayoutEventListener {
	protected String name;
	protected String property;
	
	protected String title;
    protected String jspTitle;
    
	protected String arg0;
	protected String arg1;
	protected String tooltip;
	
	protected String footer;
	protected String footerArg0;
	protected String footerArg1;
	
	protected String jspFooter;
	
	protected String action;
	protected String url;
	protected String forward;
	protected String page;
	protected String target;
	protected String param;
	protected String paramId;
	protected String paramName;
	protected String paramProperty;
	protected String onclick;
	protected boolean sortable = false;
	
	protected String type;
	protected String jspType;
	
	protected String width;
	protected String styleClass;
	protected String style;
	protected boolean filter = true;
	protected CollectionTag collectionTag;
	protected boolean skip = false;
	
	protected SimpleItemContext context = new SimpleItemContext();
	
	public static final String SPAN_KEY = "fr.improve.struts.taglib.layout.collection.CollectionItemTag.SPAN_KEY";
	
	/**
	 * Add by Damien Viel for MathCollection
	 */
	protected String mathOperation;
	protected String mathPattern;

	/**
	 * @author Damien Viel
	 * @return Returns the mathOperation.
	 */
	public String getMathOperation() {
		return mathOperation;
	}
	
	/**
	 * @author Damien Viel
	 * @param mathOperation The mathOperation to set.
	 */
	public void setMathOperation(String mathOperation) {
		this.mathOperation = mathOperation;
	}
	
	public int doEndLayoutTag() throws JspException {
		if (skip) {
			skip = false;
			collectionTag.incrementColumn();
			return EVAL_PAGE;	
		}			

		// The cell value.
		Object lc_cell = buildContent();
		
		// Math operation.
		if (mathOperation!=null) {
			double lc_mathValue = LayoutUtils.getDouble(lc_cell);
			collectionTag.storeMathData(lc_mathValue);
		}

		// Hidden value
		String lc_hiddenValue = null;
		if (collectionTag.sortType==BaseCollectionTag.SORT_JAVASCRIPT && sortable) {
			lc_hiddenValue = JavascriptSortUtil.makeSortable(lc_cell);
		}					
		
		// Computed url.
		String lc_url = buildUrl();
		
		boolean lc_filter = buildFilter();		
		
		if (lc_cell != null && lc_filter && lc_cell instanceof String) {
			// Filter HTML sensitive characters. 
			// Only do that if the value is a String (the formatter may want the original Object)
			lc_cell = ResponseUtils.filter(lc_cell.toString());
		}
		if (lc_cell!=null && mathPattern!=null){
			double lc_value = LayoutUtils.getDouble(lc_cell);
			DecimalFormatSymbols lc_symbols = new DecimalFormatSymbols(LayoutUtils.getLocale(pageContext));
			lc_cell = new DecimalFormat(mathPattern, lc_symbols).format(lc_value);
		}
		if (lc_cell != null && type != null) {
			// Format the cell value
		
			try {	
				lc_cell = LayoutUtils.getSkin(pageContext.getSession()).getFormatter().format(lc_cell, type, pageContext);				
			} catch (FormatException fe) {
				throw new JspException("format " + type + " failed (" + fe.getMessage() + ")");
				/*
				 JspException lc_jspException = new JspException("Format failed");
				 lc_jspException.initCause(fe);
				 throw lc_jspException;
				 */
			}
		}
		
		if (lc_hiddenValue!=null) {
			lc_cell = lc_hiddenValue + lc_cell;	
		}
		
		if (styleClass!=null) {
			collectionTag.setTempStyleClass(styleClass);
		}
		if (style!=null) {
			collectionTag.addTempStyle(style);
		}
		
		// Create a buffer. Try to set a good initial size.
		int lc_size = tryToGuessBufferSize(lc_cell, lc_url);		
		StringBuffer buffer = new StringBuffer(lc_size);
		
		// Initialize the context.
		context.arg0 = arg0;
		context.arg1 = arg1;
		context.item = lc_cell !=null ? lc_cell.toString() : "";
		
		context.footer = footer;
		context.footerArg0 = footerArg0;
		context.footerArg1 = footerArg1;
		
		context.onclick = onclick;
		context.property = property;
		context.sortProperty = sortable ? (property==null ? "" : property) : null;
		context.target = target;
		context.title = title;
		context.url = lc_url;
		context.width = width;
		context.mathOperation = mathOperation;
		context.mathPattern = mathPattern;
		
		collectionTag.addItem(buffer, context);				
		TagUtils.write(pageContext, buffer.toString());
		
		if (styleClass!=null) {
			collectionTag.setTempStyleClass(null);
		}
		if (style!=null) {
			collectionTag.removeTempStyle();	
		}
		//reset();
		
		pageContext.removeAttribute(SPAN_KEY);
		
		return EVAL_PAGE;
	}
	/**
	 * @return
	 */
	private int tryToGuessBufferSize(Object in_value, String in_url) {
		int lc_size = 16;
		if (in_url!=null) {
			// Pour <a href="..."></a>
			lc_size += in_url.length() + 15;			
		}
		
		if (onclick!=null) {
			// onclick="valeur"
			lc_size += onclick.length() + 10;
		}
		if (styleClass!=null) {
			lc_size += styleClass.length() + 8;
		}
		if (style!=null) {
			lc_size += style.length() + 8;
		}		
		
		if (in_value!=null) {
			lc_size += in_value.toString().length();
		}
		
		return lc_size;
	}
	
	protected Object buildContent() throws JspException {
		Object lc_cell = null;
		if (name == null) {
			// The item to add is a property of a bean in the current collection.
			lc_cell = collectionTag.getBean();
		} else {
			// The item to add is a property of a bean in the context.
			lc_cell = pageContext.findAttribute(name);
		}
		if (lc_cell != null && property != null) {
			// Get the property of the bean.
			lc_cell = getPropertyValue(lc_cell, property);
		}	
		return lc_cell;
	}
	
	protected String buildUrl() throws JspException {
		String lc_url = null;
		Object lc_cell = null;
		if (name == null) {
			// The item to add is a property of a bean in the current collection.
			lc_cell = collectionTag.getBean();
		} else {
			// The item to add is a property of a bean in the context.
			lc_cell = pageContext.findAttribute(name);
		}		
		if (lc_cell!=null && (url != null || page != null || forward!=null || action!=null)) {
			//url += LayoutUtils.getProperty(lc_cell, param);
			lc_url = getLink(url, lc_cell, param, paramId, paramName, paramProperty); 
		}			
		return lc_url;
	}
	
	protected boolean buildFilter() {
		return filter;		
	}
	
	public int doStartLayoutTag() throws JspException {
		context.reset();
		try {
			collectionTag = 
				(CollectionTag) findAncestorWithClass(this, CollectionTag.class);
			if (collectionTag.isFirst()) {
				MultiLevelHeader lc_header = new MultiLevelHeader(title, arg0, arg1, styleClass, true);
				lc_header.setTooltip(tooltip);
				lc_header.setWidth(width);
				lc_header.setSortProperty(sortable ? property : null);
				new CollectionItemEvent(this, lc_header).send();
				return SKIP_BODY;
			}
			if (name!=null) {
				if (!collectionTag.getSpans().containsKey(name)) {
					skip = true;
					return SKIP_BODY;	
				} else {
					pageContext.setAttribute(SPAN_KEY, collectionTag.getSpans().get(name));
				}
			}
			
		} catch (ClassCastException e) {
			throw new JspException("Invalid use of collectionItem tag");
		} catch (NullPointerException e) {
			throw new JspException("Invalid use of collectionItem tag");
		}
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Helper method to generate the hyperlink associated with a cell.
	 * 
	 * @param in_url           value of the url tag attribute
	 * @param in_bean          current bean in the iteration
	 * @param in_oldParam      value of the param attribute
	 * @param in_paramId       value of the paramId attribute
	 * @param in_paramName		value of the paramName attribute.
	 * @param in_paramProperty value of the paramProperty attribute
	 * 
	 * @return the hyperlink
	 */
	protected String getLink(String in_url, Object in_bean, String in_oldParam, String in_paramId, String in_paramName, String in_paramProperty) throws JspException {
		if (in_oldParam!=null) {
			// struts-layout <0.5 way of setting a unique parameter.
			return url + LayoutUtils.getProperty(in_bean, param);
		} else {
			// struts-layout >=0.5 way of setting multiple parameters.
			Map lc_params = new HashMap();
			if(in_paramId!= null) {
				StringTokenizer lc_paramIdTokens = new StringTokenizer(in_paramId, ",");
				StringTokenizer lc_paramPropertyTokens = null;
				if (in_paramProperty!=null) {
					lc_paramPropertyTokens = new StringTokenizer(in_paramProperty, ",");
				} 
				StringTokenizer lc_paramNameTokens = null;
				if (in_paramName!=null) {
					lc_paramNameTokens = new StringTokenizer(in_paramName, ",");
				}
				if (lc_paramPropertyTokens!=null && lc_paramPropertyTokens.countTokens()!=lc_paramIdTokens.countTokens()) {
					throw new JspException("paramId (" + paramId + ") and paramProperty (" + paramProperty + ") don't specify the same number of parameters");				
				}
				if (lc_paramNameTokens!=null && lc_paramNameTokens.countTokens()!=lc_paramIdTokens.countTokens()) {
					throw new JspException("paramId (" + paramId + ") and paramName (" + paramName + ") don't specify the same number of parameters");
				}
				
				while (lc_paramIdTokens.hasMoreTokens()) {
					String lc_paramId = lc_paramIdTokens.nextToken();
					Object lc_bean = in_bean;
					if (lc_paramNameTokens!=null) {
						lc_bean = pageContext.findAttribute(lc_paramNameTokens.nextToken());
					}
					String lc_paramProperty = null;
					if (lc_paramPropertyTokens!=null) {
						lc_paramProperty = lc_paramPropertyTokens.nextToken();
					} 
					lc_params.put(NestedHelper.getAdjustedProperty(lc_paramId, pageContext), LayoutUtils.getProperty(lc_bean, lc_paramProperty));
				}
			}
			String lc_url = LayoutUtils.computeURL(pageContext, forward, url, page, action, null, lc_params, null, false, target);
			
			return lc_url;
			/*
			 StringBuffer lc_buffer = new StringBuffer(url);
			 boolean lc_firstParam = url.indexOf('?')==-1;
			 while (lc_paramIdTokens.hasMoreTokens()) {
			 if (lc_firstParam) {
			 lc_buffer.append("?");
			 lc_firstParam = false;
			 } else {
			 lc_buffer.append("&");	
			 }
			 lc_buffer.append(lc_paramIdTokens.nextToken());
			 lc_buffer.append("=");
			 lc_buffer.append(LayoutUtils.getProperty(in_bean, lc_paramPropertyTokens.nextToken()));
			 }
			 return lc_buffer.toString();
			 */
		}
	}
	
	/**
	 * This method simply uses BeanUtils (indirectly) to return the value of the property 'in_property' 
	 * of in_bean. This method is intended to be overrided if needed.  
	 */
	protected Object getPropertyValue(Object in_bean, String in_property) throws JspException {
		return LayoutUtils.getProperty(in_bean, in_property);		
	}
	
	
	/**
	 * Creation date: (15/06/01 15:43:43)
	 * @return java.lang.String
	 */
	public java.lang.String getOnclick() {
		return onclick;
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @return java.lang.String
	 */
	public java.lang.String getParam() {
		return param;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (04/10/2001 12:38:46)
	 * @return java.lang.String
	 */
	public java.lang.String getType() {
		return type;
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @return java.lang.String
	 */
	public java.lang.String getUrl() {
		return url;
	}
	public void release() {
		super.release();
		property = null;
		
		title = null;
		arg0 = null;
		arg1 = null;
		
		tooltip = null;
		
		footer = null;
		footerArg0 = null;
		footerArg1= null;
		
		name = null;
		action = null;
		url = null;
		forward = null;
		page = null;
		param = null;
		paramId = null;
		paramName = null;
		paramProperty = null;
		onclick = null;
		sortable = false;
		type = null;
		target = null;
		width = null;
		styleClass = null;
		style = null;
		filter = true;
		skip = false;
		mathOperation = null;
	}
	protected void reset() {				
		collectionTag = null;
		
		type = jspType;
		jspType = null;
		
		footer = jspFooter;
		jspFooter = null;
        
        title = jspTitle;
        jspTitle = null;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	/**
	 * Creation date: (15/06/01 15:43:43)
	 * @param newOnclick java.lang.String
	 */
	public void setOnclick(java.lang.String newOnclick) {
		onclick = newOnclick;
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @param newParam java.lang.String
	 */
	public void setParam(java.lang.String newParam) {
		param = newParam;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (16/02/01 11:11:03)
	 * @param newProperty java.lang.String
	 */
	public void setProperty(java.lang.String newProperty) {
		property = newProperty;
	}
	public String getProperty() {
		return property;
	}
	public void setSortable(String sortable) {
		if ("true".equalsIgnoreCase(sortable)) {
			this.sortable = true;
		} else {
			this.sortable = false;	
		}
	}
	public String getSortable() {
		return String.valueOf(sortable);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (16/02/01 11:11:03)
	 * @param newHeader java.lang.String
	 */
	public void setTitle(java.lang.String newHeader) {
		title = newHeader;
	}
	public String getTitle() {
		return title;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (04/10/2001 12:38:46)
	 * @param newType java.lang.String
	 */
	public void setType(java.lang.String newType) {
		type = newType;
	}
	/**
	 * Creation date: (15/06/01 15:38:19)
	 * @param newUrl java.lang.String
	 */
	public void setUrl(java.lang.String newUrl) {
		url = newUrl;
	}
	public void setHref(String in_href) {
		url = in_href;	
	}
	public void setPage(String in_page) {
		page = in_page;	
	}
	public void setForward(String in_forward) {
		forward = in_forward;
	}
	/**
	 * Gets the target.
	 * @return Returns a String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * @param target The target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Gets the width.
	 * @return Returns a String
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * Gets the paramId.
	 * @return Returns a String
	 */
	public String getParamId() {
		return paramId;
	}

	/**
	 * Sets the paramId.
	 * @param paramId The paramId to set
	 */
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	/**
	 * Gets the paramProperty.
	 * @return Returns a String
	 */
	public String getParamProperty() {
		return paramProperty;
	}

	/**
	 * Sets the paramProperty.
	 * @param paramProperty The paramProperty to set
	 */
	public void setParamProperty(String paramProperty) {
		this.paramProperty = paramProperty;
	}
	
	public Object processStartLayoutEvent(StartLayoutEvent in_event) {
		return Boolean.FALSE;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) {
		return Boolean.FALSE;
		
	}
	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * Sets the style.
	 * @param style The style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * Sets the filter.
	 * @param filter The filter to set
	 */
	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	/**
	 * Sets the paramName.
	 * @param paramName The paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return Returns the arg0.
	 */
	public String getArg0() {
		return arg0;
	}
	/**
	 * @param arg0 The arg0 to set.
	 */
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}
	/**
	 * @return Returns the arg1.
	 */
	public String getArg1() {
		return arg1;
	}
	/**
	 * @param arg1 The arg1 to set.
	 */
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	protected void initDynamicValues() {
		super.initDynamicValues();
		
		jspType = type;
		type = Expression.evaluate(type, pageContext);
		
		jspFooter = footer;
		footer = Expression.evaluate(footer, pageContext);
        
        jspTitle = title;
        title = Expression.evaluate(title, pageContext);
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public void setFooterArg0(String arg0) {
		footerArg0 = arg0;
	}
	public void setFooterArg1(String arg1){
		footerArg1 = arg1;
	}
	/**
	 * @return Returns the mathPattern.
	 */
	public String getMathPattern() {
		return mathPattern;
	}
	/**
	 * @param mathPattern The mathPattern to set.
	 */
	public void setMathPattern(String mathPattern) {
		this.mathPattern = mathPattern;
	}
	/**
	 * @param tooltip The tooltip to set.
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}