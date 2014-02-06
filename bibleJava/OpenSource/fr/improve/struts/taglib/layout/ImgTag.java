package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Image tag, allowing to include an image from the skin image directory.
 * @author JN Ribette
 */
public class ImgTag extends org.apache.struts.taglib.html.ImgTag {
	/**
	 * Image location relative to the skin image directory.
	 */
	protected String srcName;
	
	/**
	 * Original styleId value
	 */
	private String jspStyleId;
	
	/**
	 * Original onclick value.
	 */
	private String jspOnclick;
	
	/**
	 * Layout support.
	 */
	protected boolean layout = true;
	
	public int doStartTag() throws JspException {
		initDynamicValues();
		return super.doStartTag();
	}
	
	public int doEndTag() throws JspException {
		int ret = super.doEndTag();
		reset();
		return ret;
	}
	
	public void release() {
		super.release();
		srcName = null;
		layout = true;
	}
	
	protected void reset() {
		setOnclick(jspOnclick);
		setStyleId(jspStyleId);
	}
	
	protected void initDynamicValues() {
		jspOnclick = getOnclick();
		setOnclick(Expression.evaluate(getOnclick(), pageContext));
		jspStyleId = getStyleId();
		setStyleId(Expression.evaluate(getStyleId(), pageContext));
	}
	
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String skinSrc) {
		this.srcName = skinSrc;
	}
	public boolean isLayout() {
		return layout;
	}
	public void setLayout(boolean layout) {
		this.layout = layout;
	}
	protected String src() throws JspException {
		if (srcName!=null) {
			// Get skin image directory.
			Skin lc_skin = LayoutUtils.getSkin(pageContext.getSession());
			String lc_imgSrc = lc_skin.getImageDirectory(pageContext.getRequest());
			
			// Generate image URL code.
			StringBuffer lc_buffer = new StringBuffer(lc_imgSrc);			
			if (!lc_imgSrc.endsWith("/") && !srcName.startsWith("/")) {
				lc_buffer.append('/');
			}			
			lc_buffer.append(srcName);
			return lc_buffer.toString();
		} else {
			return super.src();
		}
	}
}
