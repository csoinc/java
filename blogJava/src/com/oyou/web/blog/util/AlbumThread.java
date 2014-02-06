package com.oyou.web.blog.util;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;
import org.hibernate.ObjectNotFoundException;

import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.DateHelper;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogUser;

public class AlbumThread implements Runnable {
	private static final Log log = LogFactory.getLog(AlbumThread.class);
	private String photoPath, groupId, comment, mode;
	private long minId = 0;
	private long maxId = 0;
	private BlogUser blogUser;
	private BlogService blogService;
	private CommonService commonService;
	private ServletContext ctx;

	private AlbumThread() {
	}

	public AlbumThread(String photoPath, String groupId, String comment, String mode, BlogUser blogUser, BlogService blogService,
			CommonService commonService, ServletContext ctx) {
		this.photoPath = photoPath;
		this.groupId = groupId;
		this.comment = comment;
		this.mode = mode;
		this.blogUser = blogUser;
		this.blogService = blogService;
		this.commonService = commonService;
		this.ctx = ctx;
		this.minId = 0;
		this.maxId = 0;
	}

	/**
	 * 
	 * @param photoPath
	 *            The path that hold the photos
	 * @param groupId
	 *            The group that the upload photo to
	 * @param comment
	 *            The photo comment
	 * @param blogService
	 * @param userService
	 * @param ctx
	 * @throws Exception
	 */
	public void processPhotoFile(File file) {
		Long uTypeId = null;
		String uploadName = null;
		BlogMessage blogMessage = new BlogMessage();
		blogMessage.setLinkURL("");
		uTypeId = BlogMessageType.IMAGE;
		log.debug("==file name: " + file.getAbsolutePath());
		try {
			uploadName = AlbumHelper.getInstance().saveUploadFile(file.getAbsolutePath(), uTypeId, ctx);
			blogMessage.setUploadFile(uploadName);
			BlogMessageType blogMessageType = blogService.getBlogMessageTypeByID(uTypeId);
			blogMessage.setBlogMessageType(blogMessageType);
			blogMessage.setCreateTime(DateHelper.getCurrentTimestamp());
			blogMessage.setStatus(true);
			blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
			blogMessage.setMessage(HTMLHelper.formatToHTML(comment));
			blogMessage.setBlogUser(blogUser);
			blogMessage.setBlogGroup(blogService.getBlogGroupByID(Long.valueOf(groupId)));
			blogService.createBlogMessage(blogMessage);
			ActionMessage message = new ActionMessage("message.upload.created.confirmed", file.getName());
			AdminHelper.saveLog(StrutsHelper.getFormattedMessage(message), blogService, commonService);
		} catch (BusinessException be) {
			log.error("process photo filename: " + file.getAbsolutePath());
		}
	}

	public void processPhotoFileForUpdate(File file) {
		Long uTypeId = null;
		String uploadName = null;
		BlogMessage blogMessage = null;
		boolean isUpdate = true;
		uTypeId = BlogMessageType.IMAGE;
		log.debug("==file name: " + file.getAbsolutePath());
		try {
			uploadName = AlbumHelper.getInstance().saveUploadFile(file.getAbsolutePath(), uTypeId, ctx);
			if (this.minId <= this.maxId) {
				blogMessage = this.getNextBlogMessage();
				if (blogMessage == null) {
					this.minId = this.maxId + 1; 
					isUpdate = false;
					blogMessage = new BlogMessage();
				} 
			} else {
				isUpdate = false;
				blogMessage = new BlogMessage();
			}
			if (isUpdate) {
				// blogMessage.setLinkURL("");
				blogMessage.setUploadFile(uploadName);
				BlogMessageType blogMessageType = blogService.getBlogMessageTypeByID(uTypeId);
				blogMessage.setBlogMessageType(blogMessageType);
				blogMessage.setCreateTime(DateHelper.getCurrentTimestamp());
				// blogMessage.setStatus(true);
				blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
				//blogMessage.setMessage(HTMLHelper.formatToHTML(comment));
				blogMessage.setBlogUser(blogUser);
				// blogMessage.setBlogGroup(blogService.getBlogGroupByID(Long.valueOf(groupId)));
				blogService.updateBlogMessage(blogMessage);
			} else {
				blogMessage.setLinkURL("");
				blogMessage.setUploadFile(uploadName);
				BlogMessageType blogMessageType = blogService.getBlogMessageTypeByID(uTypeId);
				blogMessage.setBlogMessageType(blogMessageType);
				blogMessage.setCreateTime(DateHelper.getCurrentTimestamp());
				blogMessage.setStatus(true);
				blogMessage.setUpdateTime(DateHelper.getCurrentTimestamp());
				blogMessage.setMessage(HTMLHelper.formatToHTML(comment));
				blogMessage.setBlogUser(blogUser);
				blogMessage.setBlogGroup(blogService.getBlogGroupByID(Long.valueOf(groupId)));
				blogService.createBlogMessage(blogMessage);
			}
			ActionMessage message = new ActionMessage("message.upload.created.confirmed", file.getName());
			AdminHelper.saveLog(StrutsHelper.getFormattedMessage(message), blogService, commonService);
		} catch (BusinessException be) {
			log.error("process photo filename: " + file.getAbsolutePath());
		}
	}

