package com.oyou.web.bible;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;

import com.oyou.domain.bible.BibleService;

import fr.improve.struts.taglib.layout.suggest.SuggestAction;

public abstract class BibleSuggestAction extends SuggestAction {
	private static final Log log = LogFactory.getLog(BibleSuggestAction.class);
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


}
