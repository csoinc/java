package test.oyou.web.blog;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.spring.SpringService;
import com.oyou.common.test.CactusTest;
import com.oyou.domain.blog.BlogDAO;
import com.oyou.domain.blog.BlogService;

public class BlogTest extends CactusTest {
	private static final Log log = LogFactory.getLog(BlogTest.class);

	BlogService service;
	BlogDAO dao;

	protected void setUp() throws Exception {
        super.setUp();
        service = (BlogService)getBean(SpringService.BLOG_SERVICE);
        //dao = service.getBibleDAO();
        dao = (BlogDAO)getBean(SpringService.BLOG_DAO);
    }

	protected void tearDown() throws Exception {
        super.tearDown();
    }

	public void testGetBlogGroups() throws Exception {
		List list = service.listBlogGroups();
		log.debug("Get groups " + list.size());
		
	}

	public void testGetBlogMessagePhotos() throws Exception {
		List list = service.getBlogMessagePhotos("", "", "");
		//List list = dao.getBlogMessagePhotos("", "");
		log.debug("Get photos " + list.size());
		list = service.getBlogMessagePhotos("0", "", "");
		log.debug("Get photos " + list.size());
		list = service.getBlogMessagePhotos("", "1", "");
		log.debug("Get photos " + list.size());
		
	}

	public void testGetBlogReplyMessagePhotos() throws Exception {
		List list = service.getBlogReplyMessagePhotos("", "", "");
		//List list = dao.getBlogMessagePhotos("", "");
		log.debug("Get reply photos " + list.size());
		list = service.getBlogReplyMessagePhotos("0", "", "");
		log.debug("Get reply photos " + list.size());
		list = service.getBlogReplyMessagePhotos("", "1", "");
		log.debug("Get reply photos " + list.size());
		
	}

	
}
