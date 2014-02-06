// Copyright 2005 Improve SA
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Specify which input tags are used by the expert tag.
 * @author Romain Maton
 */
public class ExpertFieldTag extends TagSupport {
	
	protected String layoutId;
	
	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}
	
	public String getLayoutId() {
		return layoutId;
	}
	
	public int doStartTag() throws JspException {
		ExpertTag tag = (ExpertTag) findAncestorWithClass(this, ExpertTag.class);
		if (tag==null) {
			throw new JspException("<layout:expertField> should be nested in a <layout:expert> tag");
		}
		tag.doReferencer(this.layoutId);
		return SKIP_BODY;
	}
}
