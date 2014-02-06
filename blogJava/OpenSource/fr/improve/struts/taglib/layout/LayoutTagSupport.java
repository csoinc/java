package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * Abstract LayoutTag implementation.
 * This class helps :
 * <ul><li>to develop custom tags that can find their parents even when the jsp is dynamically included in another jsp,</li>
 * <li>to support dynamically computed value without breaking tag pooling</li></ul> 
 */
public abstract class LayoutTagSupport extends TagSupport implements LayoutTag {
	/**
	 * Return the tag pageContext.
	 */
  public final PageContext getPageContext() {
    return pageContext;
  }
  
  /**
   * Final implemtation of doStartTag.
   * This method ensures that :
   * <ul>
   * <li>the tag is registered in the struts-layout tag stack</li>
   * <li>the dynamic values are computed</li>
   * </ul>
   */
  public final int doStartTag() throws JspException {
    ParentFinder.registerTag(pageContext, this);
    initDynamicValues();
    return doStartLayoutTag();
  }   
  
  /**
   * Final implementation of doEndTag.
   * This method ensures that:
   * <ul>
   * <li>the tag attribute values are reset to their original values.</li>
   * <li>the tag is deregistered from the struts-layout tag stack.</li>
   * </ul>
   */
  public final int doEndTag() throws JspException {
    try {
      return doEndLayoutTag();
    } finally {
      reset();
      ParentFinder.deregisterTag(pageContext);
    }
  }
  
  public int doStartLayoutTag() throws JspException {
    return super.doStartTag();
  }
  public int doEndLayoutTag() throws JspException {
    return super.doEndTag();
  }
  
  /**
   * Init dynamuc values. This is the place to initialize EL-enabled attributes.
   */
  protected void initDynamicValues() {
    // Do nothing.
  }
  
  /**
   * Reset dynamic values. This is the place to reset EL-enabled attributes to their jsp values.  
   */
  protected void reset() {
    // DO NOTHING, to be override.
  }
}
