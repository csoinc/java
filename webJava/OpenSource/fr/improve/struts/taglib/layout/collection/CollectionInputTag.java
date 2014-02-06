package fr.improve.struts.taglib.layout.collection;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.BodyLayoutTagSupport;
import fr.improve.struts.taglib.layout.WriteTag;
import fr.improve.struts.taglib.layout.collection.header.CollectionItemEvent;
import fr.improve.struts.taglib.layout.collection.header.MultiLevelHeader;
import fr.improve.struts.taglib.layout.el.EvaluationException;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.field.AbstractFieldTag;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
import fr.improve.struts.taglib.layout.policy.AbstractPolicy;
import fr.improve.struts.taglib.layout.util.FormUtils;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Add an input field column in a collection tag.
 * (the parent tag must be an instanceof BaseCollectionTag)
 * <br><br>
 * title - The key of the title to display for this property.<br>
 * property - The property to display.<br>
 * name - If specify, name of the bean we want to display the property. Default: the current bean in the collection.<br>
 * formProperty - name of the form property that will be generated.<br>
 * <br>
 * Too add text before and after the value, override doBeforeValue and doAfterValue.
 * <br/>
 * Several systems can be used to display the field read-only or read-write:
 * - the display mode
 * - the policy
 * - the collection selection
 * 
 * 
 * @see BaseCollectionTag BaseCollectionTag
 *
 * @author: Jean-Noel Ribette
 **/
public class CollectionInputTag extends BodyLayoutTagSupport {
	protected static final Log LOG = LogFactory.getLog(CollectionInputTag.class);
	public static final String MATH_ID_PREFIX = "mathData_";
	
	private boolean filter = true;
	
	protected String name;
	protected String property;
	protected String title;
	protected boolean sortable = false;
	protected String width;
	protected String size;	
	protected String maxlength;
	protected String styleClass;
	
	protected String formName;
	protected String formProperty;
	protected String formIndex;
	protected String keyProperty;
	
	protected String onchange;
	protected String onfocus;
	protected String mathOperation;
	protected String mathPattern;
	/**
	 * Use the collection selection system.
	 */
	protected boolean useCollectionSelection = false;
	
	/**
	 * Access right restriction policy name.
	 */
	protected String policy;
	
	/**
	 * the current display mode, computed by computeDisplaymode()
	 */
	protected short fieldDisplayMode = 2;

	/**
	 * Name of the generic formatter to use in inspect mode.
	 */
	protected String inspectFormatter;

	/**
	 * Name of the formatter to use in edit mode (also the default formatter if no formatter are specified in the others mode).
	 */
	protected String editFormatter;
	protected String jspEditFormatter;
	protected String jspOnChange;
	protected String jspStyleClass;

	/**
	 * Name of the formatter to use in create mode.
	 */
	protected String createFormatter;

	/**
	 * The current display formatter, computed bu computeDisplayMode()
	 */
	protected String formatter;

	protected String fieldName;
	
	/**
	 * The field display mode.
	 */
	protected String mode = "E,E,I";
	
	/**
	 * Nested collection support.
	 */
	protected boolean skip = false;
	public static final String SPAN_KEY = CollectionItemTag.SPAN_KEY;
	
	/**
	 * Toolip.
	 */
	protected String tooltip;
	
	/**
	 * Item content, for sending the tag attributes to the parent tag.
	 */
	protected SimpleItemContext context = createItemContext();
	
	/**
	 * Parent collection tag.
	 */
	protected CollectionTag parentCollectionTag;
	
	/**
	 * Create an ItemContext
	 */
	protected SimpleItemContext createItemContext() {
		return new SimpleItemContext();
	}

