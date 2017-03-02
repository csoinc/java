package test.cso.spring2.common;

import com.cso.spring2.common.CollectionsHelper;
import com.cso.spring2.common.CollectionsUtil;

import junit.framework.TestCase;


public class CollectionsTests extends TestCase {

	public void testProcess() {
		CollectionsHelper oo = new CollectionsHelper();
		oo.process();
	}
	
	
	public void testDups() {
		CollectionsUtil ooc = new CollectionsUtil();
		ooc.findDups(new String[]{"a","b","c","a"});
	}
}
