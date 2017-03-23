package com.ntua.ote.logger.web.rest.model;

public class CellTowers {

	private String radioType;
	
	private int mobileCountryCode;
	
	private int mobileNetworkCode;
	
	private int locationAreaCode;
	
	private int cellId;
	
	private Integer age;
	
	private Integer psc;
	
	private int signalStrength;
	
	private Integer timingAdvance;

	public String getRadioType() {
		return radioType;
	}

	public void setRadioType(String radioType) {
		this.radioType = radioType;
	}

	public int getMobileCountryCode() {
		return mobileCountryCode;
	}

	public void setMobileCountryCode(int mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}

	public int getMobileNetworkCode() {
		return mobileNetworkCode;
	}

	public void setMobileNetworkCode(int mobileNetworkCode) {
		this.mobileNetworkCode = mobileNetworkCode;
	}

	public int getLocationAreaCode() {
		return locationAreaCode;
	}

	public void setLocationAreaCode(int locationAreaCode) {
		this.locationAreaCode = locationAreaCode;
	}

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getPsc() {
		return psc;
	}

	public void setPsc(int psc) {
		this.psc = psc;
	}

	public int getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	public int getTimingAdvance() {
		return timingAdvance;
	}

	public void setTimingAdvance(int timingAdvance) {
		this.timingAdvance = timingAdvance;
	}
	
}
