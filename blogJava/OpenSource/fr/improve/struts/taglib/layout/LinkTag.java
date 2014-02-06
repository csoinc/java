package fr.improve.struts.taglib.layout;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.logic.IterateTag;
import org.apache.struts.util.MessageResources;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.policy.AbstractPolicy;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Generate a URL-encoded hyperlink to the specified URI.
 * 
 * Modified for struts-layout to call the javascript code checking if there are changes in the form values.
 *
 * @author Craig R. McClanahan, JN Ribette, F Bellingard
 */

public class LinkTag extends org.apache.struts.taglib.html.BaseHandlerTag implements LayoutTag, LayoutEventListener {


    // ----------------------------------------------------- Instance Variables


    /**
     * The body content of this tag (if any).
     */
    protected String text = null;


    // ------------------------------------------------------------- Properties


    /**
     * The anchor to be added to the end of the generated hyperlink.
     */
    protected String anchor = null;

    public String getAnchor() {
        return (this.anchor);
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }


    /**
     * The logical forward name from which to retrieve the hyperlink URI.
     */
    protected String forward = null;

    public String getForward() {
        return (this.forward);
    }

    public void setForward(String forward) {
        this.forward = forward;
    }


    /**
     * The hyperlink URI.
     */
    protected String href = null;
    protected String jspHref;

    public String getHref() {
        return (this.href);
    }

    public void setHref(String href) {
        this.href = href;
    }


    /**
     * The link name for named links.
     */
    protected String linkName = null;

    public String getLinkName() {
        return (this.linkName);
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }


    /**
     * The message resources for this package.
     */
    protected static MessageResources messages =
     MessageResources.getMessageResources(Constants.Package + ".LocalStrings");


    /**
     * The JSP bean name for query parameters.
     */
    protected String name = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * The context-relative page URL (beginning with a slash) to which
     * this hyperlink will be rendered.
     */
    protected String page = null;
    protected String action;
    protected String module;

    public String getPage() {
        return (this.page);
    }

    public void setPage(String page) {
        this.page = page;
    }


    /**
     * The single-parameter request parameter name to generate.
     */
    protected String paramId = null;

    public String getParamId() {
        return (this.paramId);
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }


    /**
     * The single-parameter JSP bean name.
     */
    protected String paramName = null;

    public String getParamName() {
        return (this.paramName);
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }


    /**
     * The single-parameter JSP bean property.
     */
    protected String paramProperty = null;

    public String getParamProperty() {
        return (this.paramProperty);
    }

    public void setParamProperty(String paramProperty) {
        this.paramProperty = paramProperty;
    }


    /**
     * The single-parameter JSP bean scope.
     */
    protected String paramScope = null;

    public String getParamScope() {
        return (this.paramScope);
    }

    public void setParamScope(String paramScope) {
        this.paramScope = paramScope;
    }


    /**
     * The JSP bean property name for query parameters.
     */
    protected String property = null;

    public String getProperty() {
        return (this.property);
    }

    public void setProperty(String property) {
        this.property = property;
    }


    /**
     * The scope of the bean specified by the name property, if any.
     */
    protected String scope = null;

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    /**
     * The window target.
     */
    protected String target = null;

    public String getTarget() {
        return (this.target);
    }

    public void setTarget(String target) {
        this.target = target;
    }


    /**
     * Include transaction token (if any) in the hyperlink?
     */
    protected boolean transaction = false;

    public boolean getTransaction() {
        return (this.transaction);
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }

    /**
     * Name of parameter to generate to hold index number
     */
    protected String indexId = null;

