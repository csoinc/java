package test.oyou.gwt.banner.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BannerTest.java,v 1.1 2008/06/29 14:24:52 oyou Exp $
 * Nov 5, 2007
 */
public class BannerTest extends GWTTestCase {
	private static final Log log = LogFactory.getLog(BannerTest.class);
	
	public String getModuleName() {
		return "org.oyosoft.gwt.banner.Banner";
	}

	public void testStuff() {
		log.debug("testStuff");
		assertTrue(2 + 2 == 4);
	}

}