	public void processPhotoPath(File dir) {
		if (dir.isDirectory()) {
			File[] photos = dir.listFiles();
			for (int i = 0; i < photos.length; i++) {
				File file = photos[i];
				if (file.isDirectory())
					processPhotoPath(file);
				else {
					String suffix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
					if (StringHelper.isEmpty(suffix))
						continue;
					else
						suffix = suffix.toLowerCase();
					if (suffix.indexOf("jpg") != -1 || suffix.indexOf("gif") != -1 || suffix.indexOf("png") != -1
							|| suffix.indexOf("bmp") != -1) {
						log.debug("==file name: " + file.getAbsolutePath());
						this.processPhotoFile(file);
					} else {
						log.debug("==not photo: " + file.getAbsolutePath());
					}
				}
			}
		}
	}

	public void processPhotoPathForUpdate(File dir) {
		if (dir.isDirectory()) {
			File[] photos = dir.listFiles();
			for (int i = 0; i < photos.length; i++) {
				File file = photos[i];
				if (file.isDirectory())
					processPhotoPathForUpdate(file);
				else {
					String suffix = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("."));
					if (StringHelper.isEmpty(suffix))
						continue;
					else
						suffix = suffix.toLowerCase();
					if (suffix.indexOf("jpg") != -1 || suffix.indexOf("gif") != -1 || suffix.indexOf("png") != -1
							|| suffix.indexOf("bmp") != -1) {
						log.debug("==file name: " + file.getAbsolutePath());
						this.processPhotoFileForUpdate(file);
					} else {
						log.debug("==not photo: " + file.getAbsolutePath());
					}
				}
			}
		}
	}

	public void run() {
		File dir = new File(photoPath);
		if ("U".equalsIgnoreCase(this.mode)) {
			processPhotoPathForUpdate(dir);
		} else {
			processPhotoPath(dir);
		}
	}

	public BlogMessage getNextBlogMessage() {
		BlogMessage blogMessage = null;
		try {
			if (this.maxId == 0) {
				this.maxId = commonService.getObjectMaxID(BlogMessage.class);
				log.info("MessageMaxId:" + this.maxId);
			}	
			for (long i = this.minId; i <= this.maxId; i++) {
				try {
					BlogMessage element = (BlogMessage) commonService.get(BlogMessage.class, Long.valueOf(i));
					if (element != null) {
						if (element.getBlogGroup() != null) {
							Long gid = element.getBlogGroup().getId();
							if (Long.parseLong(this.groupId) == gid.longValue()) {
								log.info("Get blog message: " + element.getId().toString());
								this.minId = i + 1;
								blogMessage = element;
								break;
							}
						}
					} else {
						continue;
					}
				} catch (ObjectNotFoundException oe) {
					log.warn(oe.getMessage());
				}
			}
		} catch (BusinessException be) {
			log.error(be.getMessage());
		}
		return blogMessage;
	}

}