    public String getIndexId() {
       return (this.indexId);
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
    
    protected boolean layout = true;
    
    public void setLayout(boolean in_layout) {
    	layout = in_layout;
    }
    public boolean isLayout() {
    	return layout;
    }
    
    // --------------------------------------------------------- Public Methods
    
    public final PageContext getPageContext() {
      return pageContext;
    }
    
    public final int doStartTag() throws JspException {
      ParentFinder.registerTag(pageContext, this);
      initDynamicValues();
      return doStartLayoutTag();
    }   
    
    public final int doEndTag() throws JspException {
      try {
        return doEndLayoutTag();
      } finally {
        reset();
        ParentFinder.deregisterTag(pageContext);
      }
    }

    /**
     * Init dynamic values.
     */
    protected void initDynamicValues() {
    	jspHref = href;
    	href = Expression.evaluate(jspHref, pageContext);
    }

    /**
     * Render the beginning of the hyperlink.
     * Indexed property since 1.1
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartLayoutTag() throws JspException {
    	
        // if the action is about to be displayed, check the authorization first
        if (policy != null) {
            Skin lc_currentSkin = LayoutUtils.getSkin(pageContext.getSession());
            AbstractPolicy lc_policy = lc_currentSkin.getPolicy();
            String lc_action = null;
            if (getHref() != null) {
                lc_action = getHref();
            } else if (getPage() != null) {
                lc_action = getPage();            	
            } else if (getForward() != null) {
                lc_action = getForward();                
            } else {
            	throw new JspException("You must specify exactly one of the following attributes for the Link tag: href, page or forward.");
            }
            switch (lc_policy.getAuthorizedDisplayMode(getPolicy(), lc_action, getProperty(), pageContext)) {
                case AbstractPolicy.MODE_EDIT:
                    break;
                case AbstractPolicy.MODE_NODISPLAY:
                    display = false;
                    break;
                case AbstractPolicy.MODE_INSPECT:
                	showLink = false;
                break;
                default:
                    throw new IllegalStateException(lc_policy.getClass().getName() + " returns an illegal value");                
            }           
        }
			
        // do nothing if the action is not displayed in this mode.
        if (!display)
            return SKIP_BODY;
			
        if (isLayout()) {
        	StringBuffer lc_buffer = new StringBuffer();
        	beginLinkLayout(lc_buffer);
        	new StartLayoutEvent(this, lc_buffer.toString()).send();
        }

        // Special case for name anchors
        if (linkName != null) {
            StringBuffer results = new StringBuffer("<a name=\"");
            results.append(linkName);
            results.append("\">");
            TagUtils.write(pageContext, results.toString());
            return (EVAL_BODY_TAG);
        }

        // Generate the hyperlink URL
        Map params = LayoutUtils.computeParameters
            (pageContext, paramId, paramName, paramProperty, paramScope,
             name, property, scope, transaction);

        // if "indexed=true", add "index=x" parameter to query string
        // since 1.1
        if( indexId!=null ) {
           // look for outer iterate tag
           IterateTag iterateTag = (IterateTag) findAncestorWithClass(this, IterateTag.class);
           if (iterateTag == null) {
              // this tag should only be nested in iteratetag, if it's not, throw exception
              JspException e = new JspException(messages.getMessage("indexed.noEnclosingIterate"));
              TagUtils.saveException(pageContext, e);
              throw e;
           }

           //calculate index, and add as a parameter
           if (params == null) {
               params = new HashMap();             //create new HashMap if no other params
           }
            params.put(indexId, Integer.toString(iterateTag.getIndex()));
        }

        String url = null;
        url = LayoutUtils.computeURL(pageContext, forward, href,
                                          page, action, module, params, anchor, false, target);

        // Generate the opening anchor element
        StringBuffer results = new StringBuffer("<a ");
        
        if (showLink) {
	        results.append("href=\"");
	        results.append(url);
	        results.append("\"");
	        if (target != null) {
	            results.append(" target=\"");
	            results.append(target);
	            results.append("\"");
	        }
        }
        results.append(prepareStyles());
        results.append(prepareEventHandlers());
        results.append(">");

        // Print this element to our output writer
        TagUtils.write(pageContext, results.toString());

        // Evaluate the body of this tag
        this.text = null;
        return (EVAL_BODY_TAG);

    }



    /**
     * Save the associated label from the body content.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doAfterBody() throws JspException {

        if (bodyContent != null) {
            String value = bodyContent.getString().trim();
            if (value.length() > 0)
                text = value;
        }
        return (SKIP_BODY);

    }


    /**
     * Render the end of the hyperlink.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doEndLayoutTag() throws JspException {
        // do nothing if the tag is no displayed
        if (!display) {
            display = true;
            return EVAL_PAGE;
        }

        // Prepare the textual content and ending element of this hyperlink
        StringBuffer results = new StringBuffer();
        if (text != null)
            results.append(text);
        results.append("</a>");
		

        // Render the remainder to the output stream
        TagUtils.write(pageContext, results.toString());
        
        if (isLayout()) {
        	results.setLength(0);
        	endLinkLayout(results);
        	new EndLayoutEvent(this, results.toString()).send();
        }
        
        // Evaluate the remainder of this page
        return (EVAL_PAGE);

    }
    
    protected void reset() {
    	href = jspHref;
    	jspHref = null;
    	
    	showLink = true;
    }

	/**
	 * Prepare to display the link.
	 */
	protected void beginLinkLayout(StringBuffer in_buffer) throws JspException {
		in_buffer.append("<td>&nbsp;</td><td");
//			if (valign!=null) {
//				in_buffer.append(" valign=\"");
//				in_buffer.append(valign);
//				in_buffer.append("\"");	
//			}
		in_buffer.append(">");
	}

	/**
	 * End the display of the action.
	 */
	protected void endLinkLayout(StringBuffer in_buffer) {
		in_buffer.append("</td>");
	}

    /**
     * Release any acquired resources.
     */
    public void release() {

        super.release();
        anchor = null;
        forward = null;
        href = null;
        action = null;
        module = null;
        linkName = null;
        name = null;
        page = null;
        paramId = null;
        paramName = null;
        paramProperty = null;
        paramScope = null;
        property = null;
        scope = null;
        target = null;
        text = null;
        transaction = false;
        policy = null;
        display = true;
        layout = true;
    }

    
    protected boolean display = true;
    protected boolean showLink = true;
    protected String    policy  = null;

	/**
	 * Set in wich form modes the action should be displayed or not.
	 * The format form in_mode is X,Y,Z where allowed values are D (Displayed) and N (not displayed) in the same order as the input field tags.
	 */
	public void setMode(String in_mode) {
		if (in_mode == null || in_mode.length() != 5) {
			throw new IllegalArgumentException(
				"The specified mode" + in_mode + " is invalid");
		}		
		int lc_formMode = LayoutUtils.getSkin(pageContext.getSession()).getFormUtils().getFormDisplayMode(pageContext);
		char lc_displayMode;
		switch (lc_formMode) {
			case FormUtilsInterface.CREATE_MODE :
				lc_displayMode = in_mode.charAt(0);
				break;
			case FormUtilsInterface.EDIT_MODE :
				lc_displayMode = in_mode.charAt(2);
				break;
			case FormUtilsInterface.INSPECT_MODE :
				lc_displayMode = in_mode.charAt(4);
				break;
			default :
				lc_displayMode = 'D';
		}
		display = lc_displayMode == 'D' || lc_displayMode == 'd';
	}
    
	/**
	 * @return Returns the policy.
	 */
	public String getPolicy()
	{
		return policy;
	}

	/**
	 * @param policy The policy to set.
	 */
	public void setPolicy(String policy)
	{
		this.policy = policy;
	}
    
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		return Boolean.FALSE;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}