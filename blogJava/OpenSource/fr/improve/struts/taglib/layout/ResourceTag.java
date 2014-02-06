/*
 * Created on 19 oct. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Generate an absolute path to a struts-layout resource.
 * 
 * @author JN Ribette
 */
public class ResourceTag extends LayoutTagSupport {
	protected static final int IMG = 1;
	protected static final int CFG = 2;
	protected static final int CSS = 3;
	protected int type;
	protected String name;
	
	public int doStartLayoutTag() throws JspException {
		// Get skin
		Skin lc_skin = LayoutUtils.getSkin(pageContext.getSession());
		StringBuffer lc_buffer = new StringBuffer();
		
		switch (type) {
			case IMG:
				// Get image directory.
				String lc_imgSrc = lc_skin.getImageDirectory(pageContext.getRequest());
				// Generate HTML code.
				lc_buffer.append(lc_imgSrc);
				if (!lc_imgSrc.endsWith("/") && !name.startsWith("/")) {
					lc_buffer.append('/');
				}
				lc_buffer.append(name);
				break;
			case CFG:
				//	Get config directory.
				String lc_cfgDir = lc_skin.getConfigDirectory(pageContext.getRequest());
				// Generate HTML code.
				lc_buffer.append(lc_cfgDir);
				if (!lc_cfgDir.endsWith("/") && !name.startsWith("/")) {
					lc_buffer.append('/');
				}
				lc_buffer.append(name);
				break;
			case CSS:
				// Get css directory.
				String lc_cssDir = lc_skin.getCssDirectory(pageContext.getRequest());
				// Generate HTML code.
				lc_buffer.append(lc_cssDir);
				if (!lc_cssDir.endsWith("/") && !name.startsWith("/")) {
					lc_buffer.append('/');
				}
				lc_buffer.append(name);
				break;
		}
		
		TagUtils.write(pageContext, lc_buffer.toString());
		return SKIP_BODY;
	}
	public void release() {
		super.release();
		type = IMG;
		name = null;
	}
	
	public void setType(String in_type) throws JspException {
		if ("img".equals(in_type)) {
			type = IMG;
		} else if ("cfg".equals(in_type)) {
			type = CFG;
		} else if ("css".equals(in_type)) {
			type = CSS;
		} else {
			throw new JspException("Type " + in_type + " is not supported.");
		}
	}
	
	public void setName(String in_name) {
		name = in_name;
	}
}
