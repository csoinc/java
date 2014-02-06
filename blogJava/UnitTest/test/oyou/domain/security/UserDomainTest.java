package test.oyou.domain.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.spring.SpringService;
import com.oyou.common.test.CactusTest;
import com.oyou.common.util.DebugHelper;
import com.oyou.domain.blog.EmailManager;
import com.oyou.domain.blog.UserService;
import com.oyou.domain.blog.orm.BlogUser;

public class UserDomainTest extends CactusTest {
	private static final Log log = LogFactory.getLog(UserDomainTest.class);

	UserService service;
	EmailManager eManager;

    private void doEmailSimpleMailMessage() {
    	BlogUser receiveUser = new BlogUser();
    	receiveUser.setEmail("ouyangqx@gmail.com");
    	receiveUser.setFirstname("Owen");
    	receiveUser.setLastname("Ou");
    	log.debug(DebugHelper.getJSONString(receiveUser));
    	BlogUser sendUser = new BlogUser();
    	sendUser.setEmail("owen.ou@rogers.com");
    	sendUser.setFirstname("Admin");
    	sendUser.setLastname("Oyou");
    	eManager.emailSimpleMailMessage(receiveUser.getEmail(), sendUser.getEmail(), "Test", "Test 123");
	}

	protected void setUp() throws Exception {
        super.setUp();
        service = (UserService)getBean(SpringService.USER_SERVICE);
        eManager = (EmailManager)getBean(SpringService.EMAIL_MANAGER);
    }

	protected void tearDown() throws Exception {
        super.tearDown();
    }

	public void testManager() {
    	this.doEmailSimpleMailMessage();
		this.doEmailMimeMessage();
    }

    private void doEmailMimeMessage() {
    	BlogUser receiveUser = new BlogUser();
    	receiveUser.setEmail("ouyangqx@gmail.com");
    	receiveUser.setFirstname("Owen");
    	receiveUser.setLastname("Ou");
    	log.debug(DebugHelper.getJSONString(receiveUser));
    	BlogUser sendUser = new BlogUser();
    	sendUser.setEmail("owen.ou@rogers.com");
    	sendUser.setFirstname("Admin");
    	sendUser.setLastname("Oyou");
    	eManager.emailMimeMessage(sendUser, receiveUser, null, "Testing MIME email", null);
	}

	
}
