package com.oyou.web.blog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.util.StringHelper;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.orm.BlogMessageType;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.web.image.IconManager;
import com.oyou.web.image.PhotoManager;

public class AlbumHelper {
	private static final Log log = LogFactory.getLog(AlbumHelper.class);
	public static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	public static final String IMAGE_ICON_RELATIVE_PATH = "icon";

	private static AlbumHelper albumHelper = null;
	private AlbumThread albumThread = null;
	private Thread thread = null;

	private AlbumHelper() {
	}

	public static AlbumHelper getInstance() {
		if (albumHelper == null)
			albumHelper = new AlbumHelper();
		return albumHelper;
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
	public void createAlbumByPhotos(String photoPath, String groupId, String comment, String mode, BlogUser blogUser, BlogService blogService,
			CommonService commonService, ServletContext ctx) throws Exception {
		try {
			if (thread != null && thread.isAlive()) {
				log.debug("==Processing photos thread is alive, so wait......");
				return;
			} else {
				albumThread = new AlbumThread(photoPath, groupId, comment, mode, blogUser, blogService, commonService, ctx);
				thread = new Thread(albumThread);
			}
			thread.start();
			Thread.sleep(10000);
		} catch (InterruptedException ie) {
			;
		}
	}

	/**
	 * 
	 * @param filename
	 * @param uType
	 * @param ctx
	 * @return
	 */
	public String saveUploadFile(String filename, Long uType, ServletContext ctx) {
		String uploadName = "";
		try {
			String uploadPath = this.getUploadPath(uType, ctx);
			String suffix = filename.substring(filename.lastIndexOf("."));
			log.debug(">>>>Suffix = " + suffix);
			uploadName = Long.toString(Calendar.getInstance().getTimeInMillis());
			if (StringHelper.isNotEmpty(suffix)) {
				uploadName += suffix.toLowerCase();
			}
			log.debug(">>>>Saved File Name: " + uploadName);
			File pathToCreate = new File(uploadPath);
			if (!pathToCreate.exists())
				pathToCreate.mkdirs();
			File fileToCreate = new File(uploadPath, uploadName);
			if (!fileToCreate.exists()) {
				this.copyFile(filename, fileToCreate.getAbsolutePath());
			}
			if (uType.equals(BlogMessageType.IMAGE)) {
				String uploadOrgPath = uploadPath + IMAGE_ORIGINAL_RELATIVE_PATH + File.separator;
				String uploadIconPath = uploadPath + IMAGE_ICON_RELATIVE_PATH + File.separator;
				File orgPathToCreate = new File(uploadOrgPath);
				if (!orgPathToCreate.exists())
					orgPathToCreate.mkdirs();
				File iconPathToCreate = new File(uploadIconPath);
				if (!iconPathToCreate.exists())
					iconPathToCreate.mkdirs();
				String photoNameOrg = uploadOrgPath + uploadName;
				String photoNameIcon = uploadIconPath + uploadName;
				String photoName = fileToCreate.getAbsolutePath();
				this.copyFile(photoName, photoNameOrg);
				PhotoManager photo = new PhotoManager(photoNameOrg, photoName, null);
				photo.saveImage();
				IconManager icon = new IconManager(photoNameOrg, photoNameIcon);
				icon.saveImage();
			}
		} catch (Exception ie) {
			ie.printStackTrace();
			log.error(ie.getMessage());
		}
		return uploadName;
	}

	/**
	 * 
	 * @param fromFile
	 * @param toFile
	 */
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

	/**
	 * 
	 * @param mType
	 * @param ctx
	 * @return
	 */
	public String getUploadPath(Long mType, ServletContext ctx) {
		return UploadHelper.getContextRealPath(ctx) + UploadHelper.getInstance().getUploadRelativePath(mType);
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @param ctx
	 * @return
	 */
	public String getIconUploadPath(Long mType, Timestamp updateTime, ServletContext ctx) {
		String uploadPath = this.getUploadPath(mType, updateTime, ctx);
		return uploadPath + File.separator + IMAGE_ICON_RELATIVE_PATH;
	}

	/**
	 * 
	 * @param mType
	 * @param updateTime
	 * @param ctx
	 * @return
	 */
	public String getUploadPath(Long mType, Timestamp updateTime, ServletContext ctx) {
		return UploadHelper.getContextRealPath(ctx) + UploadHelper.getInstance().getUploadRelativePath(mType, updateTime);
	}

}
