package fr.improve.struts.taglib.layout.util;
import java.util.Stack;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.improve.struts.taglib.layout.LayoutTag;

/**
 * API to use instead of findAncestorWithClass which only uses the pageContext,
 * so get lost in case of dynamic includes.
 */
public class ParentFinder {
  public static final String TAGS_KEY = "fr.improve.struts.taglib.layout.util.ParentFinder";
  private static Log LOG = LogFactory.getLog(ParentFinder.class);
  
  /**
   * Find an tag being an instance of the specified class and an ancestor of the specified tag. 
   */
  public static Tag findLayoutTag(LayoutTag tag, Class clazz) {
  	// Get the tag stack.
    Stack lc_stack = getTagsStack(tag.getPageContext().getRequest());
    Tag lc_result = null;
    
    // Get the index of the specified tag. 
    // The specified tag is usually the last item in the stack.  
    int i = lc_stack.lastIndexOf(tag) - 1;
    
    // Should not happen.
    // The tag was not in the stack !
    if (i==-2) {
    	LOG.error("The requested tag" + tag + " was not found in the following stack :\n "+ lc_stack);
    }
    
    // Look for a tag
    while (lc_result==null && i>=0) {
      Tag lc_someTag = (Tag) lc_stack.get(i);
      if (clazz.isInstance(lc_someTag)) {
        lc_result = lc_someTag;
      }
      i--;
    }
    
    return lc_result;
  }
  
  public static final Tag getLastTag(PageContext in_context) {
  	// Get the tag stack.
    Stack lc_stack = getTagsStack(in_context.getRequest());
    
    return (Tag) lc_stack.lastElement();
  }
  
  public static void registerTag(PageContext context, Tag tag) {
  	LOG.debug("Registering tag " + tag);
    Stack lc_stack = getTagsStack(context.getRequest());
    lc_stack.push(tag);    
  }   
  
  public static void deregisterTag(PageContext context) {
    Stack lc_stack = getTagsStack(context.getRequest());
    Object o = lc_stack.pop();
    LOG.debug("Deregistering tag " + o);
    
  }
  
  private static Stack getTagsStack(ServletRequest request) {
    Stack lc_stack = (Stack) request.getAttribute(TAGS_KEY);
    if (lc_stack==null) {
      lc_stack = new Stack();
      request.setAttribute(TAGS_KEY, lc_stack);
    }
    return lc_stack;
  }
}
