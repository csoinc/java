package test.oyou.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oyou.common.test.JunitTest;
import com.oyou.common.util.HTMLHelper;
import com.oyou.common.util.StringHelper;

public class CommonTest extends JunitTest {
	private static final Log log = LogFactory.getLog(CommonTest.class);

	public void testCommon() {
		this.doHTMLHelper();
		//this.doStringHelper();
	}
	
	private void doStringHelper() {
		String input = "<Script type=\"text/javascript\"><br>var i =1;alert(\"run me!!!\");</Script>try hahaha one...<br/>";
		log.debug("Input: " + input);
		String output = StringHelper.filterXSS(input);
		log.debug("Output: " + output);

		input = "<script type=\"text/javascript\"><br>var i =1;alert(\"run me!!!\");</script>try hahaha two...<br/>";
		log.debug("Input: " + input);
		output = StringHelper.filterXSS(input);
		log.debug("Output: " + output);

		input = "<sCriPt type=\"text/javascript\"><br>var i =1;alert(\"run me!!!\");</scRipT>try hahaha three...<br/>";
		log.debug("Input: " + input);
		output = StringHelper.filterXSS(input);
		log.debug("Output: " + output);
		
	}

	private void doHTMLHelper() {
		String input = "<ul><li>one<li>two</ul>";
		log.debug("Input: " + input);
		String output = HTMLHelper.formatToHTML(input);
		log.debug("Output: " + output);

		input = ".One\r .Two \r";
		log.debug("Input: " + input);
		output = HTMLHelper.formatToHTML(input);
		log.debug("Output: " + output);
	
	}
	
}
