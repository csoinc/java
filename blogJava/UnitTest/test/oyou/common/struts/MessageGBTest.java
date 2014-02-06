package test.oyou.common.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.struts.MessagesFactory;
import com.oyou.common.test.CactusTest;

public class MessageGBTest extends CactusTest {
	private static final Log log = LogFactory.getLog(MessageGBTest.class);
	public String MESSAGE_FILE = "/Resource/ApplicationResources_zh_CN.prop";

	public void testMesssagesFactory() {
		MessagesFactory.getInstance();
		log.debug(MessagesFactory.getInstance().getProperty("GB2312", "menu.home"));		
	}	

	
}
