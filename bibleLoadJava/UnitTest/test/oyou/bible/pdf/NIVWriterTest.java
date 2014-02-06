package test.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;
import com.oyou.bible.writer.NIVOutputFile;
import com.oyou.common.test.JunitTest;

public class NIVWriterTest extends JunitTest {
	private static final Log log = LogFactory.getLog(NIVWriterTest.class);

	public void testCreateNIVOutputFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		NIVOutputFile file = new NIVOutputFile();
		file.createNIVOutputFile();
		log.debug("Great NIV Output File! Is works!");
	}
	
}
