/*
 * Created on 18 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.improve.struts.taglib.layout.collection.header;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.event.AbstractLayoutEvent;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * @author dev
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CollectionItemEvent extends AbstractLayoutEvent {
	private int level = 0;
	public CollectionItemEvent(LayoutTag in_tag, MultiLevelHeader in_header) {
		super(in_tag, in_header);	
	}

	public Object send() throws JspException {
		return sendToParent(source);
	}
	
	public Object sendToParent(LayoutTag in_tag) throws JspException {
		MultiLevelTitleHandler lc_listener = (MultiLevelTitleHandler) ParentFinder.findLayoutTag(in_tag, MultiLevelTitleHandler.class);
		if (lc_listener!=null) {
			return lc_listener.addCollectionTitle(this);
		} else {
			return value;
		}
	}
}