	/**
	 * set fieldDisplayMode to the correct display mode.
	 */
	protected void computeDisplayMode() throws JspException {
		// Get user runtime mode.
		Integer lc_userDisplayMode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().getFieldDisplayMode(pageContext, property);
		
		// Compute field display mode.
		if (lc_userDisplayMode == null) {
			fieldDisplayMode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().computeFieldDisplayMode(pageContext, mode);
		} else {
			fieldDisplayMode = lc_userDisplayMode.shortValue();
		}
		
		// Not all form mode are allowed !
		if (fieldDisplayMode!=AbstractModeFieldTag.MODE_NODISPLAY && 
				fieldDisplayMode!=AbstractModeFieldTag.MODE_EDIT && 
				fieldDisplayMode!=AbstractModeFieldTag.MODE_INSPECT) {
			throw new JspException("collectionInput only supports edit (E), inspect (I) and not display (N) modes");
		}
		
		// Need to check the form mode to use the right formatter.
		int lc_formMode = FormUtils.getFormDisplayMode(pageContext);
		switch (lc_formMode) {
		case FormUtils.CREATE_MODE :
			formatter = createFormatter;
			break;
		case FormUtils.EDIT_MODE :
			formatter = editFormatter;
			break;
		case FormUtils.INSPECT_MODE :
			formatter = inspectFormatter;
			break;
		}
		
		if (formatter==null || formatter.length()==0) formatter = editFormatter;
		
	}
	/**
	 * Returns some text to display after the value.
	 */
	protected String doAfterValue() throws JspException {
		// default implementation doing nothing.
		return "";
	}
	/**
	 * Returns some text to display before the value.
	 */
	protected String doBeforeValue() throws JspException {
		// default implementation doing nothing.
		return "";
	}

	protected short getEditAuthorizedMode() {
		if (policy == null)
			return fr.improve.struts.taglib.layout.field.AbstractFieldTag.MODE_EDIT;

		AbstractPolicy lc_policy = LayoutUtils.getSkin(pageContext.getSession()).getPolicy();
		return lc_policy.getAuthorizedDisplayMode(policy, name, property, pageContext);
	}	
	protected short getInspectAuthorizedMode() {
		if (policy == null)
			return fr.improve.struts.taglib.layout.field.AbstractFieldTag.MODE_INSPECT;

		AbstractPolicy lc_policy = LayoutUtils.getSkin(pageContext.getSession()).getPolicy();
		short lc_allowedDisplayMode;
		switch (lc_policy.getAuthorizedDisplayMode(policy, name, property, pageContext)) { 
			case AbstractFieldTag.MODE_DISABLED:
				lc_allowedDisplayMode = AbstractFieldTag.MODE_DISABLED;
				break;
			case AbstractFieldTag.MODE_READONLY:
				lc_allowedDisplayMode = AbstractFieldTag.MODE_READONLY;
				break;
			case AbstractFieldTag.MODE_EDIT:
			case AbstractFieldTag.MODE_INSPECT :
				lc_allowedDisplayMode = AbstractFieldTag.MODE_INSPECT;
				break;
			case AbstractFieldTag.MODE_HIDDEN:
				lc_allowedDisplayMode = AbstractFieldTag.MODE_HIDDEN;
				break;
			case AbstractFieldTag.MODE_INSPECT_ONLY:
				lc_allowedDisplayMode = AbstractFieldTag.MODE_INSPECT_ONLY;
				break;
			case AbstractFieldTag.MODE_NODISPLAY:
				lc_allowedDisplayMode = AbstractFieldTag.MODE_NODISPLAY;
				break;
			default:
				throw new IllegalStateException(lc_policy.getClass().getName() + " returns an illegal value");
				
		}
		return lc_allowedDisplayMode;
	}

