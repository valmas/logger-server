package com.ntua.ote.logger.persistence.jpa;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.ntua.ote.logger.core.enums.Direction;
import com.ntua.ote.logger.core.enums.LogType;

@Entity
public class Log {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	private String brandModel;
	
	private String version;
	
	private String imei;
	
	private String imsi;	
	
	private String extPhoneNumber;
	
	private Timestamp dateTime;
	
	private int duration;
	
	private String smsContent;
	
	@Enumerated(EnumType.STRING)
	private Direction direction;
	
	private double latitude;
	
	private double longitude;
	
	private double radius;
	
	private int cellId;
	
	private int lac;
	
	private String rat;
	
	private int mnc;
	
	private int mcc;
	
	private int rssi;
	
	private String lteRSRP;
	
	private String lteRSRQ;
	
	private String lteRSSNR;
	
	private String lteCQI;
	
	@Enumerated(EnumType.STRING)
	private LogType logType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getExtPhoneNumber() {
		return extPhoneNumber;
	}

	public void setExtPhoneNumber(String extPhoneNumber) {
		this.extPhoneNumber = extPhoneNumber;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public int getCellId() {
		return cellId;
	}

	public void setCellId(int cellId) {
		this.cellId = cellId;
	}

	public int getLac() {
		return lac;
	}

	public void setLac(int lac) {
		this.lac = lac;
	}

	public String getRat() {
		return rat;
	}

	public void setRat(String rat) {
		this.rat = rat;
	}
	
	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}

	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public String getLteRSRP() {
		return lteRSRP;
	}

	public void setLteRSRP(String lteRSRP) {
		this.lteRSRP = lteRSRP;
	}

	public String getLteRSRQ() {
		return lteRSRQ;
	}

	public void setLteRSRQ(String lteRSRQ) {
		this.lteRSRQ = lteRSRQ;
	}

	public String getLteRSSNR() {
		return lteRSSNR;
	}

	public void setLteRSSNR(String lteRSSNR) {
		this.lteRSSNR = lteRSSNR;
	}

	public String getLteCQI() {
		return lteCQI;
	}

	public void setLteCQI(String lteCQI) {
		this.lteCQI = lteCQI;
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}
	
}
