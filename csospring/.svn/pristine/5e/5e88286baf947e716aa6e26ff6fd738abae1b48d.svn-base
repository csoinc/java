package test.cso.logreport;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import junit.framework.TestCase;

public class TDReportTest extends TestCase {
	protected static final Log log = LogFactory.getLog(TDReportTest.class);

	public void testDir() {

		String logPath74 = "D:/UserData/Logs74";
		String logPath75 = "D:/UserData/Logs75";
		
		String filename = "D:/UserData/Logs74/NAS.log.2014-05-04";

		File logDir = new File(logPath74);
		
		if (logDir.isDirectory()) {
			File[] files = logDir.listFiles();
			for (File file : files) {
				log.debug("Log Filename:" + file.getAbsolutePath());
			}
			
		}
	}
	
	
}