	public int doEndLayoutTag() throws JspException {		
		if (skip) {
			// Nested collection support.
			skip = false;
			parentCollectionTag.incrementColumn();
			reset();
			return EVAL_PAGE;	
		}
		
		// Compute the display mode
		computeDisplayMode();
		
		if (fieldDisplayMode == AbstractFieldTag.MODE_NODISPLAY) {
			// The column should not be displayed.
			reset();
			return EVAL_PAGE;
		}	

		// StringBuffer to write into
		StringBuffer buffer = new StringBuffer();
		
		// Context common initialization.
		context.title = Expression.evaluate(title, pageContext);
		context.width = width;
		context.property = property;
		context.sortProperty = sortable?property:null;
		context.mathOperation = mathOperation;
		context.mathPattern = mathPattern;
		
		// First iteration to display the header.
		if (parentCollectionTag.isFirst()) {						
			parentCollectionTag.addItem(buffer, context);	
			TagUtils.write(pageContext, buffer.toString());
			reset();
			return EVAL_PAGE;
		}
		
		// Modify it according to the policy.
		switch (fieldDisplayMode) {
		case AbstractFieldTag.MODE_EDIT :
			fieldDisplayMode = getEditAuthorizedMode();
			break;
		case AbstractFieldTag.MODE_INSPECT :
			fieldDisplayMode = getInspectAuthorizedMode();
			break;
		}
		
		// Modify it according to the selected row.
		if (useCollectionSelection && fieldDisplayMode == AbstractFieldTag.MODE_EDIT && !parentCollectionTag.isCurrentBeanSelected()) {
			fieldDisplayMode = AbstractFieldTag.MODE_INSPECT;	
		}
		
		if (fieldDisplayMode == AbstractFieldTag.MODE_NODISPLAY) {
			// The field value should not be displayed.
			context.item = "";
			parentCollectionTag.addItem(buffer, context);	
			TagUtils.write(pageContext, buffer.toString());
			reset();
			return EVAL_PAGE;
		} else 
			try {

				// The input value to display
				Object value = null;

				// First build the field name
				try {
					fieldName = buildInputFieldName(parentCollectionTag);
				} catch (EvaluationException e) {
					TagUtils.saveException(pageContext, e);
					throw new JspException(e.getMessage());
				}

				// Check if there are error for this column.
				boolean anyError = false;
				ActionMessages lc_errors = (ActionMessages) pageContext.findAttribute(Globals.ERROR_KEY);
				if (lc_errors !=null && lc_errors.get(formProperty)!=null && lc_errors.get(formProperty).hasNext()) {
					anyError = true;
				}
				
				// Retrieve the error for this field.
				// PENDING find a nice way to display the error message
				List l_error = LayoutUtils.retrieveErrors(pageContext, fieldName);
				
				// Get the field value.
				value = buildInputFieldValue(parentCollectionTag, anyError);
				double lc_mathValue = 0;				
				
				if (mathOperation!=null) {
					lc_mathValue = LayoutUtils.getDouble(value);
					parentCollectionTag.storeMathData(lc_mathValue);
				}

				// Build the input field.
				StringBuffer l_field = new StringBuffer(doBeforeValue());
				Object formattedValue = null;
				if (mathPattern!=null){
					DecimalFormatSymbols lc_symbols = new DecimalFormatSymbols(LayoutUtils.getLocale(pageContext));
					// TODO Add the javascript parsing method
					lc_symbols.setDecimalSeparator('.');
					lc_symbols.setMonetaryDecimalSeparator('.');
					formattedValue = new DecimalFormat(mathPattern, lc_symbols).format(lc_mathValue);
				} else {
					// Format the field value.
					formattedValue = (formatter==null || anyError) ? filter(value) : WriteTag.write(pageContext, value, formatter);
				}
				if (fieldDisplayMode == AbstractFieldTag.MODE_EDIT || 
						fieldDisplayMode == AbstractFieldTag.MODE_DISABLED || 
						fieldDisplayMode == AbstractFieldTag.MODE_READONLY) {
					// Generate input field.
					appendFieldStart(l_field);
					l_field.append(" name=\"");
					l_field.append(fieldName);
					if (l_error != null && !l_error.isEmpty()) {
						l_field.append("\" class=\"ERROR");
					} else {
						if (styleClass!=null && styleClass.length()!=0)
							l_field.append("\" class=\""+ styleClass);
					}					
					if (mathOperation!=null && mathOperation.length()>0){
					// Generate a Id form the MathData JS
						l_field.append("\" id=\"");
						if (fieldName!=null) {
							l_field.append(MATH_ID_PREFIX);
							l_field.append("t" + parentCollectionTag.getMathOperationId());
							l_field.append("l" + parentCollectionTag.getIndex());
							l_field.append("c" + parentCollectionTag.getColumn());
						} 
					}					
					if (size != null) {
						l_field.append("\" size=\"");
						l_field.append(size);
					}
					if (maxlength != null) {
						l_field.append("\" maxlength=\"");
						l_field.append(maxlength);
					}
					if (tooltip!=null) {
						l_field.append("\" title=\"");
						l_field.append(LayoutUtils.getLabel(pageContext, Expression.evaluate(tooltip, pageContext), null));
					}
					if (onchange != null) {
						l_field.append("\" onchange=\"");
						l_field.append(Expression.evaluate(onchange, pageContext));
					}
					if (onfocus != null) {
						l_field.append("\" onfocus=\"");
						l_field.append(Expression.evaluate(onfocus, pageContext));
					}
					l_field.append("\"");
					if (fieldDisplayMode == AbstractFieldTag.MODE_DISABLED){
						l_field.append(" disabled");
					}
					if (fieldDisplayMode == AbstractFieldTag.MODE_READONLY){
						l_field.append(" readonly=\"true\"");
					}
					appendFieldValue(l_field, formattedValue);					
					String addition = buildAdditionalAttributes(parentCollectionTag);
					l_field.append(addition!=null && addition.length()>0 ? " " + addition : "");
					l_field.append(">");
				} else if (fieldDisplayMode == AbstractFieldTag.MODE_INSPECT) {
					// Generate hidden field.
					l_field.append("<input type=\"hidden\" name=\"");
					l_field.append(fieldName);
					l_field.append("\" value=\"");
					l_field.append(formattedValue==null ? "" : formattedValue);
					l_field.append("\">");					
					l_field.append(formattedValue == null ? "" : formattedValue);
				} else {
					l_field.append(formattedValue == null ? "" : formattedValue);
				}

				l_field.append(doAfterValue());

				// Ask the collection to build the table cell.
				context.item = l_field.toString();
				parentCollectionTag.addItem(buffer, context);

				// Print the table cell
				TagUtils.write(pageContext, buffer.toString());

			} catch (ClassCastException e) {
				throw new JspException("Invalid use of collectionInput tag");
			} catch (NullPointerException e) {
				throw new JspException("Invalid use of collectionInput tag");
			}
			reset();
			return EVAL_PAGE;
	}
	
