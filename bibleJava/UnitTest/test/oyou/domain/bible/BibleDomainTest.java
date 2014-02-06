package test.oyou.domain.bible;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.bible.util.BibleConstants;
import com.oyou.common.spring.SpringService;
import com.oyou.common.test.CactusTest;
import com.oyou.domain.bible.BibleDAO;
import com.oyou.domain.bible.BibleService;

public class BibleDomainTest extends CactusTest {
	private static final Log log = LogFactory.getLog(BibleDomainTest.class);
	BibleService bibleService;
	BibleDAO bibleDAO;

	private void doLoadBibleBook() {
		bibleService.loadBibleBook();
	}

	private void doLoadBibleBookEN() {
		bibleService.loadBibleBookEN();
	}
	
	private void doLoadBibleBookCN() throws SQLException {
		bibleService.loadBibleBookCN();
	}

	private void doLoadBibleBookTW() throws SQLException {
		bibleService.loadBibleBookTW();
	}

	private void doLoadBibleLine() {
		bibleService.loadBibleLine();
	}

	private void doLoadBibleLineEN() {
		bibleService.loadBibleLineEN();
	}
	
	private void doLoadBibleLineCN() throws SQLException {
		bibleService.loadBibleLineCN();
	}

	private void doLoadBibleLineTW() throws SQLException {
		bibleService.loadBibleLineTW();
	}

	protected void setUp() throws Exception {
		super.setUp();
		bibleService = (BibleService) getBean(SpringService.BIBLE_SERVICE);
		bibleDAO = (BibleDAO) getBean(SpringService.BIBLE_DAO);
		if (BibleConstants.getInstance().getRootPath() == null) {
			BibleConstants.getInstance().setRootPath("");
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLoadBibleBook() {
		log.debug("==loadBook()");
		try {
			this.doLoadBibleBookEN();
			//this.doLoadBibleBookCN();
			//this.doLoadBibleBookTW();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testLoadBibleLine() {
		log.debug("==loadLine()");
		try {
			this.doLoadBibleLineEN();
			//this.doLoadBibleLineCN();
			//this.doLoadBibleLineTW();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
