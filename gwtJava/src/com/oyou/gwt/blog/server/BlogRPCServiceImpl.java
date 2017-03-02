package com.oyou.gwt.blog.server;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.oyou.common.exception.BusinessException;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogGroup;
import com.oyou.domain.blog.orm.BlogGroupType;
import com.oyou.domain.blog.orm.BlogMessage;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogReplyMessage;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.domain.blog.orm.BlogUserType;
import com.oyou.gwt.blog.client.Blog;
import com.oyou.gwt.blog.client.BlogException;
import com.oyou.gwt.blog.client.ErrorConstants;
import com.oyou.gwt.blog.client.IsArticle;
import com.oyou.gwt.blog.client.IsComment;
import com.oyou.gwt.blog.client.IsErrorCode;
import com.oyou.gwt.blog.client.IsGroup;
import com.oyou.gwt.blog.client.IsGroupType;
import com.oyou.gwt.blog.client.IsPicture;
import com.oyou.web.blog.util.StrutsHelper;
import com.oyou.web.blog.util.UploadHelper;

/**
 * 
 * @author Owen Ou
 * @version $Id: BlogRPCServiceImpl.java,v 1.1 2008/06/29 14:24:38 oyou Exp $
 * @since Dec 5, 2007
 */
public class BlogRPCServiceImpl extends RemoteServiceServlet implements
		BlogRPCService {
	static final long serialVersionUID = 1;
	protected static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	protected static final String IMAGE_ICON_RELATIVE_PATH = "icon";
	private static final Log log = LogFactory.getLog(BlogRPCServiceImpl.class);
	private ApplicationContext applicationContext = null;
	private static final String SUCCESS = "0";
	private static final String FAIL = "-1";

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(this.getServletContext());
		}
		return applicationContext;
	}

	public BlogService getBlogService() {
		return (BlogService) this.getApplicationContext().getBean(BLOG_SERVICE);
	}

	public UserService getUserService() {
		return (UserService) this.getApplicationContext().getBean(USER_SERVICE);
	}

	public IsGroup[] getAlbums(String cid) throws BlogException {
		IsGroup[] albums = null;
		if (Blog.DEBUG) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
				;
			}
			albums = new IsGroup[5];
			for (int i = 0; i < 5; i++) {
				albums[i] = new IsGroup();
				albums[i].setId("" + i);
				albums[i].setName("album" + i);
				albums[i].setDescription("description" + i);
			}
			return albums;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser == null
				|| BlogUserType.GUEST
						.equals(blogUser.getBlogUserType().getId())) {
			BlogException exception = new BlogException(
					ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			if (StringHelper.isEmpty(cid))
				cid = Long.toString(BlogGroupType.GROUP_ALBUM.longValue());
			List list = this.getBlogService().listBlogGroupsByGroupTypeID(
					Long.valueOf(cid));
			albums = new IsGroup[list.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogGroup blogGroup = (BlogGroup) iter.next();
				albums[i] = new IsGroup();
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

	public IsGroupType[] getCategories() throws BlogException {
		IsGroupType[] categories = null;
		if (Blog.DEBUG) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
				;
			}
			categories = new IsGroupType[5];
			for (int i = 0; i < 5; i++) {
				categories[i] = new IsGroupType();
				categories[i].setId("" + i);
				categories[i].setName("category" + i);
				categories[i].setNameCN("categoryCN" + i);
			}
			return categories;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser == null
				|| BlogUserType.GUEST
						.equals(blogUser.getBlogUserType().getId())) {
			BlogException exception = new BlogException(
					ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		}
		try {
			List list = this.getBlogService().listBlogGroupTypes();
			categories = new IsGroupType[list.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogGroupType blogGroupType = (BlogGroupType) iter.next();
				categories[i] = new IsGroupType();
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

	public IsPicture[] getPhotos(String cid, String gid, String uid)
			throws BlogException {
		IsPicture[] photos = null;
		log.debug("==getPhotos(CID,GID,UID)=(" + cid + "," + gid + "," + uid
				+ ")");
		if (Blog.DEBUG) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
				;
			}
			photos = new IsPicture[100];
			for (int i = 0; i < 100; i++) {
				photos[i] = new IsPicture();
				photos[i].setId("" + i);
				photos[i].setSummary("summary" + i);
				photos[i].setComment("comment" + i);
				photos[i].setIcon("images/icon" + i % 5 + ".png");
				photos[i].setImage("images/image" + i % 5 + ".jpg");
				photos[i].setNickname("nickname" + i);
				photos[i].setTime("time" + i);
			}
			return photos;
		}

		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser == null
				|| BlogUserType.GUEST
						.equals(blogUser.getBlogUserType().getId())) {
			BlogException exception = new BlogException(
					ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		} else {
			boolean yes = StringHelper.booleanValue(uid);
			if (yes)
				uid = blogUser.getId().toString();
		}

		try {
			List list = this.getBlogService().getBlogMessagePhotos(cid, gid,
					uid);
			List reply = this.getBlogService().getBlogReplyMessagePhotos(cid,
					gid, uid);
			photos = new IsPicture[list.size() + reply.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogMessage blogMessage = (BlogMessage) iter.next();
				photos[i] = new IsPicture();
				photos[i].setId(blogMessage.getId().toString());
				photos[i].setComment(HTMLHelper.formatToText(blogMessage
						.getMessage()));
				photos[i].setIcon(getIconUploadPath(BlogMessageType.IMAGE,
						blogMessage.getUpdateTime())
						+ "/" + blogMessage.getUploadFile());
				photos[i].setImage(getUploadPath(BlogMessageType.IMAGE,
						blogMessage.getUpdateTime())
						+ "/" + blogMessage.getUploadFile());
				photos[i].setNickname(blogMessage.getBlogUser().getNickname());
				photos[i].setTime(blogMessage.getUpdateTime().toString());
				StringBuffer sb = new StringBuffer();
				sb.append("<b>");
				if (StringHelper.isNotEmpty(blogMessage.getTitle()))
					sb.append(blogMessage.getTitle());
				sb.append("</b>");
				if (StringHelper.isNotEmpty(blogMessage.getSummary())) {
					sb.append("&nbsp;&nbsp;");
					sb.append(blogMessage.getSummary());
				}
				if (StringHelper.isNotEmpty(blogMessage.getLinkURL())) {
					sb.append("&nbsp;&nbsp;");
					sb.append("<a href=");
					sb.append(blogMessage.getLinkURL());
					sb.append(" target=_blank>");
					sb.append(blogMessage.getLinkURL());
					sb.append("</a>");
				}
				photos[i].setSummary(sb.toString());
				log.debug("=Icon:" + photos[i].getIcon());
				log.debug("=Image:" + photos[i].getImage());
				i++;
			}
			for (Iterator iter = reply.iterator(); iter.hasNext();) {
				BlogReplyMessage blogMessage = (BlogReplyMessage) iter.next();
				photos[i] = new IsPicture();
				photos[i].setId(blogMessage.getId().toString());
				photos[i].setComment(HTMLHelper.formatToText(blogMessage
						.getMessage()));
				photos[i].setIcon(getIconUploadPath(BlogMessageType.IMAGE,
						blogMessage.getUpdateTime())
						+ "/" + blogMessage.getUploadFile());
				photos[i].setImage(getUploadPath(BlogMessageType.IMAGE,
						blogMessage.getUpdateTime())
						+ "/" + blogMessage.getUploadFile());
				photos[i].setNickname(blogMessage.getBlogUser().getNickname());
				photos[i].setTime(blogMessage.getUpdateTime().toString());
				StringBuffer sb = new StringBuffer();
				if (StringHelper.isNotEmpty(blogMessage.getLinkURL())) {
					sb.append("<a href=");
					sb.append(blogMessage.getLinkURL());
					sb.append(" target=_blank>");
					sb.append(blogMessage.getLinkURL());
					sb.append("</a>");
				}
				photos[i].setSummary(sb.toString());
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

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.applicationContext = ctx;

	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getUploadPath(Long mType, Timestamp updateTime) {
		return UploadHelper.getInstance().getUploadRelativePath(mType,
				updateTime);
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

	public IsGroup[] getGroups(String cid) throws BlogException {
		return this.getAlbums(cid);
	}

	public IsArticle[] getArticles(String cid, String gid, String uid)
			throws BlogException {
		IsArticle[] articles = null;
		log.debug("==getArticles(CID,GID,UID)=(" + cid + "," + gid + "," + uid
				+ ")");
		if (Blog.DEBUG) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
				;
			}
			articles = new IsArticle[100];
			for (int i = 0; i < 100; i++) {
				articles[i] = new IsArticle();
				articles[i].setId("" + i);
				articles[i].setContent("content" + i);
				articles[i].setTitle("title" + i);
				articles[i].setSummary("summary" + i);
				articles[i].setNickname("nickname" + i);
				articles[i].setTime("time" + i);
			}
			return articles;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser == null
				|| BlogUserType.GUEST
						.equals(blogUser.getBlogUserType().getId())) {
			BlogException exception = new BlogException(
					ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		} else {
			boolean yes = StringHelper.booleanValue(uid);
			if (yes)
				uid = blogUser.getId().toString();
		}
		try {
			List list = this.getBlogService().getBlogMessageArticles(cid, gid,
					uid);
			articles = new IsArticle[list.size()];
			int i = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				BlogMessage blogMessage = (BlogMessage) iter.next();
				articles[i] = new IsArticle();
				articles[i].setId(blogMessage.getId().toString());
				StringBuffer sb = new StringBuffer();
				sb.append(blogMessage.getMessage());
				if (StringHelper.isNotEmpty(blogMessage.getLinkURL())) {
					sb.append("&nbsp;&nbsp;");
					sb.append("<a href=");
					sb.append(blogMessage.getLinkURL());
					sb.append(" target=_blank>");
					sb.append(blogMessage.getLinkURL());
					sb.append("</a>");
				}
				articles[i].setContent(sb.toString());
				articles[i].setTitle(blogMessage.getTitle());
				articles[i].setSummary(blogMessage.getSummary());
				articles[i]
						.setNickname(blogMessage.getBlogUser().getNickname());
				articles[i].setTime(blogMessage.getUpdateTime().toString());
				i++;
			}
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return articles;
	}

	public IsComment[] getComments(String mid, String uid) throws BlogException {
		IsComment[] comments = null;
		log.debug("(MID,UID)=(" + mid + "," + uid + ")");
		if (Blog.DEBUG) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				;
			}
			int length = 10;
			comments = new IsComment[length];
			for (int i = 0; i < length; i++) {
				comments[i] = new IsComment();
				comments[i].setId("" + i);
				comments[i].setComment("comment" + i);
				comments[i].setNickname("nickname" + i);
				comments[i].setTime("time" + i);
			}
			return comments;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser == null
				|| BlogUserType.GUEST
						.equals(blogUser.getBlogUserType().getId())) {
			BlogException exception = new BlogException(
					ErrorConstants.error_message_authority);
			log.debug("==" + ErrorConstants.error_message_authority);
			throw exception;
		} else {
			boolean yes = StringHelper.booleanValue(uid);
			if (yes)
				uid = blogUser.getId().toString();
		}
		try {
			List reply = this.getBlogService().getBlogMessageComments(mid, uid);
			comments = new IsComment[reply.size()];
			int i = 0;
			for (Iterator iter = reply.iterator(); iter.hasNext();) {
				BlogReplyMessage blogMessage = (BlogReplyMessage) iter.next();
				comments[i] = new IsComment();
				comments[i].setId(blogMessage.getId().toString());
				StringBuffer sb = new StringBuffer();
				sb.append(blogMessage.getMessage());
				if (StringHelper.isNotEmpty(blogMessage.getLinkURL())) {
					sb.append("&nbsp;&nbsp;");
					sb.append("<a href=");
					sb.append(blogMessage.getLinkURL());
					sb.append(" target=_blank>");
					sb.append(blogMessage.getLinkURL());
					sb.append("</a>");
				}
				comments[i].setComment(sb.toString());
				comments[i]
						.setNickname(blogMessage.getBlogUser().getNickname());
				comments[i].setTime(blogMessage.getUpdateTime().toString());
				i++;
			}
		} catch (BusinessException be) {
			be.printStackTrace();
			log.error(be.getMessage());
		}
		return comments;
	}

	public IsErrorCode updatePhoto(String mid, String comment)
			throws BlogException {
		IsErrorCode errorCode = new IsErrorCode();
		log.debug("(MID,COMMENT)=(" + mid + "," + comment + ")");
		if (Blog.DEBUG) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				;
			}
			errorCode.setMessage(SUCCESS);
			return errorCode;
		}
		BlogUser blogUser = StrutsHelper.getBlogUser(this
				.getThreadLocalRequest());
		if (blogUser != null
				&& (BlogUserType.LEADER.equals(blogUser.getBlogUserType()
						.getId()) || BlogUserType.ADMIN.equals(blogUser
						.getBlogUserType().getId()))) {
			try {
				Long id = Long.valueOf(mid);
				log.debug("==ID: " + id.toString());
				BlogMessage blogMessage = this.getBlogService()
						.getBlogMessageByID(Long.valueOf(mid));
				if (blogMessage != null) {
					blogMessage.setMessage(HTMLHelper.formatToHTML(comment));
					this.getBlogService().updateBlogMessage(blogMessage);
				} else {
					BlogReplyMessage blogReplyMessage = this.getBlogService()
							.getBlogReplyMessageByID(Long.valueOf(mid));
					if (blogReplyMessage != null) {
						blogReplyMessage.setMessage(HTMLHelper
								.formatToHTML(comment));
						this.getBlogService().updateBlogReplyMessage(
								blogReplyMessage);
					}
				}
				errorCode.setId(SUCCESS);
				errorCode.setMessage("SUCCESS");
			} catch (BusinessException be) {
				be.printStackTrace();
				errorCode.setId(FAIL);
				errorCode.setMessage("FAIL: " + be.getMessage());
				log.error(be.getMessage());
			}
		} else {
			errorCode.setId(FAIL);
			errorCode.setMessage("FAIL: "
					+ ErrorConstants.error_message_authority);
		}
		return errorCode;
	}

}
