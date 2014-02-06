package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.TextTag;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * A text field tag with a suggestion list
 * @author: Francois Goldgewicht
 */
public class SuggestFieldTag extends AbstractFieldTag {
	
	static class StrutsSuggestFieldTag extends TextTag {
		
		public void prepareTextEvents(StringBuffer in_buffer) {
			super.prepareTextEvents(in_buffer);
			in_buffer.append(" autocomplete=\"off\"");
		}
		
	}
	
	private TextTag textFieldTag = new StrutsSuggestFieldTag();
	
	public static final String LOADED = "fr.improve.struts.taglib.layout.field.SuggestFieldTag.LOADED";

	public static final int DEFAULT_SUGGESTCOUNT = 10;
	public static final String DEFAULT_SUGGESTENCODING = "ISO-8859-1";
	public static final int DEFAULT_MINWORDLENGTH = 1;
	public static final int DEFAULT_TIMEOUT = 0;
	protected String suggestAction;
	protected int suggestCount;
	protected String suggestEncoding;
	protected int minWordLength;
	protected int timeout;
	protected boolean all = false;
	
	protected String jspOnkeyup;
	protected String jspOnkeydown;
	protected String jspOnkeypress;
	protected String jspOnFocus;
	

	protected void initDynamicValues() {		
		super.initDynamicValues();
		
		jspOnkeyup = onkeyup;
		jspOnkeydown = onkeydown;
		jspOnkeypress = onkeypress;
		jspOnFocus = onfocus;
		
		String url = null;

		try {
			url = LayoutUtils.computeURL(pageContext, null, null, null, this.getSuggestAction(), null, null, null, false, "suggest");
		}
		catch (JspException e) {
			TagUtils.saveException(pageContext, e);
			throw new RuntimeException(e.getMessage());
		}
		
		if (suggestCount == 0) 
			suggestCount = DEFAULT_SUGGESTCOUNT;
		
		if (suggestEncoding == null) 
			suggestEncoding = DEFAULT_SUGGESTENCODING;
		
		if (minWordLength == 0) 
			minWordLength = DEFAULT_MINWORDLENGTH;

		if (timeout == 0) 
			timeout = DEFAULT_TIMEOUT;

		if (all) {
			minWordLength = 0;
			onfocus=(onfocus != null ? onfocus : "") +
			";return computeFocus('" + this.getStyleId() + 
			"', '" + url + "', " + suggestCount + ", '" + suggestEncoding + 
			"', " + minWordLength + ", " + timeout + ", '" + all + "' )";
			/*onblur=(onblur != null ? onblur : "") +
			";return computeBlur('" + this.getStyleId() + "', '" + all + "' )";*/
		}
		
		onkeyup=(onkeyup != null ? onkeyup : "") +
		";return computeKeyUp('" + this.getStyleId() + 
		"', getKey(event.keyCode, event.which), '" + 
		url + "', " + suggestCount + ", '" + suggestEncoding + 
		"', " + minWordLength + ", " + timeout + ", '" + all + "' )";
	
		onkeydown=(onkeydown != null ? onkeydown : "") +
		";return computeKeyDown('" + this.getStyleId() + 
		"', getKey(event.keyCode, event.which) , '" + 
		url + "' )";
		
		onkeypress=(onkeypress != null ? onkeypress : "") +
		";return computeKeyPress('" + this.getStyleId() + 
		"', getKey(event.keyCode, event.which) )";
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		textFieldTag.setCols(getCols());
		textFieldTag.setMaxlength(getMaxlength());
		textFieldTag.setProperty(getProperty());
		textFieldTag.setRows(getRows());
		textFieldTag.setValue(getValue());
		textFieldTag.setAccept(getAccept());
		textFieldTag.setName(getName());
	}
	
	protected boolean doBeforeValue() throws javax.servlet.jsp.JspException {
		fieldTag = textFieldTag;
		
		loadScript();
		
		return true;
	}
	
	protected void doAfterValue() throws JspException {
		
        StringBuffer buffer = new StringBuffer();
        
    	// Suggestion list Div
        buffer.append("<div id=\"" + this.getStyleId() + "SuggestionList\" class=\"suggestionList\"></div>");
    	
    	// Hidden fields
        buffer.append("<input type=\"hidden\" id=\"" + this.getStyleId() + "SuggestionList_selectedFieldText" + "\" value=\"0\">");
        buffer.append("<input type=\"hidden\" id=\"" + this.getStyleId() + "SuggestionList_selectedSuggestionIndex" + "\" value=\"-1\">");
        buffer.append("<input type=\"hidden\" id=\"" + this.getStyleId() + "SuggestionList_typedWord" + "\" value=\"\">");
    	
        TagUtils.write(pageContext, buffer.toString());
	}
	
	/**
	 * Load the required Javascript code.
	 */
	protected void loadScript() throws JspException {
		
		if (pageContext.getRequest().getAttribute(LOADED)==null) {
			TagUtils.write(pageContext, "<script src=\"");
			TagUtils.write(pageContext, LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
			TagUtils.write(pageContext, "/suggest.js\"></script>");
			pageContext.getRequest().setAttribute(LOADED, "");
		}
		
	}
		
	/* (non-Javadoc)
	 * @see fr.improve.struts.taglib.layout.LayoutTagSupport#reset()
	 */
	protected void reset() {
		super.reset();
		onkeyup = jspOnkeyup;
		onkeydown = jspOnkeydown;
		onkeypress = jspOnkeypress;
		onfocus = jspOnFocus;
	}
	
	public void release(){
		super.release();
		all = false;
	}
	
	/**
	 * @return Returns the suggestAction.
	 */
	public String getSuggestAction() {
		return suggestAction;
	}
	
	/**
	 * @param suggestAction The suggestAction to set.
	 */
	public void setSuggestAction(String suggestAction) {
		this.suggestAction = suggestAction;
	}

	/**
	 * @return Returns the suggestCount.
	 */
	public int getSuggestCount() {
		return suggestCount;
	}
	/**
	 * @param suggestCount The suggestCount to set.
	 */
	public void setSuggestCount(int suggestCount) {
		this.suggestCount = suggestCount;
	}
	/**
	 * @return Returns the suggestEncoding.
	 */
	public String getSuggestEncoding() {
		return suggestEncoding;
	}
	/**
	 * @param suggestEncoding The suggestEncoding to set.
	 */
	public void setSuggestEncoding(String suggestEncoding) {
		this.suggestEncoding = suggestEncoding;
	}
	/**
	 * @return Returns the minWordLength.
	 */
	public int getMinWordLength() {
		return minWordLength;
	}
	/**
	 * @param minWordLength The minWordLength to set.
	 */
	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}
	/**
	 * @return Returns the timeout.
	 */
	public int getTimeout() {
		return timeout;
	}
	/**
	 * @param timeout The timeout to set.
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	/**
	 * @param all Returns the all.
	 */
	public boolean isAll() {
		return all;
}
	/**
	 * @param all The all to set.
	 */
	public void setAll(boolean all) {
		this.all = all;
	}
}
