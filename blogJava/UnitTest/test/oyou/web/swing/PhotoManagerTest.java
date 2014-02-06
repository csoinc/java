package test.oyou.web.swing;

import java.io.File;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.test.JunitTest;
import com.oyou.web.image.IconManager;
import com.oyou.web.image.PhotoManager;

public class PhotoManagerTest extends JunitTest {
	private static final Log log = LogFactory.getLog(PhotoManagerTest.class);
	public String MESSAGE_FILE = "/Resource/ApplicationResources_zh_CN.prop";

	public void ntestPhotoComponent() {
		long startTime = Calendar.getInstance().getTimeInMillis();
		try {
			String uploadFile = "C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures\\Sunset.jpg";
			File file = new File(uploadFile);
			if (file.exists()) {
				String uploadName = file.getAbsolutePath();
				String photoName = "D:\\Development\\SunsetA.jpg";
				String iconName = "D:\\Development\\icon\\SunsetA.jpg";
				//PhotoJFrame photo = new PhotoJFrame(fileBytes, photoName, null);
				PhotoManager photo = new PhotoManager(uploadName, photoName, null, true, 700);
				//photoComponent.setPhotoName("C:\\Documents and Settings\\All Users\\Documents\\My Pictures\\Sample Pictures\\Sunset.jpg");
				//photoComponent.start();
				photo.saveImage();
				//IconJFrame icon = new IconJFrame(uploadName, iconName, true, 100);
				IconManager icon = new IconManager(uploadName, iconName);
				icon.saveImage();
				long endTime = Calendar.getInstance().getTimeInMillis();
				log.debug("Time cost = " + (endTime - startTime));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void testPhotoToIcon() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			String iconPath = "C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/mcbc2web/UPLOAD-INF/2007/image/icon";
			File dir = new File(iconPath);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					log.debug("==file: " + file.getAbsolutePath());
					if (file.exists()) {
						String uploadName = file.getAbsolutePath();
						String iconName = uploadName;
						IconManager icon = new IconManager(uploadName, iconName);
						icon.saveImage();
					}
				}
			}
			long endTime = Calendar.getInstance().getTimeInMillis();
			log.debug("Time cost = " + (endTime - startTime));
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
