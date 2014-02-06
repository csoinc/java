package fr.improve.struts.taglib.layout.util;


/**
 * Definition of the mathCollection interface
 * @author: Damien Viel
 */
public interface IMathCollectionRenderer  extends PanelInterface {
	public void startMathData(StringBuffer in_buffer);
	public void endMathData(StringBuffer in_buffer);
	public void renderMathData(StringBuffer out_buffer, String in_data, int in_span, String in_resutlId, String in_style);
}