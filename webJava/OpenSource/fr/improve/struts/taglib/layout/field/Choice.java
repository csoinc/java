/*
 * Copyright Improve SA 2005
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.field;

/**
 * Interface that must be implemented by tags that want
 * to add an option to an option list.
 * @author jribette
 */
public interface Choice {
	public String getChoiceValue();
	public String getChoiceLabel();
	public String getChoiceTooltip();
}
