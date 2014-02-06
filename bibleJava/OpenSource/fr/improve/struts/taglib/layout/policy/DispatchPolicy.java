package fr.improve.struts.taglib.layout.policy;

import java.lang.reflect.Method;

import javax.servlet.jsp.PageContext;

/**
 * Implementation of AbstractPolicy that will dispatch the create/edit/inspect call to a policy method.
 * The policy method must have the following signature:
 * public short policy(String name, String property, PageContext pageContext);
 * @author: JeanNoël Ribette
 */
public abstract class DispatchPolicy extends AbstractPolicy {

	public final short getAuthorizedDisplayMode(
		String in_policy,
		String in_name,
		String in_property,
		PageContext in_pageContext) {
		try {
			Class[] lc_parameters = new Class[3];
			lc_parameters[0] = String.class;
			lc_parameters[1] = String.class;
			lc_parameters[2] = PageContext.class;
			Method lc_method = getClass().getDeclaredMethod(in_policy, lc_parameters);
			Object[] l_args = { in_name, in_property, in_pageContext };
			Object lc_result = lc_method.invoke(this, l_args);
			return ((Short) lc_result).shortValue();
		} catch (NoSuchMethodException nsme) {
			throw new RuntimeException("Policy " + in_policy + " is not supported");
		} catch (IllegalAccessException iae) {
			throw new RuntimeException("Accessing policy " + in_policy + " is not allowed");
		} catch (java.lang.reflect.InvocationTargetException ite) {
			throw new RuntimeException(
				"Apply of policy " + in_policy + " failed: " + ite.getTargetException());
		}
	}	
}