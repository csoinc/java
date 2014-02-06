/*
 * Created on May 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.improve.struts.taglib.layout.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts.taglib.nested.NestedPropertyHelper;

import fr.improve.struts.taglib.layout.skin.Skin;

/**
 * @author jribette
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class NestedHelper {	
	
	public static String getAdjustedProperty(String in_property, PageContext in_context) {
		if (isNestedCompatbilitySet(in_context)) {
			return NestedPropertyHelper.getAdjustedProperty((HttpServletRequest)in_context.getRequest(), in_property);
		} else {
			return in_property;
		}
	}
	
	protected static boolean isNestedCompatbilitySet(PageContext in_context) {
		Skin lc_skin = LayoutUtils.getSkin(in_context.getSession());
		return lc_skin.isNestedCompatible();
	}
}
