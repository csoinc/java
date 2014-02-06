package com.oyou.gwt.popup.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Popup.java,v 1.1 2008/06/29 14:23:36 oyou Exp $
 * @since Nov 12, 2007
 */
public class Popup implements EntryPoint {

  public void onModuleLoad() {
	RootPanel slot = RootPanel.get("popup-announcement");
    if (slot != null) {
      PopupInformationWidget announcement = new PopupInformationWidget("1", "Announcement");
      slot.add(announcement);
    }

    slot = RootPanel.get("popup-aboutus");
    if (slot != null) {
        PopupInformationWidget aboutUS = new PopupInformationWidget("4", "AboutUS");
        slot.add(aboutUS);
    }
    
    slot = RootPanel.get("popup-help");
    if (slot != null) {
        PopupInformationWidget help = new PopupInformationWidget("5", "Help");
        slot.add(help);
    }
  
  }
}
