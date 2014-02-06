package fr.improve.struts.taglib.layout.field;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Tag for creating multiple &lt;select&gt; options from a collection. The
 * collection may be part of the enclosing form, or may be independent of
 * the form. Each element of the collection must expose a 'label' and a
 * 'value', the property names of which are configurable by attributes of
 * this tag.
 * <p>
 * The collection may be an array of objects, a Collection, an Enumeration,
 * an Iterator, or a Map.
 * <p>
 * This tag is the struts-layout version of the struts-html tag.
 * <p> 
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 *
 * @author JN Ribette
 * @author Martin Cooper
 */

public class OptionsCollectionTag extends TagSupport implements Choice {

	// ----------------------------------------------------- Instance Variables

	/**
	 * The message resources for this package.
	 */
	protected static MessageResources messages =
		MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

	/**
	 * The name of the bean property containing the label.
	 */
	protected String label = "label";
	
	
	/**
	 * The computed label.
	 */
	protected String choiceLabel;
	
	/**
	 * The computed value.
	 */
	protected String choiceValue;	
	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * The name of the bean containing the values collection.
	 */
	protected String name = Constants.BEAN_KEY;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The name of the property to use to build the values collection.
	 */
	protected String property = null;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * The name of the bean property containing the value.
	 */
	protected String value = "value";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Process the start of this tag.
	 *
	 * @exception JspException if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {

	
		ChoiceTag choiceTag;	
		// Acquire the select or radios tag we are associated with
		choiceTag = (ChoiceTag) findAncestorWithClass(this, ChoiceTag.class);
		if (choiceTag==null) {
			throw new JspException(messages.getMessage("optionsTag.select"));
		}
		
		// Acquire the collection containing our options
		Object collection = TagUtils.lookup(pageContext, name, property, null);

		if (collection == null) {
			JspException e =
				new JspException(messages.getMessage("optionsCollectionTag.collection"));
			TagUtils.saveException(pageContext, e);
			throw e;
		}

		// Acquire an iterator over the options collection
		Iterator iter = LayoutUtils.getIterator(collection);

		StringBuffer sb = new StringBuffer();

		// Render the options
		while (iter.hasNext()) {

			Object bean = iter.next();
			Object beanLabel = null;
			Object beanValue = null;

			// Get the label for this option
			try {
				beanLabel = PropertyUtils.getProperty(bean, label);
				if (beanLabel == null) {
					beanLabel = "";
				}
			} catch (IllegalAccessException e) {
				JspException jspe =
					new JspException(messages.getMessage("getter.access", label, bean));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			} catch (InvocationTargetException e) {
				Throwable t = e.getTargetException();
				JspException jspe =
					new JspException(messages.getMessage("getter.result", label, t.toString()));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			} catch (NoSuchMethodException e) {
				JspException jspe =
					new JspException(messages.getMessage("getter.method", label, bean));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			}

			// Get the value for this option
			try {
				beanValue = PropertyUtils.getProperty(bean, value);
				if (beanValue == null) {
					beanValue = "";
				}
			} catch (IllegalAccessException e) {
				JspException jspe =
					new JspException(messages.getMessage("getter.access", value, bean));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			} catch (InvocationTargetException e) {
				Throwable t = e.getTargetException();
				JspException jspe =
					new JspException(messages.getMessage("getter.result", value, t.toString()));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			} catch (NoSuchMethodException e) {
				JspException jspe =
					new JspException(messages.getMessage("getter.method", value, bean));
				TagUtils.saveException(pageContext, jspe);
				throw jspe;
			}

			choiceLabel = beanLabel.toString();
			choiceValue = beanValue.toString();

			// Render this option
			choiceTag.addChoice(sb, this);			
		}

		// Render this element to our writer
		TagUtils.write(pageContext, sb.toString());

		return SKIP_BODY;
	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {
		super.release();	
		label = "label";
		name = Constants.BEAN_KEY;
		property = null;	
		value = "value";
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
