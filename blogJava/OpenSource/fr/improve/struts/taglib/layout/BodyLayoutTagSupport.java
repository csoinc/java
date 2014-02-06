package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * Ancestor class for all body layout tag.
 * This class automatically register the tag in a tag stack to avoid the problem of findinf parent tag with dynamic include.
 * It also provides method to initialize dynamically computed values.
 * 
 * @author JN Ribette
 */
public class BodyLayoutTagSupport extends BodyTagSupport implements LayoutTag {
	
	/**
	 * Return the tag pageContext.
	 */
	public final PageContext getPageContext() {
		return pageContext;
	}
  
	/**
	 * Start the tag :
	 * <ul>
	 * <li>register the tag (call registerTag)</li>
	 * <li>initialize dynamic values (call initDynamicValues). This is the place to compute EL values.</li>
	 * <li>handle tag start (call doStartLayoutTag)</li>
	 * </ul> 
	 */
	public final int doStartTag() throws JspException {
		registerTag();
		initDynamicValues();
		return doStartLayoutTag();
	}   
  
	/**
	 * end the tag:
	 * <ul>
	 * <li>handle tag end (call doEndLayoutTag)</li>
	 * <li>reset initial values (call reset). Due to tag pooling, values fixed by the jsp that have been changed by the code 
	 * 	must be restored to their initial values.</li>
	 * <li>deregister the tag (call desreigsterTag)</li>
	 * </ul>
	 */
  public final int doEndTag() throws JspException {
    try {
      return doEndLayoutTag();
    } finally {
      reset();
      deregisterTag();
    }
  }
  
  public int doStartLayoutTag() throws JspException {
    return super.doStartTag();
  }
  public int doEndLayoutTag() throws JspException {
    return super.doEndTag();
  }
  
  protected void initDynamicValues() throws JspException {
    // Do nothing.
  }
  
  protected void reset() {
    // do nothing, to be override.
  }
  
  /**
   * Register this tag in the layout tag stack.
   * Even if this is a bad idea, overriding this method allow to insert a tag betwwen this tag and its parent. 
   */
  protected void registerTag() throws JspException {
  	ParentFinder.registerTag(pageContext, this);
  }
  
  /**
   * Deregister this tag in the layout tag stack.
   * Even if this is a bad idea, overriding this method allow to insert a tag betwwen this tag and its parent.
   */
  protected void deregisterTag() throws JspException {
  	ParentFinder.deregisterTag(pageContext);
  }

}
