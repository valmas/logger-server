package com.ntua.ote.logger.core.models;

import java.util.Date;

public class SearchCriteria {

	private String phoneNumber;

	private String externalPhoneNumber;
	
	private Date dateFrom;
	
	private Date dateTo;
	
	private String direction;
	
	private String logType;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExternalPhoneNumber() {
		return externalPhoneNumber;
	}

	public void setExternalPhoneNumber(String externalPhoneNumber) {
		this.externalPhoneNumber = externalPhoneNumber;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}
}
