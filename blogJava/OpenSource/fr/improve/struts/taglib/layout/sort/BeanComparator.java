package fr.improve.struts.taglib.layout.sort;

import java.util.Comparator;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
/**
 * Generic bean comparator.
 * 
 * Creation date: (27/10/2001 15:49:40)
 * 
 * @author: Jean-Noël Ribette
 */
public class BeanComparator implements Comparator {
	/**
	 * The property to use to compare the beans.
	 */
	protected String property;
	
	/**
	 * The rules to use to compare String properties.
	 */
	protected SortRules stringSortingRules;
	
	
	/**
	 * Constructor
	 * @param in_property 	The property to use to compare objects.
	 * @param in_rules		The string sorting rules to use.
	 */	
	public BeanComparator(String in_property, SortRules in_rules) {
		property = in_property;
		stringSortingRules = in_rules;
	}
	/**
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second.<p>
	 *
	 * The implementor must ensure that <tt>sgn(compare(x, y)) ==
	 * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>compare(x, y)</tt> must throw an exception if and only
	 * if <tt>compare(y, x)</tt> throws an exception.)<p>
	 *
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
	 * <tt>compare(x, z)&gt;0</tt>.<p>
	 *
	 * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
	 * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
	 * <tt>z</tt>.<p>
	 *
	 * It is generally the case, but <i>not</i> strictly required that 
	 * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
	 * any comparator that violates this condition should clearly indicate
	 * this fact.  The recommended language is "Note: this comparator
	 * imposes orderings that are inconsistent with equals."
	 * 
	 * @return a negative integer, zero, or a positive integer as the
	 * 	       first argument is less than, equal to, or greater than the
	 *	       second. 
	 * @throws ClassCastException if the arguments' types prevent them from
	 * 	       being compared by this Comparator.
	 */
public int compare(java.lang.Object o1, java.lang.Object o2) {
	// Don't try to compare null values.
	if (o1==null || o2==null) {
		return compareNull(o1, o2);
	}

	try {
		// Get the values to compare from the beans
		Object lc_object1 = LayoutUtils.getProperty(o1, property);
		Object lc_object2 = LayoutUtils.getProperty(o2, property);
		
		// Don't try to compare null values.
		if (lc_object1==null || lc_object2==null) {
			return compareNull(lc_object1, lc_object2);
		}
		
		// Values are Strings.
		if (lc_object1 instanceof String && lc_object2 instanceof String) {
			return compareString((String)lc_object1, (String)lc_object2);			
		}
		
		// Values are comparable.
		if (lc_object1 instanceof Comparable && lc_object2 instanceof Comparable) {
			return compareComparable((Comparable)lc_object1, (Comparable)lc_object2);			
		}
		
		// Value are boolean.
		if (lc_object1 instanceof Boolean && lc_object2 instanceof Boolean) {
			return compareBoolean((Boolean)lc_object1, (Boolean)lc_object2);
		}
		
		// Don't nowhow to compare the values
		throw new ClassCastException("Cannot compare objects of class " + (lc_object1!=null ? lc_object1.getClass().getName() : "null") + " and " + (lc_object2!=null ? lc_object2.getClass().getName() : "null"));
	} catch (javax.servlet.jsp.JspException e) {
		throw new ClassCastException();
	}
	
}

	/**
	 * Compare boolean values.
	 */
	public int compareBoolean(Boolean in_bool1, Boolean in_bool2) {
		boolean lc_b1 = in_bool1.booleanValue();
		boolean lc_b2 = in_bool2.booleanValue();
		
		if (lc_b1 && lc_b2 || !lc_b1 && !lc_b2) {
			return 0;
		}
		
		if (!lc_b1) {
			return -1;
		}
		
		if (!lc_b2) {
			return 1;
		}
		
		// Should not happen.
		return 0;
	}
	
	/**
	 * Compare string values
	 */
	public int compareString(String lc_value1, String lc_value2) {
		if (stringSortingRules==null) {
			return lc_value1.compareTo(lc_value2);
		} else {
			return stringSortingRules.getRules().compare(lc_value1, lc_value2);
		}
	}

	/**
	 * Compare comparable values.
	 */
	public int compareComparable(Comparable lc_value1, Comparable lc_value2) {
		return lc_value1.compareTo(lc_value2);
	}
	
	/**
	 * Compare null values.
	 */
	public int compareNull(Object o1, Object o2) {
		if (o1==null && o2==null) {
			return 0;
		}
		if (o1==null) {
			return -1;
		}
		if (o2==null) {
			return 1;
		}
		// Should not happen
		return 0;
	}

}
