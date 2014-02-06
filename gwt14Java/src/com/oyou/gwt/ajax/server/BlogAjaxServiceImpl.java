package com.oyou.gwt.ajax.server;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.util.StringHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.oyou.common.exception.BusinessException;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogInformation;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.gwt.album.client.Album;
import com.oyou.gwt.album.client.AlbumException;
import com.oyou.gwt.album.client.ErrorConstants;
import com.oyou.gwt.album.client.IsAlbum;
import com.oyou.gwt.album.client.IsCategory;
import com.oyou.gwt.album.client.IsPhoto;
import com.oyou.gwt.banner.client.IsMessage;
import com.oyou.gwt.console.client.Console;
import com.oyou.gwt.console.client.ConsoleException;
import com.oyou.gwt.console.client.IsError;
import com.oyou.gwt.popup.client.Information;
import com.oyou.web.blog.util.AlbumHelper;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.blog.util.UploadHelper;

/**
 * 
 * @author Owen Ou
 * @version $Id: BlogAjaxServiceImpl.java,v 1.1 2008/06/29 14:24:37 oyou Exp $
 * @since Nov 6, 2007
 */
public class BlogAjaxServiceImpl extends RemoteServiceServlet implements BlogAjaxService {
	static final long serialVersionUID = 1;
	protected static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	protected static final String IMAGE_ICON_RELATIVE_PATH = "icon";
	protected static final String ALBUM_CREATE_MODE = "U";
	private static final Log log = LogFactory.getLog(BlogAjaxServiceImpl.class);
	private ApplicationContext applicationContext = null;
	
