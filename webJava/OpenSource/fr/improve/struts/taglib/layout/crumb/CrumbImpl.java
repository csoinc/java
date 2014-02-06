package fr.improve.struts.taglib.layout.crumb;

public class CrumbImpl implements Crumb {
	private String target;
	private String key;
	private String link;	
	private String bundle;
	/**
	 * Gets the target.
	 * @return Returns a String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * @param target The target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Gets the key.
	 * @return Returns a String
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the key.
	 * @param key The key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets the link.
	 * @return Returns a String
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Sets the link.
	 * @param link The link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * @param bundle The bundle to set.
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

}
