package com.oyou.web.blog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.springframework.web.context.WebApplicationContext;

import com.oyou.common.process.ProcessCommand;
import com.oyou.common.spring.SpringService;
import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.web.blog.util.ConfigHelper;
import com.oyou.web.blog.util.UploadHelper;
//import com.oyou.web.image.IconManager;
//import com.oyou.web.image.PhotoManager;
import com.oyou.web.struts.StrutsAction;

public abstract class BlogAction extends StrutsAction {
	private static final Log log = LogFactory.getLog(BlogAction.class);
	protected static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	protected static final String IMAGE_ICON_RELATIVE_PATH = "icon";
	protected static final String IMAGE_PUBLISH_RELATIVE_PATH = "image";

	protected static final String EMAIL_MANAGER = "emailManager";
	protected static final String DEFAULT_SUBJECT = "";

	private BlogService blogService;
	private CommonService commonService;

	public void copyFile(String fromFile, String toFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		byte[] buffer = new byte[2048];
		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);
			int i = 0;
			while ((i = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, i);
			}
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	protected boolean deleteUploadFile(Long mType, Timestamp updateTime, String uploadName) {
		String uploadPath = getUploadPath(mType, updateTime);
		String uploadFile = uploadPath + uploadName;
		File file = new File(uploadFile);
		if (file.exists())
			return file.delete();
		return true;
	}

