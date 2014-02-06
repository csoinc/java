package fr.improve.struts.taglib.layout.util;

import java.util.Vector;
/**
 * Permet de factoriser le code commun à tous les
 * formulaires nécessitant l'utilisation d'une
 * liste de checkbox (utiliser une instance par
 * liste présente dans le formulaire).
 * @author: Francois MAURIT
 */
public class SelectionHelper {
	/**
	 * Permet de redimensionner la liste si besoin.
	 */
	protected Vector selection = new Vector();
/**
 * Accès au résultat.
 */
public Vector getSelection() {
	return selection;
}
/**
 * Retourne l'élément à la position demandée s'il existe,
 * <code>null</code> sinon.
 */
public String getSelection(int in_index) {
	if (in_index < selection.size())
		return (String) selection.get(in_index);
	return null;
}
/**
 * Setter adapté à Struts.
 */
public void setSelection(int in_index, String in_value) {
	if (in_index >= selection.size()) {
		selection.setSize(in_index + 1);//on laisse la classe gérer son propre incrément + SELECTION_INCREMENT);
	}
	selection.set(in_index, in_value);
}
public void setSelection(java.util.Collection in_values) {
	selection = new Vector(in_values);
}
}
