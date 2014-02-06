package fr.improve.struts.taglib.layout.sort;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Persian Sorting rules.
 * 
 * @author jribette
 * @author hasha(hamedshayan@gmail.com)
 */
public class PersianSortRules implements SortRules {
	/**
	 * Log
	 */
	private static final Log LOG = LogFactory.getLog(BeanComparator.class);
			
	/**
	 * Rules.
	 */
	private static RuleBasedCollator ruleBasedCollator;
	
	/**
	 * Initializer
	 */
	static {
		try {
			Collator collate = Collator.getInstance(new Locale("ar", ""));
		    String rules = ( (RuleBasedCollator) collate ).getRules();      
		    String newRules = "";
		    for( int i = 0 ; i < rules.length() ; i++ )
		       {                 
		         newRules += rules.substring( i , i + 1 );
		         if ( rules.charAt( i ) == 'ک' )
		         {         
		           newRules = newRules.substring( 0 , newRules.length() - 1 );          
		           newRules += "ك";
		         }
		         if ( rules.charAt( i ) == 'ك' )
		         {         
		           newRules = newRules.substring( 0 , newRules.length() - 1 );          
		           newRules += "ک";
		         }
		        
		         if ( rules.charAt( i ) == 'و' )
		         {
		           newRules = newRules.substring( 0 , newRules.length() - 1);
		           newRules += "ه";
		         }
		         if ( rules.charAt( i ) == 'ه' )
		         {
		           newRules = newRules.substring( 0 , newRules.length () - 1);
		           newRules += "و";
		         }
		       }	      
		       rules = newRules;
		       ruleBasedCollator = new RuleBasedCollator( rules );
		} catch (Exception e) {
			LOG.error("Could not initialize ParsianSortingRules", e);
		}
	}
	       
	public Collator getRules() {
	       return ruleBasedCollator;		
	}

}