	public BlogService getBlogService() {
		if (blogService == null) {
			log.debug(">>BEAN blogService is null");
			try {
				WebApplicationContext ctx = getWebApplicationContext();
				if (ctx != null)
					if (ctx.containsBean(SpringService.BLOG_SERVICE)) {
						Object obj = ctx.getBean(SpringService.BLOG_SERVICE);
						if (obj != null)
							blogService = (BlogService) obj;
					}
				if (blogService == null) {
					log.fatal(">>Can't get blogService from Application Context");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception on get context " + e.getMessage());
			}
		}
		return blogService;
	}

	public CommonService getCommonService() {
		if (commonService == null) {
			log.debug(">>BEAN commonService is null");
			try {
				WebApplicationContext ctx = getWebApplicationContext();
				if (ctx != null)
					if (ctx.containsBean(SpringService.COMMON_SERVICE)) {
						Object obj = ctx.getBean(SpringService.COMMON_SERVICE);
						if (obj != null)
							commonService = (CommonService) obj;
					}
				if (commonService == null) {
					log.fatal(">>Can't get commonService from Application Context");
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception on get context " + e.getMessage());
			}
		}
		return commonService;
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getIconUploadPath(Long mType, Timestamp updateTime) {
		String uploadPath = this.getUploadPath(mType, updateTime);
		log.debug("IconUploadPath:" + uploadPath);
		return uploadPath + File.separator + IMAGE_ICON_RELATIVE_PATH;
	}

	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.create", "create");
		map.put("button.update", "update");
		map.put("button.delete", "delete");
		map.put("button.search", "list");
		map.put("button.send", "send");
		return map;
	}

	protected Long getUploadFileType(FormFile uFile) {
		Long uType = BlogMessageType.TEXT;
		if ((uFile != null) && uFile.getFileSize() > 0) {
			String contentType = uFile.getContentType();
			if (contentType != null)
				contentType = contentType.toLowerCase();
			log.debug("====contentType: " + contentType);
			if (StringHelper.isNotEmpty(contentType)) {
				if (contentType.indexOf("image") != -1 || contentType.indexOf("jpg") != -1 || contentType.indexOf("gif") != -1
						|| contentType.indexOf("png") != -1 || contentType.indexOf("bmp") != -1) {
					uType = BlogMessageType.IMAGE;
				} else if (contentType.indexOf("music") != -1 || contentType.indexOf("mp3") != -1
						|| contentType.indexOf("wma") != -1 || contentType.indexOf("rm") != -1
						|| contentType.indexOf("audio") != -1 || contentType.indexOf("mpeg") != -1) {
					uType = BlogMessageType.MUSIC;
				} else if (contentType.indexOf("plain") != -1 || contentType.indexOf("txt") != -1
						|| contentType.indexOf("text") != -1) {
					uType = BlogMessageType.PLAIN;
				} else if (contentType.indexOf("html") != -1 || contentType.indexOf("htm") != -1) {
					uType = BlogMessageType.HTML;
				} else if (contentType.indexOf("doc") != -1 || contentType.indexOf("word") != -1) {
					uType = BlogMessageType.WORD;
				} else {
					uType = BlogMessageType.FILE;
				}
			}
		}
		log.debug("====contentType: " + uType);
		return uType;
	}

	/**
	 * @param mType
	 * @return
	 */
	protected String getUploadPath(Long mType) {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativePath(mType);
		return this.getBlogService().getBlogProperties(ConfigHelper.UPLOAD_PATH).getValue() + File.separator
				+ UploadHelper.getInstance().getUploadRelativePath(mType);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @return
	 */
	protected String getUploadPath(Long mType, Timestamp updateTime) {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
		return this.getBlogService().getBlogProperties(ConfigHelper.UPLOAD_PATH).getValue() + File.separator
				+ UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
	}

	protected String getUploadRoot() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.UPLOAD_PATH).getValue();
	}

	protected String getTmpPath() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.TMP_PATH).getValue();
	}

	protected String getImagePath() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.IMAGE_PATH).getValue();
	}

	protected String getSiteDomain() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.SITE_DOMAIN).getValue();
	}

	protected String getFromEmail() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.FROM_EMAIL).getValue();
	}

	protected String getAdminEmail() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.ADMIN_EMAIL).getValue();
	}

	protected String getAdminName() {
		// return UploadHelper.getContextRealPath(this.getServletContext()) +
		// UploadHelper.getInstance().getUploadRelativeRoot();
		return this.getBlogService().getBlogProperties(ConfigHelper.ADMIN_NAME).getValue();
	}
	
	protected String saveUploadFile(FormFile uFile, Long uType, String title) {
		String uploadName = "";
		try {
			String fileName = uFile.getFileName();
			log.debug(">>>>upload filename " + fileName);
			int fileSize = uFile.getFileSize();
			byte[] fileData = uFile.getFileData();
			log.debug(">>>>File Name: " + fileName);
			log.debug(">>>>File Size: " + fileSize);
			String uploadPath = this.getUploadPath(uType);
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			log.debug(">>>>Suffix = " + suffix);
			
			if (title == null || "".equals(title)) title = "file";
			uploadName = Long.toString(Calendar.getInstance().getTimeInMillis());
			uploadName += "-" + title.replaceAll(" ", "-");

			if (StringHelper.isNotEmpty(suffix)) {
				uploadName += suffix.toLowerCase();
			}
			log.info(">>>>Saved File Name: " + uploadName);
			File pathToCreate = new File(uploadPath);
			if (!pathToCreate.exists())
				pathToCreate.mkdirs();
			File fileToCreate = new File(uploadPath, uploadName);
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
				fileOutStream.write(fileData);
				fileOutStream.flush();
				fileOutStream.close();
			}
			if (uType.equals(BlogMessageType.IMAGE)) {
				String uploadTmpPath = this.getTmpPath() + File.separator;
				String uploadIconPath = uploadPath + IMAGE_ICON_RELATIVE_PATH + File.separator;
				File tmpPathToCreate = new File(uploadTmpPath);
				if (!tmpPathToCreate.exists())
					tmpPathToCreate.mkdirs();
				File iconPathToCreate = new File(uploadIconPath);
				if (!iconPathToCreate.exists())
					iconPathToCreate.mkdirs();
				String photoNameTmp = uploadTmpPath + uploadName;
				String photoNameIcon = uploadIconPath + uploadName;
				String photoName = fileToCreate.getAbsolutePath();
				this.copyFile(photoName, photoNameTmp);
				
				String convert = "convert " + photoNameTmp + " -resize 800x1100> " + photoName;
				ProcessCommand processCommand = new ProcessCommand();
				processCommand.runCommand(convert);
				log.info("RunCommand:" + convert);
				
				//PhotoManager photo = new PhotoManager(photoNameTmp, photoName, null);
				//photo.saveImage();

				convert = "convert " + photoNameTmp + " -resize 100x100> " + photoNameIcon;
				processCommand = new ProcessCommand();
				processCommand.runCommand(convert);
				log.info("RunCommand:" + convert);				
				
				//IconManager icon = new IconManager(photoNameTmp, photoNameIcon);
				//icon.saveImage();
				
				File photoTmpFile = new File(photoNameTmp);
				if (photoTmpFile.exists())
					photoTmpFile.delete();
			}
		} catch (IOException ie) {
			ie.printStackTrace();
			log.error(ie.getMessage());
		}
		return uploadName;
	}

	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}
}
