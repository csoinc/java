package fr.improve.struts.taglib.layout.swap;

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.OptionsTag;
import org.apache.struts.taglib.html.SelectTag;

import fr.improve.struts.taglib.layout.ButtonTag;
import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;

/**
 * @author jnribette
 */
public class SwapTag extends LayoutTagSupport implements LayoutEventListener {
	private static final String SWAP_KEY = "fr.improve.struts.taglib.layout.swap.SwapTag.SWAP_KEY";
	private SelectTag selectTag = new SelectTag();
	private OptionsTag optionsTag = new OptionsTag();
	private ButtonTag buttonTag = new ButtonTag();
	private StringTokenizer propertyTokenizer;
	private StringTokenizer formPropertyTokenizer;
	private boolean first = true;
		
	protected String selectedStyleClass = null;
	protected int currentCollectionNumber = 0;
	protected String property = null;
	protected String formProperty = null;
			
	/**
	 * Start the tag.
	 * Generate an id for your collection.
	 */
	public int doStartLayoutTag() throws JspException {		
		Integer lc_int = (Integer) pageContext.getRequest().getAttribute(SWAP_KEY);
		if (lc_int==null) {
			currentCollectionNumber = 0;	
		} else {
			currentCollectionNumber = lc_int.intValue();
		}
		propertyTokenizer = new StringTokenizer(property, ",");
		formPropertyTokenizer = new StringTokenizer(formProperty, ",");
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * End the tag.
	 * Reset it.
	 */
	public int doEndLayoutTag() throws JspException {
		pageContext.getRequest().setAttribute(SWAP_KEY, new Integer(currentCollectionNumber));

		propertyTokenizer = null;
		formPropertyTokenizer = null;
		first = true;
		return EVAL_PAGE;
	}
	
	/**
	 * Release the tag.
	 */
	public void release() {
		super.release();
		selectTag.release();
		optionsTag.release();
		
		selectedStyleClass = null;
		property = null;
		formProperty = null;
	}
	
	/**
	 * Called by inner tag to inform this tag of what is going on.
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		LayoutTag lc_tag = in_event.getSource();
		if (lc_tag instanceof CollectionTag) {
			// Set the onRowClick and styleId of the collection.
			CollectionTag lc_collectionTag = (CollectionTag) lc_tag;
			lc_collectionTag.setOnRowClick(new StringBuffer("prepareSwap(this, '").append(selectedStyleClass).append("')").toString());
			lc_collectionTag.setStyleId("stdLayoutSwap" + currentCollectionNumber);
			if (!first) {
				doPrintSwapButtons();							
			}
		}
		return new StartLayoutEvent(lc_tag, in_event.getValue()).sendToParent(this);
	}
	/**
	 * Called by inner tag to inform this tag of what is going on.
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		LayoutTag lc_tag = in_event.getSource();	
		if (lc_tag instanceof CollectionTag) {
			CollectionTag lc_collectionTag = (CollectionTag) lc_tag;

			doPrintHiddenSelect(lc_collectionTag);				

			first = false;					
			currentCollectionNumber++;															
		}
		return new EndLayoutEvent(lc_tag, in_event.getValue()).sendToParent(this);
	}

	/**
	 * Print an hidden select tag.
	 */	
	private void doPrintHiddenSelect(CollectionTag in_collectionTag) throws JspException {
		if (!propertyTokenizer.hasMoreTokens() || !formPropertyTokenizer.hasMoreTokens()) {
			throw new JspException("<layout:swap>: Bad formProperty / property value");
		}
		
		selectTag.setPageContext(pageContext);
		selectTag.setParent(this);
		selectTag.setProperty(formPropertyTokenizer.nextToken());
		selectTag.setStyleId("stdLayoutSwapHS" + currentCollectionNumber);
		selectTag.setMultiple("true");
		selectTag.setStyle("display:none");
		selectTag.doStartTag();
					
		optionsTag.setPageContext(pageContext);
		optionsTag.setParent(this);
		optionsTag.setCollection(in_collectionTag.getName());
		optionsTag.setProperty(propertyTokenizer.nextToken());
		optionsTag.doStartTag();
		optionsTag.doEndTag();
					
		selectTag.doEndTag();		
	}
	
	/**
	 * Print two buttons to swap items betwwen the two collections.
	 */
	private void doPrintSwapButtons() throws JspException {
		buttonTag.setPageContext(pageContext);
		buttonTag.setParent(this);
		buttonTag.setValign("top");
		buttonTag.setOnclick("swap('stdLayoutSwap" + currentCollectionNumber + "', 'stdLayoutSwapHS" + currentCollectionNumber + "', 'stdLayoutSwap" + (currentCollectionNumber-1) + "', 'stdLayoutSwapHS" + (currentCollectionNumber-1) + "')");
		buttonTag.setValue("<<");
		buttonTag.doStartTag();
		buttonTag.doEndTag();

		buttonTag.setOnclick("swap('stdLayoutSwap" + (currentCollectionNumber-1) + "', 'stdLayoutSwapHS" + (currentCollectionNumber-1) + "', 'stdLayoutSwap" + currentCollectionNumber + "', 'stdLayoutSwapHS" + currentCollectionNumber + "')");		
		buttonTag.setValue(">>");
		buttonTag.doStartTag();
		buttonTag.doEndTag();		
	}

	/**
	 * Sets the selectedStyleClass.
	 * @param selectedStyleClass The selectedStyleClass to set
	 */
	public void setSelectedStyleClass(String selectedStyleClass) {
		this.selectedStyleClass = selectedStyleClass;
	}

	/**
	 * Sets the formProperty.
	 * @param formProperty The formProperty to set
	 */
	public void setFormProperty(String formProperty) {
		this.formProperty = formProperty;
	}

	/**
	 * Sets the property.
	 * @param property The property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

}
