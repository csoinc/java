package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

/**
 * 
 * @author	Owen Ou
 * @version $Id: BlogCity.java,v 1.1 2008/06/29 14:36:47 oyou Exp $
 * @since Nov 20, 2007
 */
public class BlogCity extends Entity {
	static final long serialVersionUID = 1;

	private String region;
	private String countryCode;
	private String countryEnglish;
	private String countryFrench;
	private String provinceCode;
	private String provinceEnglish;
	private String provinceFrench;
	private String longNameEnglish;
	private String longNameFrench;
	private String locationId;  
	private boolean status;
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryEnglish() {
		return countryEnglish;
	}
	public void setCountryEnglish(String countryEnglish) {
		this.countryEnglish = countryEnglish;
	}
	public String getCountryFrench() {
		return countryFrench;
	}
	public void setCountryFrench(String countryFrench) {
		this.countryFrench = countryFrench;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLongNameEnglish() {
		return longNameEnglish;
	}
	public void setLongNameEnglish(String longNameEnglish) {
		this.longNameEnglish = longNameEnglish;
	}
	public String getLongNameFrench() {
		return longNameFrench;
	}
	public void setLongNameFrench(String longNameFrench) {
		this.longNameFrench = longNameFrench;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvinceEnglish() {
		return provinceEnglish;
	}
	public void setProvinceEnglish(String provinceEnglish) {
		this.provinceEnglish = provinceEnglish;
	}
	public String getProvinceFrench() {
		return provinceFrench;
	}
	public void setProvinceFrench(String provinceFrench) {
		this.provinceFrench = provinceFrench;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

}
