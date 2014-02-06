package fr.improve.struts.taglib.layout.el;

import javax.servlet.jsp.PageContext;

/**
 * @author jer80876
 */
public class TextBlock implements Block {
	private String text;
	
	public TextBlock(String in_string) {
		text = in_string;	
	}

	/**
	 * @see com.beaufouripsen.seas.presentation.taglib.el.Block#evaluate(PageContext)
	 */
	public String evaluate(PageContext in_pg) {
		return text;
	}
	
	public String toString() {
		return new StringBuffer("[T(").append(text).append(")]").toString();
	}

}
