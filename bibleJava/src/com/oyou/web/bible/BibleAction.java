package com.oyou.web.bible;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.oyou.domain.bible.BibleService;
import com.oyou.web.struts.StrutsAction;

public abstract class BibleAction extends StrutsAction {
	private static final Log log = LogFactory.getLog(BibleAction.class);
	private static final String BEAN_BIBLE_SERVICE = "bibleService";
	private BibleService bibleService;

	public BibleService getBibleService() {
		if (bibleService == null) {
			log.debug(">>BEAN bibleService is null");
			try {
				WebApplicationContext ctx = getWebApplicationContext();
				if (ctx != null)
					if (ctx.containsBean(BEAN_BIBLE_SERVICE)) {
						Object obj = ctx.getBean(BEAN_BIBLE_SERVICE);
						if (obj != null)
							bibleService = (BibleService)obj;
					}
				if (bibleService == null) {
					log.fatal(">>Can't get bibleService from Application Context");
				} 
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Exception on get context " + e.getMessage());
			}
		}
		return bibleService;
	}

	public void setBibleService(BibleService bibleService) {
		this.bibleService = bibleService;
	}

//	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
//		return super.unspecified(mapping, form, request, response);
//    }
	
	@SuppressWarnings("rawtypes")
	protected Map getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.create", "create");
		map.put("button.update", "update");
		map.put("button.delete", "delete");
		map.put("button.search", "list");
		return map;
	}


}
