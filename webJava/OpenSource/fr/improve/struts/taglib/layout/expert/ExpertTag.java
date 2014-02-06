//Copyright 2005 Improve SA
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package fr.improve.struts.taglib.layout.expert;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;

import fr.improve.struts.taglib.layout.field.TextareaFieldTag;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Expert tag, allowing to set the value of specified field with a meta langage.
 * 
 * @author: Romain Maton
 */
public class ExpertTag extends TextareaFieldTag {
	
	public static final String LOADED = "fr.improve.struts.taglib.layout.expert.ExpertTag.LOADED";
	
	public static final int DEFAULT_SUGGESTCOUNT = 10;
	public static final int DEFAULT_MINWORDLENGTH = 1;
	
	protected List id = null;
	protected int minWordLength;
	protected int suggestCount;
	
	/**
	 * Get all the id that expertTag have to manage
	 * @param layoutId
	 */
	public void doReferencer(String layoutId) {
		(this.id).add(layoutId);
	}
	
	/**
	 * Write the before value
	 */
	protected boolean doBeforeValue() throws JspException {
		super.doBeforeValue();
		this.id = new ArrayList();
		this.suggestCount = DEFAULT_SUGGESTCOUNT;
		this.minWordLength = DEFAULT_MINWORDLENGTH;
		loadScript();
		return true;
	}
	
	/**
	 * Load the required Javascript code.
	 */
	protected void loadScript() throws JspException {
		if (pageContext.getRequest().getAttribute(LOADED)==null) {
			TagUtils.write(pageContext, "<script src=\"");
			TagUtils.write(pageContext, LayoutUtils.getSkin(pageContext.getSession()).getConfigDirectory(pageContext.getRequest()));
			TagUtils.write(pageContext, "/expert.js\"></script>");
			pageContext.getRequest().setAttribute(LOADED, "");
		}
	}

	/**
	 * End of the tag with init and suggest input hidden
	 */
	public int doEndLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script language=\"JavaScript\">var arrayId = new Array(");
		for(int i=0; i<(this.id).size(); i++) {
			if(i==0)
				buffer.append("'"+id.get(i)+"'");
			else
				buffer.append(",'"+id.get(i)+"'");
		}
		buffer.append(");StrutsLayoutExpert.init(arrayId);</script>");
		buffer.append("<br/><div id=\"expertSuggestSuggestionList\" class=\"suggestionList\"></div>");
		buffer.append("<input type=\"hidden\" id=\"expertSuggestSuggestionList_selectedFieldText" + "\" value=\"0\">");
        buffer.append("<input type=\"hidden\" id=\"expertSuggestSuggestionList_selectedSuggestionIndex" + "\" value=\"-1\">");
        buffer.append("<input type=\"hidden\" id=\"expertSuggestSuggestionList_typedWord" + "\" value=\"\">");
		TagUtils.write(pageContext, buffer.toString());
		return super.doEndLayoutTag();
	}
	
	/**
	 * Copy properties
	 */
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		if(getAccesskey()==null || getAccesskey().length()==0) {
			fieldTag.setAccesskey("E");
		}
		fieldTag.setOnkeydown((onkeydown != null ? onkeydown : "") +
				";StrutsLayoutExpert.computeKeyDown(this, StrutsLayoutExpert.getKey(event.keyCode, event.which));");
		fieldTag.setOnkeyup((onkeyup != null ? onkeyup : "") +
				";StrutsLayoutExpert.expertUpdate(this.value,false);" +
				"StrutsLayoutExpert.computeKeyUp(this, StrutsLayoutExpert.getKey(event.keyCode, event.which), '" + 
				this.suggestCount + "', '" + this.minWordLength + "');");
		fieldTag.setOnkeypress((onkeypress != null ? onkeypress : "") +
				";return StrutsLayoutExpert.computeKeyPress(this, StrutsLayoutExpert.getKey(event.keyCode, event.which));");
	}
}
