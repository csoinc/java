package test.oyou.bible.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.pdf.BBEPdfFile;
import com.oyou.bible.pdf.CNBBEKJVPdfFile;
import com.oyou.bible.pdf.CNBBEPdfFile;
import com.oyou.bible.pdf.CNPdfFile;
import com.oyou.bible.pdf.KJVPdfFile;
import com.oyou.bible.pdf.PdfFile;
import com.oyou.bible.pdf.TWPdfFile;
import com.oyou.bible.util.BibleConstants;
import com.oyou.common.test.JunitTest;

public class PDFWriterTest extends JunitTest {
	private static final Log log = LogFactory.getLog(PDFWriterTest.class);

	public void ntestCreateCNBBEFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new CNBBEPdfFile();
		file.createPdfFile();
		log.debug("Great HGB-BBE! Is works!");
	}

	public void ntestCreateCNBBEKJVFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new CNBBEKJVPdfFile();
		file.createPdfFile();
		log.debug("Great HGB-BBE-KJV! Is works!");
	}
	
	public void ntestCreateCNFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new CNPdfFile();
		file.createPdfFile();
		log.debug("Great HGB! Is works!");
	}

	public void ntestCreateCNSlideShowFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new CNPdfFile();
		file.createPdfSlideShowFile();
		log.debug("Great HGB Slide Show! Is works!");
	}
	
	public void testCreateTWFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new TWPdfFile();
		file.createPdfFile();
		log.debug("Great HB5! Is works!");
	}

	public void testCreateTWSlideShowFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new TWPdfFile();
		file.createPdfSlideShowFile();
		log.debug("Great HB5 Slide Show! Is works!");
	}
	
	public void ntestCreateBBEFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new BBEPdfFile();
		file.createPdfFile();
		log.debug("Great BBE! Is works!");
	}

	public void ntestCreateBBESlideShowFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new BBEPdfFile();
		file.createPdfSlideShowFile();
		log.debug("Great BBE Slide Show! Is works!");
	}
	
	public void ntestCreateKJVFile() {
        if (BibleConstants.getInstance().getRootPath() == null) {
        	BibleConstants.getInstance().setRootPath("");
        }	
		PdfFile file = new KJVPdfFile();
		file.createPdfFile();
		log.debug("Great KJV! Is works!");
	}
	
}
