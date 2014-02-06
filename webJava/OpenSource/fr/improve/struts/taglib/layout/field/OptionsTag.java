package fr.improve.struts.taglib.layout.field;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Tag for creating multiple &lt;select&gt; options from a collection.  The
 * associated values displayed to the user may optionally be specified by a
 * second collection, or will be the same as the values themselves.  Each
 * collection may be an array of objects, a Collection, an Iterator, or a
 * Map.  <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 *
 * Tag modified to use the ChoiceTag interface of struts-layout
 *
 * @author Florent Carpentier
 * @author Craig McClanahan
 * @author Jean-Noel Ribette
 */

public class OptionsTag extends TagSupport implements Choice {

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
	 MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

	/**
	 * The name of the collection containing beans that have properties to
	 * provide both the values and the labels (identified by the
	 * <code>property</code> and <code>labelProperty</code> attributes).
	 */
	protected String collection = null;

	/**
	 * The name of the bean containing the labels collection.
	 */
	protected String labelName = null;

	/**
	 * The bean property containing the labels collection.
	 */
	protected String labelProperty = null;

	/**
	 * The name of the bean containing the values collection.
	 */
	protected String name=null;

	/**
	 * The name of the property to use to build the values collection.
	 */
	protected String property=null;
	protected String jspProperty = null;
	
	/**
	 * The name of the other select tags whose values depends of values definied by this options tag.
	 */
	protected String sourceOf;
	
	protected String choiceLabel;
	protected String choiceValue;

	/**
	 * Process the end of this tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {

		ChoiceTag choiceTag;	
	// Acquire the select or radios tag we are associated with
		try {
			choiceTag = (ChoiceTag) getParent();
		} catch (ClassCastException e) {
		    throw new JspException(messages.getMessage("optionsTag.select"));
		}
		
		// Compute dynamic values.
		initDynamicValues();

	StringBuffer sb = new StringBuffer();	
	// If a collection was specified, use that mode to render options
		if (collection != null) {
			Iterator collIterator = getIterator(collection, null);
			while (collIterator.hasNext()) {
				Object bean = collIterator.next();
				Object value = null;
				Object label = null;;

					value = getValueFromBean(bean, property);
					if (value == null)
						value = "";
					if (labelProperty != null)
						label = getLabelFromBean(bean, labelProperty);
					else
						label = value;
					if (label == null)
						label = "";
				choiceLabel = label.toString();
				choiceValue = value.toString();
				choiceTag.addChoice(sb, this);
			}
			if (sourceOf!=null) {
				initDependentCombo();
			}
		}

		// Otherwise, use the separate iterators mode to render options
			else {

			  // Construct iterators for the values and labels collections
			  Iterator valuesIterator = getIterator(name, property);
			  Iterator labelsIterator = null;
			  if ((labelName == null) && (labelProperty == null))
				  labelsIterator = getIterator(name, property); // Same coll.
			  else
				  labelsIterator = getIterator(labelName, labelProperty);

			  // Render the options tags for each element of the values coll.
			  while (valuesIterator.hasNext()) {
				  Object obj = valuesIterator.next();
				  choiceValue = obj==null ? null : obj.toString();
				  choiceLabel = choiceValue;
				  if (labelsIterator.hasNext()) {
					  obj = labelsIterator.next();
					  choiceLabel = obj==null ? null : obj.toString();
				  }
				  choiceTag.addChoice(sb, this);
			  }
		}

	// Render this element to our writer
	TagUtils.write(pageContext, sb.toString());
	
	// Reset
	reset();

	return EVAL_PAGE;

	}
		
	/**
	 * Init dynamic values.
	 */
	protected void initDynamicValues() {
		jspProperty = property;		
		property = Expression.evaluate(jspProperty, pageContext);
	}
	
	/**
	 * Reset dynamic values.	 
	 */
	protected void reset() {		
		property = jspProperty;
		jspProperty = null;
	}

	/**
	 * make two combo interdependent.
	 */
	protected void initDependentCombo() {
		SelectTag lc_select = (SelectTag) findAncestorWithClass(this, SelectTag.class);
		OptionsDependentTag.DependentInfo lc_info = (OptionsDependentTag.DependentInfo) OptionsDependentTag.getDependentInfo(lc_select.getProperty(), pageContext);
		lc_info.mainCollectionName = collection;
		lc_info.mainCollectionProperty = null;
		lc_info.mainCollectionBeanProperty = property;
	}

	protected Object getLabelFromBean(Object in_bean, String in_labelProperty) throws JspException {		
		return LayoutUtils.getProperty(in_bean, in_labelProperty);
	}
	protected Object getValueFromBean(Object in_bean, String in_property) throws JspException {
		return LayoutUtils.getProperty(in_bean, in_property);
	}
	/**
	 * Process the start of this tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */

	public int doStartTag() throws JspException {
	return SKIP_BODY;
	}
	public String getCollection() {
		return (this.collection);
	}
	/**
	 * Return an iterator for the option labels or values, based on our
	 * configured properties.
	 *
	 * @param name Name of the bean attribute (if any)
	 * @param property Name of the bean property (if any)
	 *
	 * @exception JspException if an error occurs
	 */
	protected Iterator getIterator(String name, String property) throws JspException {
	    String lc_beanName = name;
	    if (lc_beanName == null) {
        	lc_beanName = Constants.BEAN_KEY;
	    }
		return LayoutUtils.getIterator(pageContext, lc_beanName, property);
			
	}
	public String getLabelName() {
		return labelName;
	}
	public String getLabelProperty() {
		return labelProperty;
	}
	public String getName() {
		return name;
	}
	public String getProperty() {
		return property;
	}
	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();
		collection = null;
		labelName = null;
		labelProperty = null;
		name = null;
		property = null;
		sourceOf = null;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public void setLabelProperty(String labelProperty) {
		this.labelProperty = labelProperty;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * @return Returns the sourceOf.
	 */
	public String getSourceOf() {
		return sourceOf;
	}
	/**
	 * @param sourceOf The sourceOf to set.
	 */
	public void setSourceOf(String sourceOf) {
		this.sourceOf = sourceOf;
	}
	
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceLabel()
	 */
	public String getChoiceLabel() {
		return choiceLabel;
	}
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceTooltip()
	 */
	public String getChoiceTooltip() {
		return null;
	}
	/**
	 * @see fr.improve.struts.taglib.layout.field.Choice#getChoiceValue()
	 */
	public String getChoiceValue() {
		return choiceValue;
	}
}
