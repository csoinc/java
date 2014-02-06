package fr.improve.struts.taglib.layout.sort;

import java.text.Collator;
import java.util.Locale;

/**
 * French sort rules : 
 * consider "equals" upper and lowercas letters.
 * consider "equals" accented and unaccented letters.
 */
public class FrenchSortRules implements SortRules {

	public Collator getRules() {
		Collator collator = Collator.getInstance(Locale.FRENCH);
		collator.setDecomposition(Collator.PRIMARY);
		return collator;
	}

}
