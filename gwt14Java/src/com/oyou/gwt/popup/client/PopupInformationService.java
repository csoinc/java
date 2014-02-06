package com.oyou.gwt.popup.client;



import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: PopupInformationService.java,v 1.1 2008/06/29 14:23:36 oyou Exp $
 * Nov 5, 2007
 */
public interface PopupInformationService extends RemoteService {

	Information[] getInformations(String infoId);

}
