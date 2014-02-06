package com.oyou.gwt.ajax.server;


import com.oyou.common.spring.SpringService;
import com.oyou.gwt.album.client.AlbumDataService;
import com.oyou.gwt.banner.client.BannerMessageService;
import com.oyou.gwt.console.client.ConsoleDataService;
import com.oyou.gwt.popup.client.PopupInformationService;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogAjaxService.java,v 1.1 2008/06/29 14:24:38 oyou Exp $
 * @since Nov 7, 2007
 */
public interface BlogAjaxService extends ConsoleDataService, AlbumDataService, PopupInformationService, BannerMessageService, SpringService {

}
