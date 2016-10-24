package com.ntua.ote.logger.web.ws;

import java.util.Date;

public class InitialLoggingRequest {

private String phoneNumber;
	
	private String externalPhoneNumber;
	
	private Date dateTime;
	
	private String smsContent;

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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}
