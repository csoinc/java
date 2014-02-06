package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * Base class for all Struts-Layout tags.
 * 
 * This class allows the tag find their parent even in the case of dynamic includes.
 */
public interface LayoutTag extends Tag {
  public abstract PageContext getPageContext();   
}
