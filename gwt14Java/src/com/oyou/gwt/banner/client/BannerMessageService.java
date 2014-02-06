package com.oyou.gwt.banner.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BannerMessageService.java,v 1.1 2008/06/29 14:24:39 oyou Exp $
 * Nov 5, 2007
 */
public interface BannerMessageService extends RemoteService {

	IsMessage[] getMessages(String msgId);
	IsMessage[] getAnnouncement();
	
}