	protected void appendFieldValue(StringBuffer in_buffer, Object in_value) {
		in_buffer.append(" value=\"");
		in_buffer.append(in_value==null ? "": in_value);
		in_buffer.append("\"");
	}

	protected void appendFieldStart(StringBuffer in_buffer) {
		in_buffer.append("<input type=\"");
		in_buffer.append(getType());
		in_buffer.append("\"");
	}
	protected Object buildInputFieldValue(CollectionTag in_parent, boolean in_anyError) throws JspException {

		// Get the field value from the form.
		Object value = null;
		
		String lc_fieldName = buildInputFieldName(in_parent);
		value = LayoutUtils.getBeanFromPageContext(pageContext, lc_fieldName);
				
		// No value in the form, get the value from the bean.
		if (value == null) {

			if (bodyContent != null && bodyContent.getString().length() > 0) {
				// The input field value is given in the body.
				value = bodyContent.getString();
				
			} else {
				if (name == null) {
					// get the value from the current bean in the collection
					value = LayoutUtils.getProperty(in_parent.getBean(), property);
				} else {
					// get the value from the pageContext
					value = LayoutUtils.getBeanFromPageContext(pageContext, name, property);
				}
			}
		}
		
		return value;
	}
	
	protected String getType() {
		return "text";
	}
	
	protected String buildAdditionalAttributes(CollectionTag in_parent) throws JspException {
		return "";
	}
	
	public int doStartLayoutTag() throws JspException {
		try {
			initDynamicValues();
			context.reset();			
			if (parentCollectionTag.isFirst()) {
				MultiLevelHeader lc_header = new MultiLevelHeader(title, null, null, styleClass, true);
				lc_header.setWidth(width);
				lc_header.setSortProperty(sortable ? property : null);
				new CollectionItemEvent(this, lc_header).send();
				return evaluateFirstBody();
			}
			
			if (name!=null) {
				// Nested collection support.
				if (parentCollectionTag.isNestedIteration() && !parentCollectionTag.getSpans().containsKey(name)) {
					skip = true;
					return SKIP_BODY;	
				} else {
					pageContext.setAttribute(SPAN_KEY, parentCollectionTag.getSpans().get(name));
				}
			}
		} catch (ClassCastException e) {
			LOG.error(e);
			throw new JspException("Invalid use of collectionInput tag");
		} catch (NullPointerException e) {
			LOG.error(e);
			throw new JspException("Invalid use of collectionInput tag");
		}
		return evaluateNextBody();
	}
	
	protected int evaluateFirstBody() {
		return SKIP_BODY;
	}
	protected int evaluateNextBody() {
		return EVAL_BODY_TAG;
	}
	
