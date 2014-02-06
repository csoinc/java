package fr.improve.struts.taglib.layout.workflow;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.config.ForwardConfig;
import org.apache.struts.tiles.TilesRequestProcessor;

import fr.improve.struts.taglib.layout.sort.SortUtil;
import fr.improve.struts.taglib.layout.treeview.TreeViewReconciler;

/**
 * This is the struts-layout request processor for Struts.
 * 
 * The processor make a cache of the datas in the request scope 
 * so that the page can be redisplayed without invoking the application.
 * 
 * This processor is compatible with the Tiles processor.
 * 
 * @author jnribette
 */
public class LayoutRequestProcessor extends TilesRequestProcessor {
	public static final String CONTEXT_KEY = "fr.improve.struts.taglib.layout.workflow.LayoutRequestProcessor.CONTEXT_KEY";
	private static final String CONTEXT_REQUIRED = "fr.improve.struts.taglib.layout.workflow.LayoutRequestProcessor.CONTEXT_REQUIRED";
	
    protected void processForwardConfig(HttpServletRequest request,
                                        HttpServletResponse response,
                                        ForwardConfig forward)
        throws IOException, ServletException {
      	// Required by struts contract
    	if (forward != null) {
    		// Reconcile open/close treeview.
    		TreeViewReconciler.reconceileFromMenu(request, response);
    		    		
	    	// Process the forward.
		    super.processForwardConfig(request, response, forward);
		    
		    HttpSession lc_session = request.getSession(false);
			if (lc_session!=null) {
			    SortUtil lc_sortUtil = (SortUtil) lc_session.getAttribute(SortUtil.SORTUTIL_KEY);
			    if (lc_sortUtil!=null && "aaa".equals(request.getAttribute(SortUtil.SORTUTIL_KEY + "aaa"))) {
			    	lc_sortUtil.initSortUtil(request, forward.getPath());
			    }
			}
	    }
	    	    	    	    	    
	    // Update the layout context.
	    //updateContext(request, forward.getPath());
	}
}
