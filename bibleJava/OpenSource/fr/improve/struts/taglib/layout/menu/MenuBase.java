/*
 * MenuBase.java
 *
 * Created on January 28, 2001, 7:28 PM
 */

package fr.improve.struts.taglib.layout.menu;

/*
 * MenuBase.java
 *
 * Created on January 28, 2001, 7:28 PM
 */

/**
 * MenuBase, from Struts-Menu.
 *
 * @author  ssayles
 * @version 
 */
public abstract class MenuBase {

	/** Holds value of property name. */
	protected String name;
	/** Holds value of property title. */
	protected String title;
	/** Holds value of property location. */
	protected String location;
	/** Holds value of property target. */
	protected String target;
	/** Holds value of property description. */
	protected String description;
	/** Holds value of property onClick. */
	protected String onClick;
	/** Holds value of property onMouseOver. */
	protected String onMouseOver;
	/** Holds value of property onMouseOut. */
	protected String onMouseOut;
	/** Holds value of property image. */
	private String image;
	/** Holds value of property altImage. */
	private String altImage;
    
	/** Holds value of property toolTip. */
	private String toolTip;
	
	/** Holds value of property page */
	protected String page;
	
	/** Holds value of the property action */
	protected String action;
	
	/** Holds value of property forward */
	protected String forward;
	
	/** Holds value of property style */
	protected String style;
	
	protected String id;
	
	/** Creates new MenuBase */
	public MenuBase() {
	}
	
	public MenuBase(String in_id) {
		id = in_id;
	}
    

	/** Getter for property name.
	 * @return Value of property name.
	 */
	public String getName() {
		return name;
	}
	/** Setter for property name.
	 * @param name New value of property name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/** Getter for property title.
	 * @return Value of property title.
	 */
	public String getTitle() {
		return title;
	}
	/** Setter for property title.
	 * @param title New value of property title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/** Getter for property location.
	 * @return Value of property location.
	 */
	public String getLocation() {
		return location;
	}
	/** Setter for property location.
	 * @param location New value of property location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/** Getter for property target.
	 * @return Value of property target.
	 */
	public String getTarget() {
		return target;
	}
	/** Setter for property target.
	 * @param target New value of property target.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/** Getter for property description.
	 * @return Value of property description.
	 */
	public String getDescription() {
		return description;
	}
	/** Setter for property description.
	 * @param description New value of property description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/** Getter for property onClick.
	 * @return Value of property onClick.
	 */
	public String getOnClick() {
		return onClick;
	}
	/** Setter for property onClick.
	 * @param onClick New value of property onClick.
	 */
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	/** Getter for property onMouseOver.
	 * @return Value of property onMouseOver.
	 */
	public String getOnMouseOver() {
		return onMouseOver;
	}
	/** Setter for property onMouseOver.
	 * @param onMouseOver New value of property onMouseOver.
	 */
	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}
    
	/** Getter for property onMouseOut.
	 * @return Value of property onMouseOut.
	 */
	public String getOnMouseOut() {
		return onMouseOut;
	}
	/** Setter for property onMouseOut.
	 * @param onMouseOut New value of property onMouseOut.
	 */
	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}
    
	/** Getter for property image.
	 * @return Value of property image.
	 */
	public String getImage() {
		return image;
	}
	/** Setter for property image.
	 * @param image New value of property image.
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/** Getter for property altImage.
	 * @return Value of property altImage.
	 */
	public String getAltImage() {
		return altImage;
	}
	/** Setter for property altImage.
	 * @param altImage New value of property altImage.
	 */
	public void setAltImage(String altImage) {
		this.altImage = altImage;
	}
	/** Getter for property toolTip.
	 * @return Value of property toolTip.
	 */
	public String getToolTip() {
		return toolTip;
	}
	/** Setter for property toolTip.
	 * @param toolTip New value of property toolTip.
	 */
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	/**
	 * Returns the forward.
	 * @return String
	 */
	public String getForward() {
		return forward;
	}

	/**
	 * Returns the page.
	 * @return String
	 */
	public String getPage() {
		return page;
	}

	/**
	 * Sets the forward.
	 * @param forward The forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * Sets the page.
	 * @param page The page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String in_string) {
		style = in_string;
	}

	public String getId() {
		return id;
	}
	
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}
}