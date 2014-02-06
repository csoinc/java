package test.oyou.domain.blog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.spring.SpringService;
import com.oyou.common.test.CactusTest;
import com.oyou.domain.blog.BlogService;
import com.oyou.domain.blog.CommonService;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;
import com.oyou.web.blog.util.AlbumHelper;

public class BlogAlbumTest extends CactusTest {
	private static final Log log = LogFactory.getLog(BlogAlbumTest.class);
	protected static final String IMAGE_ORIGINAL_RELATIVE_PATH = "org";
	protected static final String IMAGE_ICON_RELATIVE_PATH = "icon";
	private BlogService blogService = null;
	private UserService userService = null;
	private CommonService commonService = null;

	protected void setUp() throws Exception {
		super.setUp();
		blogService = (BlogService) getBean(SpringService.BLOG_SERVICE);
		userService = (UserService) getBean(SpringService.USER_SERVICE);
		commonService = (CommonService) getBean(SpringService.COMMON_SERVICE);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUploadBlogPhotos() throws Exception {
		// the path that hold the photos
		String photoPath = "C:/Documents and Settings/All Users/Documents/My Pictures/Sample Pictures";
		// the group that the upload photo to
		String groupId = "24";
		// the photo comment
		String comment = "Album photo comment\r\n";
		String mode = "U";
		BlogUser blogUser = userService.login("admin", "oy920918", "local");
		AlbumHelper.getInstance().createAlbumByPhotos(photoPath, groupId, comment, mode, blogUser, blogService, commonService,
				this.config.getServletContext());
		log.debug("==testUploadBlogPhotos");
	}

}