	protected void initDynamicValues() throws JspException {
		parentCollectionTag = (CollectionTag) findAncestorWithClass(this, CollectionTag.class);
		String lc_col = String.valueOf(parentCollectionTag.getColumn());
		jspEditFormatter = editFormatter;
		
		jspStyleClass = styleClass;
		if (styleClass==null){
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty("styleclass.collection",null);
		}		
		editFormatter = Expression.evaluate(editFormatter, pageContext);
		if (mathOperation!=null){
			
			// First build the field name
			try {
				fieldName = buildInputFieldName(parentCollectionTag);
			} catch (EvaluationException e) {
				TagUtils.saveException(pageContext, e);
				throw new JspException(e.getMessage());
			}
			
			// Check if there are error for this column.
			boolean anyError = false;
			ActionMessages lc_errors = (ActionMessages) pageContext.findAttribute(Globals.ERROR_KEY);
			if (lc_errors !=null && lc_errors.get(formProperty)!=null && lc_errors.get(formProperty).hasNext()) {
				anyError = true;
			}
			double lc_value = LayoutUtils.getDouble(buildInputFieldValue(parentCollectionTag, anyError));
			
			String lc_result = String.valueOf(lc_value);
			if (mathPattern!=null){
				DecimalFormatSymbols lc_symbols = new DecimalFormatSymbols(LayoutUtils.getLocale(pageContext));
				lc_symbols.setDecimalSeparator('.');
				lc_symbols.setMonetaryDecimalSeparator('.');
				lc_result = new DecimalFormat(mathPattern, lc_symbols).format(lc_value);
			} 
			
			jspOnChange = onchange;
			onchange = Expression.evaluate(onchange,pageContext);
			onchange = (onchange==null?"":onchange + ";") 
						+ "mathDataUpdate('"
						+ mathOperation 
						+ "','"	
						+ MATH_ID_PREFIX + "t" + parentCollectionTag.getMathOperationId() + "r" + lc_col 
						+ "','"
						+ parentCollectionTag.getMathOperationId()
						+ "','"
						+ lc_col
						+ "','"
						+ parentCollectionTag.getSize() 
						+ "','"
						+ lc_result
						+ "')"; 
		}
	}
	
