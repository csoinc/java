package fr.improve.struts.taglib.layout.el;

import javax.servlet.jsp.PageContext;

/**
 * Block that computes a sum.
 */
public class AddBlock implements Block {
	private Block left;
	private Block right;
	
	public AddBlock(Block in_left,Block in_right) {
		left = in_left;
		right = in_right;
	}
	
	public String evaluate(PageContext in_pg) throws EvaluationException {
		String leftAsString = left.evaluate(in_pg);
		String rightAsString = right.evaluate(in_pg);
		try {
			int left = Integer.parseInt(leftAsString);
			int right = Integer.parseInt(rightAsString);
			return String.valueOf(left+right);
		} catch (NumberFormatException e) {
			throw new EvaluationException("Operand is not a number : " + leftAsString + " + " + rightAsString);
		}
	}
	
	public String toString() {
		return new StringBuffer("[A( ").append(left.toString()).append(" , ").append(right.toString()).append(" )]").toString();
	}

	
}
