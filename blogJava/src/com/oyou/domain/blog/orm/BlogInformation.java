package com.oyou.domain.blog.orm;

import java.sql.Timestamp;

import com.oyou.common.Entity;

/**
 * 
 * @author	Owen Ou
 * @since 	Nov 5, 2007
 * @version	$Id: BlogInformation.java,v 1.1 2008/06/29 14:36:52 oyou Exp $
 */
public class BlogInformation extends Entity {
	static final long serialVersionUID = 1;
	public static final Long ANNOUNCEMENT_ID = Long.valueOf("1");
	public static final Long HSQL_ID = Long.valueOf("2");
	public static final Long SQL_ID = Long.valueOf("3");
	public static final Long ABOUTUS_ID = Long.valueOf("4");
	public static final Long HELP_ID = Long.valueOf("5");
	public static final Long LOG_ID = Long.valueOf("6");
	public static final String ANNOUNCEMENT_TYPE = "Announcement";
	public static final String HSQL_TYPE = "HSQL";
	public static final String SQL_TYPE = "SQL";
	public static final String ABOUTUS_TYPE = "AboutUS";
	public static final String HELP_TYPE = "Help";
	public static final String LOG_TYPE = "Log";

	private String informationType;
	private String information;
	private Timestamp updateTime; 
	private boolean status;

	public String getInformation() {
		return information;
	}

	public String getInformationType() {
		return informationType;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}




}
