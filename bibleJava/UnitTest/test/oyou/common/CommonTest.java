package test.oyou.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.test.JunitTest;

public class CommonTest extends JunitTest {
	private static final Log log = LogFactory.getLog(CommonTest.class);

	public void testCommon() {
		this.doPageList();
		//this.doStringHelper();
	}
	

	private void doPageList() {
		log.debug("Total: " + (int)Math.ceil(236/21));
		log.debug("Total: " + (int)Math.ceil(104/10));
	
	}
	
}