	protected void reset() {
		editFormatter = jspEditFormatter;
		jspEditFormatter = null;
		styleClass = jspStyleClass;
		jspStyleClass = null;
		if (mathOperation!=null){
			onchange = jspOnChange;
			jspOnChange = null;
		}
		parentCollectionTag = null;
	}
	
	
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:51:21)
	 * @return java.lang.String
	 */
	public java.lang.String getCreateFormatter() {
		return createFormatter;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:48:41)
	 * @return java.lang.String
	 */
	public java.lang.String getEditFormatter() {
		return editFormatter;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (18/10/2001 11:23:06)
	 * @return java.lang.String
	 */
	public java.lang.String getFormIndex() {
		return formIndex;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (18/10/2001 11:23:06)
	 * @return java.lang.String
	 */
	public java.lang.String getFormProperty() {
		return formProperty;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:48:41)
	 * @return java.lang.String
	 */
	public java.lang.String getInspectFormatter() {
		return inspectFormatter;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 13:18:06)
	 * @return java.lang.String
	 */
	public java.lang.String getSize() {
		return size;
	}

	public java.lang.String getMaxlength() {
		return maxlength;
	}
	public void release() {
		super.release();
		
		// init the display properties
		property = null;
		title = null;
		name = null;
		size = null;
		maxlength = null;
		
		// init the javascript properties
		onchange = null;
		onfocus = null;

		sortable = false;
		width = null;
		
		inspectFormatter = null;
		editFormatter = null;

		// init the input properties
		formIndex = null;
		formName = null;
		formProperty = null;
		keyProperty = null;
		tooltip = null;

		// reset the form mode
		fieldDisplayMode = 2;
		policy = null;
		useCollectionSelection = false;
		mode = "E,E,I";

		fieldName = null;
		skip = false;
		filter = true;
		
		mathOperation = null;
		mathPattern = null;
	}
	
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:51:21)
	 * @param newCreateFormatter java.lang.String
	 */
	public void setCreateFormatter(java.lang.String newCreateFormatter) {
		createFormatter = newCreateFormatter;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:48:41)
	 * @param newEditFormatter java.lang.String
	 */
	public void setEditFormatter(java.lang.String newEditFormatter) {
		editFormatter = newEditFormatter;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (18/10/2001 11:23:06)
	 * @param newFormIndex java.lang.String
	 */
	public void setFormIndex(java.lang.String newFormIndex) {
		formIndex = newFormIndex;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (18/10/2001 11:23:06)
	 * @param newFormProperty java.lang.String
	 */
	public void setFormProperty(java.lang.String newFormProperty) {
		formProperty = newFormProperty;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 11:48:41)
	 * @param newInspectFormatter java.lang.String
	 */
	public void setInspectFormatter(java.lang.String newInspectFormatter) {
		inspectFormatter = newInspectFormatter;
	}
	/**
	 * Set the display mode
	 * format is XX,XX,XX where XX can be N (not displayed), E (editable), I (inspectable). Order is create mode, edit mode, inspect mode
	 */
	public void setMode(String in_mode) throws JspException {
		mode = in_mode;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (16/02/01 11:11:03)
	 * @param newProperty java.lang.String
	 */
	public void setProperty(java.lang.String newProperty) {
		property = newProperty;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/11/2001 13:18:06)
	 * @param newSize java.lang.String
	 */
	public void setSize(java.lang.String newSize) {
		size = newSize;
	}
	public void setMaxlength(java.lang.String newMaxlength) {
		maxlength = newMaxlength;
	}

	public void setSortable(String sortable) {
		if ("true".equalsIgnoreCase(sortable)) this.sortable = true;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (16/02/01 11:11:03)
	 * @param newHeader java.lang.String
	 */
	public void setTitle(java.lang.String newHeader) {
		title = newHeader;
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
	 * Sets the keyProperty.
	 * @param keyProperty The keyProperty to set
	 */
	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	/**
	 * Sets the onchange.
	 * @param onchange The onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * Sets the onfocus.
	 * @param onfocus The onfocus to set
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Returns the policy.
	 * @return String
	 */
	public String getPolicy() {
		return policy;
	}

	/**
	 * Sets the policy.
	 * @param policy The policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
	/**
	 * @param tooltip The tooltip to set.
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}	

	/**
	 * Sets the useCollectionSelection.
	 * @param useCollectionSelection The useCollectionSelection to set
	 */
	public void setUseCollectionSelection(boolean useCollectionSelection) {
		this.useCollectionSelection = useCollectionSelection;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String in_string) {
		formName = in_string;
	}
	
	public String getMathOperation() {
		return mathOperation;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public void setMathOperation(String mathOperation) {
		this.mathOperation = mathOperation;
	}
	
	public String getMathPattern() {
		return mathPattern;
	}
	public void setMathPattern(String mathPattern) {
		this.mathPattern = mathPattern;
	}
	protected String buildInputFieldName(CollectionTag in_parent) throws JspException, EvaluationException {		
		String lc_evaluatedFormProperty = Expression.evaluate(formProperty, pageContext);
		if (lc_evaluatedFormProperty!=null && !lc_evaluatedFormProperty.equals(formProperty)) {		
			return lc_evaluatedFormProperty;
		} else {	
			StringBuffer lc_fieldNameBuffer = new StringBuffer();
			if (formName!=null) {
				lc_fieldNameBuffer.append(formName);
				lc_fieldNameBuffer.append(".");
			}			
			lc_fieldNameBuffer.append(formProperty);		
			if (keyProperty==null) {
				lc_fieldNameBuffer.append("[");
				if (formIndex == null) {
					lc_fieldNameBuffer.append(in_parent.getIndex());
				} else {
					lc_fieldNameBuffer.append(pageContext.findAttribute(formIndex));
				}
				lc_fieldNameBuffer.append("]");
			} else {
				lc_fieldNameBuffer.append("(");
				lc_fieldNameBuffer.append(LayoutUtils.getProperty(in_parent.getBean(), keyProperty));
				lc_fieldNameBuffer.append(")");				
			}
			return lc_fieldNameBuffer.toString();
		}		
	}
	
	/**
	 * Filter value, if asked so.
	 */
	protected Object filter(Object in_value) {
		if (filter) {
			if (in_value==null) {
				return null;
			} else {
				return ResponseUtils.filter(in_value.toString());
			}
		} else {
			return in_value;
		}
	}
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		this.filter = filter;
	}
}
