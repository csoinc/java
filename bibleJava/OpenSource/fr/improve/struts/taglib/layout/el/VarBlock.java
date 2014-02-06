package fr.improve.struts.taglib.layout.el;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * @author jer80876
 */
public class VarBlock implements Block {
	
	private String name;
	private String property;
	
	public VarBlock(String in_name, String in_property) {
		name = in_name;
		property = in_property;	
	}

	/**
	 * @see com.beaufouripsen.seas.presentation.taglib.el.Block#evaluate(PageContext)
	 */
	public String evaluate(PageContext in_pg)  throws EvaluationException {
		Object lc_bean = null;
		String lc_name = Expression.evaluate(name, in_pg);
		String lc_property = Expression.evaluate(property, in_pg);
			
		if ("this".equals(lc_name)) {
			lc_bean = ParentFinder.getLastTag(in_pg);
		} else {
			lc_bean = in_pg.findAttribute(lc_name);
		}
		
		// Check if there is an array, not efficient.
		if (lc_bean==null) {
			int lc_indexStart = lc_name.indexOf('[');
			int lc_indexEnd = lc_name.indexOf(']');
			if (lc_indexStart!=-1 && lc_indexEnd!=-1 && lc_indexStart < lc_indexEnd) {
				String lc_beanName = lc_name.substring(0, lc_indexStart);
				Iterator lc_it;
				try {
					lc_it = LayoutUtils.getIterator(in_pg, lc_beanName, null);
				} catch (JspException e) {
					throw new EvaluationException("Could not get iterator for bean " + lc_beanName);
				}
				int lc_pos = Integer.parseInt(lc_name.substring(lc_indexStart+1, lc_indexEnd));
				while (lc_it.hasNext() && lc_pos>=0) {
					lc_bean = lc_it.next();
					lc_pos--;
				}
			}
		}
		
		if (lc_bean==null) {
			return "";	
		}
		if (lc_property!=null) {
			Object lc_value = null;
			try {
				lc_value = PropertyUtils.getProperty(lc_bean, lc_property);
			} catch (NoSuchMethodException e) {
				throw new EvaluationException("No method to get the property " + lc_property + " on a " + lc_bean.getClass().getName());
			} catch (InvocationTargetException e) {
				throw new EvaluationException("Getter of property " + lc_property + " failed");
			} catch (IllegalAccessException e) {
				throw new EvaluationException("Illegal access to property " + lc_property);	
			}
			if (lc_value==null) {
				return "";	
			} else {
				return lc_value.toString();	
			}
		}
		return lc_bean.toString();
	}
	
	public String toString() {
		return new StringBuffer("[V(").append(name).append(".").append(property).append(")]").toString();
	}

}
