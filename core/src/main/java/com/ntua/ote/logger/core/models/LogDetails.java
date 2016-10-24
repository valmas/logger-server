package com.ntua.ote.logger.core.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class LogDetails {
	
	private static final Logger LOGGER = Logger.getLogger(LogDetails.class);

	private String phoneNumber;
	
	private String externalPhoneNumber;
	
	private int duration;
	
	private Date dateTime;
	
	private String smsContent;
	
	private double latitude;
	
	private double longitude;

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
