package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.MessageResources;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Base class for struts layout tags, as quite a lof of tags have a label
 * @author: Jean-Noel Ribette
 */
public abstract class LabelledTag extends LayoutTagSupport {

	protected static MessageResources messages =
		MessageResources.getMessageResources(Constants.Package + ".LocalStrings");

	protected String arg0;
	protected String arg1;
	protected String arg2;
	protected String arg3;
	protected String arg4;
	
	protected String arg0Name;
	protected String arg0Property;
	
	protected String arg1Name;
	protected String arg1Property;

	protected String key;

	protected String bundle = Globals.MESSAGES_KEY;

	protected String localeKey = Globals.LOCALE_KEY;

	protected String name;
	protected String property;

	protected String styleClass;
	protected String style;
	
	/**
	 * Id of the struts-layout tag. Can be used to hide or display all the generated HTML.
	 */
	protected String layoutId;
	
	/**
	 * The current skin
	 */
	private Skin skin = null;
		
	public String getKey() {
		return key;
	}
	protected String getLabel() throws JspException {
		try {
			if (key == null && name != null)
				return (String) LayoutUtils
					.getBeanFromPageContext(pageContext, name, property)
					.toString();
		} catch (ClassCastException l_cce) {
			throw new JspException("oups");
		}
		
		String lc_key = null;
		String[] lc_args = new String[5];
		String lc_arg0 = null;
		String lc_arg1 = null;
		
		Object lc_obj;
		if (arg0Name != null) {
			lc_obj = LayoutUtils.getBeanFromPageContext(pageContext, arg0Name, arg0Property);
			if (lc_obj != null) {
				lc_arg0 = lc_obj.toString();
			}
		} else {
			lc_arg0 = Expression.evaluate(arg0, pageContext);
		}
		if (arg1Name != null) {
			lc_obj = LayoutUtils.getBeanFromPageContext(pageContext, arg1Name, arg1Property);
			if (lc_obj != null) {
				lc_arg1 = lc_obj.toString();
			}
		} else {
			lc_arg1 = Expression.evaluate(arg1, pageContext);	
		}
		
		lc_key = Expression.evaluate(key, pageContext);
		lc_args[0] = lc_arg0;
		lc_args[1] = lc_arg1;
		lc_args[2] = Expression.evaluate(arg2, pageContext);
		lc_args[3] = Expression.evaluate(arg3, pageContext);
		lc_args[4] = Expression.evaluate(arg4, pageContext);
		return LayoutUtils.getLabel(pageContext, bundle, lc_key, lc_args, false);
	}
	public String getName() {
		return name;
	}

	public String getProperty() {
		return property;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setPageContext(PageContext in_pageContext) {
		super.setPageContext(in_pageContext);
		skin = LayoutUtils.getSkin(in_pageContext.getSession());
	}
	protected Skin getSkin() {
		return skin;	
	}
	public void release() {
		super.release();
		key = null;
		bundle = Globals.MESSAGES_KEY;
		localeKey = Globals.LOCALE_KEY;
		arg0 = null;
		arg1 = null;
		arg2 = null;
		arg3 = null;
		arg4 = null;
		name = null;
		property = null;
		styleClass = null;
		style = null;
		skin = null;
		
		arg0Name = null;
		arg0Property = null;
		arg1Name = null;
		arg1Property = null;
		
		layoutId = null;
	}
	/**
	 * Sets the arg0.
	 * @param arg0 The arg0 to set
	 */
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}

	/**
	 * Sets the arg1.
	 * @param arg1 The arg1 to set
	 */
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	/**
	 * Sets the arg2.
	 * @param arg2 The arg2 to set
	 */
	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	/**
	 * Sets the arg3.
	 * @param arg3 The arg3 to set
	 */
	public void setArg3(String arg3) {
		this.arg3 = arg3;
	}

	/**
	 * Sets the arg4.
	 * @param arg4 The arg4 to set
	 */
	public void setArg4(String arg4) {
		this.arg4 = arg4;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public void setStyle(String in_style) {
		this.style = in_style;
	}
	public String getStyle() {
		return style;
	}
	public String getBundle() {
		return bundle;
	}

//	public String getLocale() {
//		return localeKey;
//	}

	/**
	 * Gets the arg0.
	 * @return Returns a String
	 */
	public String getArg0() {
		return arg0;
	}

	/**
	 * Gets the arg1.
	 * @return Returns a String
	 */
	public String getArg1() {
		return arg1;
	}

	/**
	 * Gets the arg2.
	 * @return Returns a String
	 */
	public String getArg2() {
		return arg2;
	}

	/**
	 * Gets the arg3.
	 * @return Returns a String
	 */
	public String getArg3() {
		return arg3;
	}

	/**
	 * Gets the arg4.
	 * @return Returns a String
	 */
	public String getArg4() {
		return arg4;
	}
	/**
	 * Returns the arg0Name.
	 * @return String
	 */
	public String getArg0Name() {
		return arg0Name;
	}

	/**
	 * Returns the arg0Property.
	 * @return String
	 */
	public String getArg0Property() {
		return arg0Property;
	}

	/**
	 * Returns the arg1Name.
	 * @return String
	 */
	public String getArg1Name() {
		return arg1Name;
	}

	/**
	 * Returns the arg1Property.
	 * @return String
	 */
	public String getArg1Property() {
		return arg1Property;
	}

	/**
	 * Sets the arg0Name.
	 * @param arg0Name The arg0Name to set
	 */
	public void setArg0Name(String arg0Name) {
		this.arg0Name = arg0Name;
	}

	/**
	 * Sets the arg0Property.
	 * @param arg0Property The arg0Property to set
	 */
	public void setArg0Property(String arg0Property) {
		this.arg0Property = arg0Property;
	}

	/**
	 * Sets the arg1Name.
	 * @param arg1Name The arg1Name to set
	 */
	public void setArg1Name(String arg1Name) {
		this.arg1Name = arg1Name;
	}

	/**
	 * Sets the arg1Property.
	 * @param arg1Property The arg1Property to set
	 */
	public void setArg1Property(String arg1Property) {
		this.arg1Property = arg1Property;
	}

	/**
	 * Returns the layoutId.
	 * @return String
	 */
	public String getLayoutId() {
		return layoutId;
	}

	/**
	 * Sets the layoutId.
	 * @param layoutId The layoutId to set
	 */
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

}