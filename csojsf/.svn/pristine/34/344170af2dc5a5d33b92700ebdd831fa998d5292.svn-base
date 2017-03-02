package test.cso.jsf2spring3.util;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cso.jsf2spring3.util.ImageText;

public class ImageTextTest extends TestCase {
    private static final Log log = LogFactory.getLog(ImageTextTest.class);

    public static final String TEST_TEXT_1 = "c:/temp/text_pic_1.jpg";

    private static final String text = "Many people believe that Vincent van Gogh painted his best works "
            + "during the two-year period he spent in Provence. Here is where he "
            + "painted The Starry Night--which some consider to be his greatest "
            + "work of all. However, as his artistic brilliance reached new "
            + "heights in Provence, his physical and mental health plummeted. ";

    @Override
    public void setUp() {
    }

    public void testImageText() {
        log.debug(">>testImageText()");
        ImageText it = new ImageText(text, 500, 60);
        
        it.saveImageFile(TEST_TEXT_1);

        log.debug("<<testImageText()");
    }

}
