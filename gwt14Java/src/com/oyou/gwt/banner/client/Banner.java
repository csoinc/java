package com.oyou.gwt.banner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * 
 * @author	Owen Ou
 * @version $Id: Banner.java,v 1.1 2008/06/29 14:24:40 oyou Exp $
 * @since Nov 12, 2007
 */
public class Banner implements EntryPoint {
  static final boolean DEBUG = false; 	 

  public void onModuleLoad() {
	RootPanel slot = RootPanel.get("banner-announcement");
    if (slot != null) {
      BannerMessageWidget announcement = new BannerMessageWidget("1", "Announcement");
      slot.add(announcement);
    }
    slot = RootPanel.get("banner-aboutus");
    if (slot != null) {
        BannerMessageWidget aboutUS = new BannerMessageWidget("4", "AboutUS");
        slot.add(aboutUS);
    }
    slot = RootPanel.get("banner-help");
    if (slot != null) {
        BannerMessageWidget help = new BannerMessageWidget("5", "Help");
        slot.add(help);
    }
    if (DEBUG) {
    	Window.alert(this.getFirstName() + " " + this.getLastName());
    }	
  }
  
  public native String getFirstName()/*-{ 
  	return $wnd.firstName;
  }-*/;

  public native String getLastName()/*-{ 
	return $wnd.lastName;
  }-*/;
  
}
