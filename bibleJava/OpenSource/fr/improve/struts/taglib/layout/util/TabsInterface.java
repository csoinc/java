package fr.improve.struts.taglib.layout.util;

import java.util.Map;

/**
 * @author jnribette
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface TabsInterface extends PanelInterface {
	public void doPrintHeaders(StringBuffer out_buffer, String in_tabGroupId, Map in_headers);
	public void doStartHeaders(StringBuffer buffer);
	public void doEndHeaders(StringBuffer buffer);		
	public String getHeaderEnabledStyle();
	public String getHeaderDisabledStyle();
	public String getHeaderErrorStyle();
		
		
}
