package fr.improve.struts.taglib.layout.policy;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
/**
 * @author: JeanNoël Ribette
 */
public abstract class AbstractPolicy {
	public static final short MODE_NODISPLAY = AbstractModeFieldTag.MODE_NODISPLAY;
	public static final short MODE_HIDDEN = AbstractModeFieldTag.MODE_HIDDEN;
	public static final short MODE_INSPECT = AbstractModeFieldTag.MODE_INSPECT;
	public static final short MODE_INSPECT_ONLY = AbstractModeFieldTag.MODE_INSPECT_ONLY;
	public static final short MODE_INSPECT_PRESENT = AbstractModeFieldTag.MODE_INSPECT_PRESENT;
	public static final short MODE_EDIT = AbstractModeFieldTag.MODE_EDIT;
	public static final short MODE_READONLY = AbstractModeFieldTag.MODE_READONLY;
	public static final short MODE_DISABLED = AbstractModeFieldTag.MODE_DISABLED;
	public static final short MODE_CELL = AbstractModeFieldTag.MODE_CELL;
	public abstract short getAuthorizedDisplayMode(String in_policy, String in_name, String in_property, PageContext in_pageContext);
}
