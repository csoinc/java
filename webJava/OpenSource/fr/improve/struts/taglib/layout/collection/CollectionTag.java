package fr.improve.struts.taglib.layout.collection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.RequestUtils;

import fr.improve.struts.taglib.layout.collection.header.MultiLevelHeader;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener;
import fr.improve.struts.taglib.layout.sort.SortUtil;
import fr.improve.struts.taglib.layout.util.IFooterRenderer;
import fr.improve.struts.taglib.layout.util.IMathCollectionRenderer;
import fr.improve.struts.taglib.layout.util.IMultiLevelHeaderRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;



/**
 * This class is responsible for displaying the collection.<br />
 * Actually, the rendering is done by another class implementing the CollectionInterface.
 * This class just computes differents parameters (headers and items).
 * @author: Jean-Noel Ribette
 **/
public class CollectionTag extends BaseCollectionTag implements StaticCodeIncludeListener {
	/**
	 * Math class
	 */
	static Class mathClass;
	
	static {
		try {
			mathClass = RequestUtils.applicationClass("org.apache.commons.math.stat.StatUtils");
		} catch (ClassNotFoundException e) {
			// Math class is not available.
		}
	}
	
	/**
	 * String of the static code to generate.
	 */
	private String staticCode = "";
	
	/**
	 * Map of the numbers for operation 
	 */
	protected Map numbers = new HashMap();
	
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.BodyLayoutTagSupport#reset()
	 */
	protected void reset() {
		numbers.clear();
		super.reset();
	}
	/**
	 * Store in a List the value of the cells for selected column in a map 
	 * @author Damien Viel
	 * @version 1.0
	 * @throws JspException
	 *
	 */
	protected void storeMathData(double in_value) throws JspException {
		if (!isFirst()) {
	        int lc_ligne = getIndex();
	        int lc_col = getColumn();
	        Object bean = getBean();
	        String property = ((ItemContext)headers.get(lc_col)).getProperty();
	        String mathOp =  ((ItemContext)headers.get(lc_col)).getMathOperation();
	        if (mathOp!=null) {
	           String key = String.valueOf(lc_col);
	           if (!numbers.containsKey(key)){
	           		double[] lc_list = new double[size]; // ???
	           		numbers.put(key,lc_list);
	           }
	           	double[] lc_list = (double[]) numbers.get(key);       	
	           lc_list[index-1] = in_value;
	        }
		}
    }
	/**
	 * 
	 * @author Damien Viel
	 * @param in_operation
	 * @param in_data
	 * @return
	 * @throws JspException
	 */
	protected String doMathOperation(String in_operation, double[] in_data, String in_mathPattern) throws JspException {
		if (in_data!=null && in_operation!=null && in_operation.length()>0){			
			double total = computeMathResult(in_operation, in_data);
			if (in_mathPattern!=null){
				NumberFormat lc_format = new DecimalFormat(in_mathPattern);
				return lc_format.format(total);				
			}
			return String.valueOf(total);
		}
		return null;
	}
	/**
	 * 
	 * @author JNRibette
	 * @param in_operation
	 * @param in_data
	 * @return
	 * @throws JspException
	 */
	protected double computeMathResult(String in_operation, double[] in_data) throws JspException {
		if (mathClass==null) {
			throw new IllegalStateException("Math operation is not available, please put commons-math.jar in the classpath");
		}
		Method m;
		try {
			m = mathClass.getMethod(in_operation, new Class[]{double[].class});
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Math operation " + in_operation + " is not supported");
		}
		Double result = null;
		try {
			result = (Double) m.invoke(null,new Object[]{in_data});
		} catch (InvocationTargetException e) {
			// TODO log
			throw new JspException("Math operation " + in_operation + " failed : " + e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO log
			throw new JspException("Math operation " + in_operation + " failed : " + e.getMessage());
		}
		return result.doubleValue();
		//return new BigDecimal(result.doubleValue()).setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	
/**
 * add an item.
 */
protected void addItem(StringBuffer in_buffer, String in_item, String in_url, String in_target, String in_onclick) throws JspException {
	// Display the table cell.		
	if (in_url==null) {
		renderItem(in_buffer, in_item);
	} else {
		StringBuffer lc_buffer = new StringBuffer();
		String lc_item = in_item;
		if (sortType==SORT_JAVASCRIPT) {
			if (lc_item.startsWith("<!--")) {		
				// if we are sorting the collection on the client, make sure the resulting item still begin with the comment 
				// which may be used to sort the items.
				int lc_index = lc_item.indexOf("-->");
				lc_buffer.append(lc_item.substring(0, lc_index +3));
				lc_item = lc_item.substring(lc_index+3);
			} else {
				// same concern, in case there is no preliminar comment.
				lc_buffer.append("<!-- ");
				lc_buffer.append(in_item);
				lc_buffer.append("-->");
			}
		}
		
		lc_buffer.append("<a href=\"");
		lc_buffer.append(((HttpServletResponse)pageContext.getResponse()).encodeURL(in_url));
		if (in_onclick!=null) {
			lc_buffer.append("\" onClick=\"");
			lc_buffer.append(Expression.evaluate(in_onclick, pageContext));		
		}
		if (in_target!=null) {
			lc_buffer.append("\" target=\"");
			lc_buffer.append(in_target);
		}
		String lc_styleClass = styleClass;
		if (tempStyleClass != null) lc_styleClass = tempStyleClass;
		if (lc_styleClass != null) {
			lc_buffer.append("\" class=\"");
			lc_buffer.append(lc_styleClass);
		}		
		lc_buffer.append("\">");
		lc_buffer.append(lc_item);
		lc_buffer.append("</a>");
		renderItem(in_buffer, lc_buffer.toString());
	}
}


protected void renderBlankCollection(StringBuffer in_buffer) throws JspException { 
    panel.doPrintEmptyCollection(in_buffer, LayoutUtils.getLabel(pageContext, getBundle(), emptyKey, null, false));
}
	// render the end of the table
	protected void renderEnd(StringBuffer buffer) throws JspException {
		renderMathData(buffer);
		renderFooter(buffer);
		panel.doEndPanel(buffer);
		renderStaticCode(buffer);
	}
	
	/**
	 * Render the result for the specified math operation
	 * @param in_buffer
	 * @throws JspException
	 */
	protected void renderMathData(StringBuffer in_buffer) throws JspException {
		if (!(panel instanceof IMathCollectionRenderer)) {
			return;
		}
		IMathCollectionRenderer lc_renderer = (IMathCollectionRenderer) panel;
	
		// did we start generated footer ?
		boolean started = false;
		// number of null consecutive footer.
		int consecutiveNull = 0;
		String uniqueParam = mathOperationId;
		int lc_nbOfSpan = 0; 
		for (int i=0; i < headers.size(); i++) {
			ItemContext lc_header = (ItemContext) headers.get(i);
			String lc_operation = lc_header.getMathOperation();
			String lc_mathStyleClass = tempStyleClass!=null ? tempStyleClass : styleClass; 
			String lc_mathPattern = lc_header.getMathPattern();
			double[] data = (double[])numbers.get(String.valueOf(i));
			String lc_data = doMathOperation(lc_operation,data,lc_mathPattern);
			// Unique identifier for the result cell
			String resultId = CollectionInputTag.MATH_ID_PREFIX + "t" + uniqueParam + "r" + i;
			
			// If there is no math operation to do
			if (lc_operation==null) {
				consecutiveNull++;
			} else {
				if (!started){
					// Start rendering result !
					lc_renderer.startMathData(in_buffer);
					started = true;
				}
								
				// Generate an empty td with colspan = number of consecutive null.
				if (consecutiveNull>0) {
					lc_renderer.renderMathData(in_buffer, "&nbsp;", consecutiveNull, null,lc_mathStyleClass);
					lc_nbOfSpan = lc_nbOfSpan + consecutiveNull;
				}
				
				// Generate the result cell.
				lc_renderer.renderMathData(in_buffer, lc_data, 1, resultId,lc_mathStyleClass);
				consecutiveNull = 0;
				lc_nbOfSpan = lc_nbOfSpan + 1;
			}
		}
		if (started) {
			if (headers.size()-lc_nbOfSpan>0){
				lc_renderer.renderMathData(in_buffer, "&nbsp;", headers.size()-lc_nbOfSpan, null,styleClass);
			}
			// There were result, close.
			lc_renderer.endMathData(in_buffer);
		}
		
	}
	
	/**
	 * Render the footer of the collection.
	 */
	protected void renderFooter(StringBuffer in_buffer) throws JspException {
		// Get IFooterRenderer
		if (!(panel instanceof IFooterRenderer)) {
			return;
		}
		IFooterRenderer lc_renderer = (IFooterRenderer) panel;
		
		// did we start generated footer ?
		boolean started = false;
		
		// number of null consecutive footer.
		int consecutiveNull = 0;
		
		for (int i=0; i < headers.size(); i++) {
			ItemContext lc_header = (ItemContext) headers.get(i);
			String lc_footer = lc_header.getFooter();
			if (lc_footer==null) {
				consecutiveNull++;
			} else {
				if (!started){
					// Start rendering footer !
					lc_renderer.startFooter(in_buffer);
					started = true;
				}
				
				// Generate an empty td with colspan = number of consecutive null.
				if (consecutiveNull>0) {
					lc_renderer.printFooterElement(in_buffer, "&nbsp;", consecutiveNull);
				}
				
				// Generate the footer.
				Object[] lc_args = new Object[5];
				lc_args[0] = Expression.evaluate(lc_header.getFooterArg0(), pageContext);
				lc_args[1] = Expression.evaluate(lc_header.getFooterArg1(), pageContext);
				String lc_label = LayoutUtils.getLabel(pageContext, getBundle(), lc_footer, lc_args, false);
				lc_renderer.printFooterElement(in_buffer, lc_label, 1);
				consecutiveNull = 0;
				
			}
		}
		
		if (started) {
			// There were footer, close.
			lc_renderer.endFooter(in_buffer);
		}
	}
	
	
/**
 * Print a column title to the buffer.
 * (mono level header)
 */
protected void renderHeader(StringBuffer buffer, ItemContext in_header) throws JspException {
	
	// Compute the title.
	String lc_key = in_header.getTitle();
	String lc_arg0 = Expression.evaluate(in_header.getArg0(), pageContext);
	String lc_arg1 = Expression.evaluate(in_header.getArg1(), pageContext); 
	Object[] lc_args = new Object[2];
	lc_args[0] = lc_arg0;
	lc_args[1] = lc_arg1;
	String lc_title = LayoutUtils.getLabel(pageContext, getBundle(), lc_key, lc_args, false);
	
	// Compute the sort url.
	String lc_sortUrl = computeSortUrl(in_header.getSortProperty());
		
	// Print the title.
	panel.doPrintHeader(buffer, lc_title, in_header.getWidth(), lc_sortUrl);
}

	protected String computeSortUrl(String in_sortProperty) {
		String lc_sortUrl = null;
		if (in_sortProperty!=null) {
			StringBuffer lc_tempBuffer = new StringBuffer();	
			switch (sortType) {
				case SORT_LAYOUT:
					String lc_unEncodedUrl = SortUtil.getURLForCollection(in_sortProperty, (javax.servlet.http.HttpServletRequest) pageContext.getRequest());
					lc_tempBuffer.append(((HttpServletResponse)pageContext.getResponse()).encodeURL(lc_unEncodedUrl));
					break;
				case SORT_CUSTOM:
					// use user custom sort action.								
					if (! sortAction.toLowerCase().startsWith("javascript:")) {
						// No js code : go to the server to sort.
						lc_tempBuffer.append(sortAction);
						if (sortAction.indexOf("?")!=-1) lc_tempBuffer.append("&"); else lc_tempBuffer.append("?");
						lc_tempBuffer.append(sortParam);
						lc_tempBuffer.append("=");
						lc_tempBuffer.append(in_sortProperty);
					} else {
						// Js code : need to put the right parameters at their place.					
				        String javascriptSortParamName = "sortParam";
						if (sortParam != null && sortParam.trim().length() > 0) {
							javascriptSortParamName = sortParam;
						}
						pageContext.setAttribute(javascriptSortParamName, in_sortProperty);					
						lc_tempBuffer.append(Expression.evaluate(sortAction, pageContext));
						pageContext.removeAttribute(javascriptSortParamName);
					}
					break;				
				case SORT_JAVASCRIPT:
					// sorting on the client browser with javascript.
					lc_tempBuffer.append("javascript:arraySort(");
					lc_tempBuffer.append(sortParam);
					lc_tempBuffer.append(",");
					lc_tempBuffer.append(column);
					lc_tempBuffer.append(",");
					lc_tempBuffer.append(size);
					lc_tempBuffer.append(",");
					lc_tempBuffer.append(nbOfColumns);
					lc_tempBuffer.append(")");
					break;
			}
			lc_sortUrl = lc_tempBuffer.toString();
		}
		return lc_sortUrl;
	}
	
	private MultiLevelHeader currentHeader;

	/**
	 * Print a column title to the buffer. 
	 * (multi level header)
	 */
	protected void renderMultiLevelHeaders(StringBuffer in_buffer, List in_multiLevelHeaders, int in_level) throws JspException {
		if (in_multiLevelHeaders!=null) {
			List lc_nestedLevels = null;
			Iterator lc_it = in_multiLevelHeaders.iterator();
			IMultiLevelHeaderRenderer lc_panel = (IMultiLevelHeaderRenderer) panel;			
			column = 0;			
			
			// Did we start a header row ?
			boolean lc_started = false;
			
			while (lc_it.hasNext()) {				
				MultiLevelHeader lc_header = (MultiLevelHeader) lc_it.next();
				List lc_headerChildren = lc_header.getChildHeaders();
				int lc_rowSpan = lc_headerChildren!=null ? 1 : in_level+1 - lc_header.getLevel();
				
				Object[] lc_args = new Object[2];
				lc_args[0] = Expression.evaluate(lc_header.getArg0(), pageContext);
				lc_args[1] = Expression.evaluate(lc_header.getArg1(), pageContext);
				String lc_title = LayoutUtils.getLabel(pageContext, getBundle(), lc_header.getTitle(), lc_args, false);
				String lc_sortUrl = computeSortUrl(lc_header.getSortProperty());
				String lc_styleClass = lc_header.getStyleClass()==null ? getStyleClass() : lc_header.getStyleClass();
				String lc_tooltip = LayoutUtils.getLabel(pageContext, getBundle(), lc_header.getTooltip(), null, false);
				currentHeader = lc_header;
				
				if (lc_title!=null) {
					// Title is not null, render it.
					
					if (!lc_started) {
						// First time we render a title, start a title row.
						lc_panel.startMultiLevelHeaderRow(in_buffer);
						lc_started = true;
					}
					
					// Render the title.
					lc_panel.renderMultiLevelHeader(in_buffer, lc_title, lc_sortUrl, lc_styleClass, lc_header.getColSpan(), lc_rowSpan, lc_header.getWidth());
				}
				currentHeader = null;
				if (lc_headerChildren!=null) {
					if (lc_nestedLevels==null) {
						lc_nestedLevels = new ArrayList();
					}
					lc_nestedLevels.addAll(lc_headerChildren);
				}
				column++;
			}			
			if(lc_started) {
				lc_panel.endMultiLevelHeaderRow(in_buffer);
			}
			renderMultiLevelHeaders(in_buffer, lc_nestedLevels, in_level-1);
		}
	}
	
	public MultiLevelHeader getCurrentHeader() {
		return currentHeader;
	}

protected void renderItem(StringBuffer buffer, String in_item) throws JspException {
	
	String lc_item = in_item;

	// if the line of the table are selectable and this is the first column, add a checkbox or a radio button.
	if (needSelect && !selectHidden) {
		StringBuffer lc_tempBuffer = new StringBuffer();
		if (sortType==SORT_JAVASCRIPT && lc_item.startsWith("<!--")) {
			// if we are sorting the collection on the client, make sure the resulting item still begin with the comment 
			// which may be used to sort the items.
			int lc_index = lc_item.indexOf("-->");
			lc_tempBuffer.append(lc_item.substring(0, lc_index +3));
			lc_item = lc_item.substring(lc_index+3);
		}
		renderSelection(lc_tempBuffer);
		needSelect = false;
		lc_tempBuffer.append(lc_item);
		lc_item = lc_tempBuffer.toString();
	}

	
	String[] lc_styleClasses = new String[1 + tempStyles.size()];
	lc_styleClasses[0] = tempStyleClass!=null ? tempStyleClass : styleClass;
	for (int i=0; i <  tempStyles.size(); i++) {
		lc_styleClasses[i+1] = (String) tempStyles.get(i);
	}
	
	panel.doPrintItem(buffer, lc_item, lc_styleClasses, sortType==SORT_JAVASCRIPT ? "t" + sortParam + "l" + index + "c" + column : null);
}

	protected void renderSelection(StringBuffer lc_tempBuffer) throws JspException {
		lc_tempBuffer.append("<input type=\"");
		if ("checkbox".equalsIgnoreCase(selectType)) {
			lc_tempBuffer.append("checkbox");	
		} else {
			lc_tempBuffer.append("radio");	
		}
		lc_tempBuffer.append("\" name=\"");
		if (selectName!=null) {
			lc_tempBuffer.append(selectName);
		} else {
			lc_tempBuffer.append(selectProperty);	
		}	
		if ("checkbox".equalsIgnoreCase(selectType)) {
			if (selectId!=null) {
				lc_tempBuffer.append("(");
				lc_tempBuffer.append(LayoutUtils.getProperty(bean, selectId));
				lc_tempBuffer.append(")");
			} else {
				lc_tempBuffer.append("[");
				lc_tempBuffer.append(index-1);
				lc_tempBuffer.append("]");
				if (selectIndex!=null) {
					lc_tempBuffer.append(".");
					lc_tempBuffer.append(selectIndex);
				}
			}							
		}
		
		if (onClick!=null) {
			lc_tempBuffer.append("\" onclick=\"");
			lc_tempBuffer.append(onClick);	
		}
		
		lc_tempBuffer.append("\" value=\"");
		Object lc_value = LayoutUtils.getProperty(bean, selectProperty);
		lc_tempBuffer.append(lc_value);
		lc_tempBuffer.append("\"");
		
		// check if the value is selected.
		if (isCurrentBeanSelected()) {
			lc_tempBuffer.append(" checked");
		}
	
		lc_tempBuffer.append(">");
}

	protected void renderStart(StringBuffer out_buffer) throws JspException {
		// Display the title.
		// we do not use the possibility of the PanelTag to do that.
		Object[] lc_args = new Object[5];
		Object lc_arg0 = null;
		if (arg0Name!=null) {
			lc_arg0 = LayoutUtils.getBeanFromPageContext(pageContext, arg0Name, null);
		}
		lc_args[0] = lc_arg0;
		panel.doPrintTitle(out_buffer, LayoutUtils.getLabel(pageContext, getBundle(), title, lc_args, false));
				
		panel.doStartPanel(out_buffer, align,width);				
	}
	
	 
    /**
     * Returns true if the bean at the current index is selected. Results only valid 
     * when called once iteration is under way.
     * @return boolean
     * @throws JspException
     * @author LeeFreyberg
     */
    public boolean isCurrentBeanSelected() throws JspException{
        if (selectName!=null) {
            Object lc_selectedValue = null;
            if ("checkbox".equalsIgnoreCase(selectType)) {
                if (selectId==null) {
                    lc_selectedValue = LayoutUtils.getBeanFromPageContext(pageContext, Constants.BEAN_KEY, selectName + "[" + (index-1) +"]");
                } else {
                    lc_selectedValue = LayoutUtils.getBeanFromPageContext(pageContext, Constants.BEAN_KEY, selectName + "(" + LayoutUtils.getProperty(bean, selectId) +")");
                }
            } else {
                lc_selectedValue = LayoutUtils.getBeanFromPageContext(pageContext, Constants.BEAN_KEY, selectName);
            }
            if (lc_selectedValue!=null && lc_selectedValue.equals(LayoutUtils.getProperty(bean, selectProperty))) {
                return true;
            }
        }
        return false;
    }
	public Object processStaticCodeIncludeEvent(StaticCodeIncludeLayoutEvent in_event) throws JspException {
		String lc_codeToPrint = (String) in_event.sendToParent(this);
		staticCode += lc_codeToPrint;
		return "";
	}
	protected void renderStaticCode(StringBuffer out_buffer) {
		if (staticCode.length()>0) {
			out_buffer.append(staticCode);
			staticCode = "";
		}
	}
}
