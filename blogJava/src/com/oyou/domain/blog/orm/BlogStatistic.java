package com.oyou.domain.blog.orm;

import com.oyou.common.Entity;

/**
 * 
 * @author	Owen Ou
 * May 29, 2007
 */
abstract public class BlogStatistic extends Entity {
	static final long serialVersionUID = 1;
	public static String MESSAGE_TABLE = "Message";
	public static String REPLY_MESSAGE_TABLE = "ReplyMessage";
	public static String USER_TABLE = "User";
	private Long statisticId;
	private String statisticTable;
	private long viewTimes;
	private long updateTimes;
	private boolean status;

	public Long getStatisticId() {
		return statisticId;
	}

	public String getStatisticTable() {
		return statisticTable;
	}

	public long getUpdateTimes() {
		return updateTimes;
	}

	public long getViewTimes() {
		return viewTimes;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatisticId(Long statisticId) {
		this.statisticId = statisticId;
	}

	public void setStatisticTable(String statisticTable) {
		this.statisticTable = statisticTable;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setUpdateTimes(long updateTimes) {
		this.updateTimes = updateTimes;
	}

	public void setViewTimes(long viewTimes) {
		this.viewTimes = viewTimes;
	}
}