	public IsMessage[] getAnnouncement() {
		IsMessage[] messages = new IsMessage[1];
		try {
			BlogInformation blogInformation = this.getBlogService().getBlogInformationByID(BlogInformation.ANNOUNCEMENT_ID);
			messages[0] = new IsMessage();
			messages[0].setMessage(blogInformation.getInformation()); 
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return messages;
	}

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		}
		return applicationContext;
	}

	public BlogService getBlogService() {
		return (BlogService)this.getApplicationContext().getBean(BLOG_SERVICE);
	}

	public CommonService getCommonService() {
		return (CommonService)this.getApplicationContext().getBean(COMMON_SERVICE);
	}

	public UserService getUserService() {
		return (UserService)this.getApplicationContext().getBean(USER_SERVICE);
	}
	
	public Information[] getInformations(String mid) {
		Information[] messages = new Information[1];
		try {
			BlogInformation blogInformation = this.getBlogService().getBlogInformationByID(Long.valueOf(mid));
			messages[0] = new Information();
			messages[0].setInformation(blogInformation.getInformation()); 
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return messages;
	}
	
	public IsMessage[] getMessages(String mid) {
		IsMessage[] messages = new IsMessage[1];
		try {
			BlogInformation blogInformation = this.getBlogService().getBlogInformationByID(Long.valueOf(mid));
			messages[0] = new IsMessage();
			messages[0].setMessage(blogInformation.getInformation()); 
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return messages;
	}

	public IsAlbum[] getAlbums(String cid) throws AlbumException {
		IsAlbum[] albums = null;
		if (Album.DEBUG) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				;
			}
			albums = new IsAlbum[5];
			for (int i = 0; i < 5; i++) {
				albums[i] = new IsAlbum();
				albums[i].setId("" + i);
				albums[i].setName("album" + i);
				albums[i].setDescription("description" + i);
			}
			return albums;
		}	
		BlogUser blogUser = StrutsHelper.getBlogUser(this.getThreadLocalRequest());
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			AlbumException exception = new AlbumException(ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			if (StringHelper.isEmpty(cid)) cid = Long.toString(BlogGroupType.GROUP_ALBUM.longValue());
			List list = this.getBlogService().listBlogGroupsByGroupTypeID(Long.valueOf(cid));
			albums = new IsAlbum[list.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogGroup blogGroup = (BlogGroup) iter.next();
				albums[i] = new IsAlbum();
				albums[i].setId(blogGroup.getId().toString());
				albums[i].setName(blogGroup.getGroupName());
				albums[i].setDescription(blogGroup.getDescription());
				log.debug("=Album:" + albums[i].getName());
				i++;
			}
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return albums;
	}

	public IsCategory[] getCategories() throws AlbumException {
		IsCategory[] categories = null;
		if (Album.DEBUG) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				;
			}
			categories = new IsCategory[5];
			for (int i = 0; i < 5; i++) {
				categories[i] = new IsCategory();
				categories[i].setId("" + i);
				categories[i].setName("category" + i);
				categories[i].setNameCN("categoryCN" + i);
			}
			return categories;
		}	
		BlogUser blogUser = StrutsHelper.getBlogUser(this.getThreadLocalRequest());
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			AlbumException exception = new AlbumException(ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			List list = this.getBlogService().listBlogGroupTypes();
			categories = new IsCategory[list.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogGroupType blogGroupType = (BlogGroupType) iter.next();
				categories[i] = new IsCategory();
				categories[i].setId(blogGroupType.getId().toString());
				categories[i].setName(blogGroupType.getName());
				categories[i].setNameCN(blogGroupType.getNameCN());
				log.debug("=Category:" + categories[i].getName());
				i++;
			}
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return categories;
	}
	
	public IsPhoto[] getPhotos(String cid, String gid, String uid) throws AlbumException {
		IsPhoto[] photos = null;
		log.debug("=GID:" + gid);
		if (Album.DEBUG) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				;
			}
			photos = new IsPhoto[100];
			for (int i = 0; i < 100; i++) {
				photos[i] = new IsPhoto();
				photos[i].setComment("comment"+i);
				photos[i].setIcon("images/icon"+i%5+".png");
				photos[i].setImage("images/image"+i%5+".jpg");
				photos[i].setNickname("nickname"+i);
				photos[i].setTime("time"+i);
			}
			return photos;
		}	
		BlogUser blogUser = StrutsHelper.getBlogUser(this.getThreadLocalRequest());
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			AlbumException exception = new AlbumException(ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			List list = this.getBlogService().getBlogMessagePhotos(cid, gid, uid);
			List reply = this.getBlogService().getBlogReplyMessagePhotos(cid, gid, uid);
			photos = new IsPhoto[list.size() + reply.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogMessage blogMessage = (BlogMessage) iter.next();
				photos[i] = new IsPhoto();
				photos[i].setComment(blogMessage.getMessage());
				photos[i].setIcon(getIconUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime()) + "/" + blogMessage.getUploadFile());
				photos[i].setImage(getUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime()) + "/" + blogMessage.getUploadFile());
				photos[i].setNickname(blogMessage.getBlogUser().getNickname());
				photos[i].setTime(blogMessage.getUpdateTime().toString());
				log.debug("=Icon:" + photos[i].getIcon());
				log.debug("=Image:" + photos[i].getImage());
				i++;
			}
			for (Iterator iter = reply.iterator(); iter.hasNext();) {
				BlogReplyMessage blogMessage = (BlogReplyMessage) iter.next();
				photos[i] = new IsPhoto();
				photos[i].setComment(blogMessage.getMessage());
				photos[i].setIcon(getIconUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime()) + "/" + blogMessage.getUploadFile());
				photos[i].setImage(getUploadPath(BlogMessageType.IMAGE, blogMessage.getUpdateTime()) + "/" + blogMessage.getUploadFile());
				photos[i].setNickname(blogMessage.getBlogUser().getNickname());
				photos[i].setTime(blogMessage.getUpdateTime().toString());
				log.debug("=Icon:" + photos[i].getIcon());
				log.debug("=Image:" + photos[i].getImage());
				i++;
			}
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return photos;
	}

	public IsError importPhotos(String photoPath, String albumId, String comment) throws ConsoleException {
		IsError error = new IsError();
		if (Console.DEBUG) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ie) {
				;
			}
			log.debug("=Success");
			error.setMessage(IsError.SUCCESS);
			return error;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this.getThreadLocalRequest());
		if (blogUser == null || BlogUserType.GUEST.equals(blogUser.getBlogUserType().getId())) {
			ConsoleException exception = new ConsoleException(ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			AlbumHelper.getInstance().createAlbumByPhotos(photoPath, albumId, comment, ALBUM_CREATE_MODE, blogUser, getBlogService(), getCommonService(), getServletContext());
		} catch (Exception e) {
			//ConsoleException exception = new ConsoleException(e.getMessage());
			log.debug("==" + e.getMessage());
			//throw exception;
			error.setMessage(e.getMessage());
			return error;
		}
		error.setMessage(IsError.SUCCESS);
		return error;
	}
	
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.applicationContext = ctx;

	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getUploadPath(Long mType, Timestamp updateTime) {
		return UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getIconUploadPath(Long mType, Timestamp updateTime) {
		String uploadPath = this.getUploadPath(mType, updateTime);
		return uploadPath + "/" + IMAGE_ICON_RELATIVE_PATH;
	}

	
}
