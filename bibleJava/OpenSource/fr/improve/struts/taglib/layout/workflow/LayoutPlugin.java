package fr.improve.struts.taglib.layout.workflow;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import fr.improve.struts.taglib.layout.skin.Skin;

/**
 * Layout plugin for struts 1.1.
 * 
 * The plugin only allows to specify a different configuration file.
 * People still using struts 1.0 can subclass the ActionServlet and
 * call Skin.setResourcesName("the_skin_properties_file_name") in the init method.
 * 
 * @author jnribette
 */
public class LayoutPlugin implements PlugIn {
	private String skinResources = "Struts-Layout";
	
    public void destroy() {
    	// nothing to destroy	
    }
    
	public void init(ActionServlet in_servlet, ModuleConfig in_config)
		throws ServletException {
			// Set the skin properties file to use.
        	Skin.setResourcesName(skinResources);
	}
	    
    public void setSkinResources(String in_skinResources) {
    	skinResources = in_skinResources;	
    }

}
