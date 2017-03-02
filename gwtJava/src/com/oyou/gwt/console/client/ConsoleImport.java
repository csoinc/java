package com.oyou.gwt.console.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author	Owen Ou
 * @version $Id: ConsoleImport.java,v 1.1 2008/06/29 14:24:29 oyou Exp $
 * @since Nov 23, 2007
 */
public class ConsoleImport extends Composite implements ClickListener {

	private FlexTable ftPanel = new FlexTable();
	private TextBox tbPhotoPath = new TextBox();
	private TextBox tbGroupId = new TextBox();
	private TextArea taComment = new TextArea();
	private Button bSubmit;
	
	public ConsoleImport() {
		bSubmit = new Button("Submit", this);

		ftPanel.setWidget(0, 0, new HTML("<b>Photo Path:</b>"));
		tbPhotoPath.setVisibleLength(80);
		tbPhotoPath.setMaxLength(250);
		ftPanel.setWidget(0, 1, tbPhotoPath);
		ftPanel.setWidget(1, 1, new Label("Photo path is the server path that the photo files sitted in there."));
		ftPanel.getFlexCellFormatter().setColSpan(1, 0, 1);

		ftPanel.setWidget(2, 0, new HTML("<b>Album ID:</b>"));
		tbGroupId.setMaxLength(20);
		ftPanel.setWidget(2, 1, tbGroupId);
		ftPanel.setWidget(3, 1, new Label("Album ID is the album that the photo files will be put."));
		ftPanel.getFlexCellFormatter().setColSpan(3, 0, 1);

		ftPanel.setWidget(4, 0, new HTML("<b>Comment:</b>"));
		taComment.setCharacterWidth(70);
		taComment.setVisibleLines(3);
		ftPanel.setWidget(4, 1, taComment);
		ftPanel.setWidget(5, 1, new Label("Comment is the photo comment will be display on the web page."));
		ftPanel.getFlexCellFormatter().setColSpan(5, 0, 1);

		ftPanel.setWidget(6, 1, bSubmit);
		ftPanel.setWidth("800px");
		initWidget(ftPanel);
	}


	public void onClick(Widget sender) {
		if (sender == bSubmit) {
			Console.get().importPhotos(tbPhotoPath.getText(), tbGroupId.getText(), taComment.getText());
		}
	}
	
	public void reset() {
		tbPhotoPath.setText("");
		tbGroupId.setText("");
		taComment.setText("");
	}
	
}
