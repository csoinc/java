package fr.improve.struts.taglib.layout.el;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Version très simplifiée des EL de la JSTL.
 * 
 * @author jer80876
 */
public class Expression {
	
	
	
	/**
	 * Cache des expressions déjà utilisés.
	 */
	private static WeakHashMap cache = new WeakHashMap();
	
	/**
	 * Méthode public évaluant une expression.
	 */
	public static String evaluate(String in_string, PageContext in_pg) throws EvaluationException {
		if (in_string==null) {
			return null;	
		}
		Skin skin = LayoutUtils.getSkin(in_pg.getSession());
		String elDelim = skin.getELCharacter() + "{";
		if (in_string.indexOf(elDelim)==-1) {
			return in_string;
		}
		
		// Ok, on a affaire à une expression.
		Expression lc_expr = (Expression) cache.get(in_string);
		if (lc_expr==null) {
			lc_expr = new Expression(in_string, elDelim);
			cache.put(in_string, lc_expr);
		}
		
		return lc_expr.evalueExpression(in_pg);
	}
	
	/**
	 * Blocs de l'expression.
	 */
	private List blocks = new ArrayList();
	
	/**
	 * Parse une expression.
	 */
	private Expression(String in_string, String in_delim) throws EvaluationException {
		int lc_beginIndex = -1;
		int lc_endIndex = -1;
		String lc_string = in_string;
		lc_beginIndex = lc_string.indexOf(in_delim);
		while (lc_beginIndex!=-1) {			
			// On cherche l'emplacement d'une variable.
			lc_endIndex = lc_string.indexOf('}', lc_beginIndex);
			if (lc_endIndex==-1) {
				throw new EvaluationException("Invalid expression " + in_string);	
			}									
			
			// On créé le bloc texte avant la variable.
			if (lc_beginIndex>0) {
				blocks.add(new TextBlock(lc_string.substring(0, lc_beginIndex)));
			}
			
			// Check for nested variable (1 niveau)
			int lc_nestedVar = lc_string.indexOf(in_delim, lc_beginIndex+2);
			if (lc_nestedVar>lc_beginIndex && lc_nestedVar<lc_endIndex) {
				// Nested variable, fix end location.
				lc_endIndex = lc_string.indexOf('}', lc_endIndex+1);
			}			
			
			// On créé le bloc variable.
			String lc_variable = lc_string.substring(lc_beginIndex+2, lc_endIndex);
			int lc_delim = lc_variable.indexOf('.');
			if (lc_delim==-1) {
				blocks.add(parseBlock(lc_variable, null));
			} else {
				blocks.add(parseBlock(lc_variable.substring(0,lc_delim), lc_variable.substring(lc_delim+1)));
			}
			
			
			// Et on continue.
			lc_string = lc_string.substring(lc_endIndex+1);
			lc_beginIndex = lc_string.indexOf(in_delim);
			lc_endIndex = -1;
			
						
		}
		
		// Plus (ou pas de variable)
		if (lc_string!=null) {
			blocks.add(new TextBlock(lc_string));
		}
	}
	
	private Block parseBlock(String in_name, String in_property) {
		if (in_name!=null) {
			int position = in_name.indexOf('+');
			if (position!=-1) {
				String left = in_name.substring(0, position);
				String right = in_name.substring(position+1);
				VarBlock var1 = new VarBlock(left, null);
				TextBlock var2 = new TextBlock(right);
				return new AddBlock(var1, var2);
			}
		}
		return new VarBlock(in_name, in_property);
	}
	
	private String evalueExpression(PageContext in_pg) throws EvaluationException {
		StringBuffer lc_buffer = new StringBuffer(10 * blocks.size());
		Iterator lc_it = blocks.iterator();
		while (lc_it.hasNext()) {
			Block lc_block = (Block) lc_it.next();
			lc_buffer.append(lc_block.evaluate(in_pg));
		}				
		return lc_buffer.toString();	
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		Iterator it = blocks.iterator();
		while (it.hasNext()) {
			Block block = (Block) it.next();
			buffer.append(block.toString());
			buffer.append(" ");
		}
		return buffer.toString();
	}
	
	public static void main(String argv[]) {
		Expression lc_expr = new Expression("${personnes[${idPersonne}].qualite}", "${");
		System.out.println(lc_expr);
		
		lc_expr = new Expression("${name}", "${");
		System.out.println(lc_expr);
		
		lc_expr = new Expression("${name.property}", "${");
		System.out.println(lc_expr);
		
		lc_expr = new Expression("abc${name}def", "${");
		System.out.println(lc_expr);
		
		lc_expr = new Expression("${index+1}", "${");
		System.out.println(lc_expr);
	}
}
