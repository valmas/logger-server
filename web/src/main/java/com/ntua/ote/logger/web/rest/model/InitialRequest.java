package com.ntua.ote.logger.web.rest.model;

import java.util.Date;

import com.ntua.ote.logger.core.enums.Direction;
import com.ntua.ote.logger.core.enums.LogType;

public class InitialRequest extends AuthenticationRequest {

	private String brandModel;
	
	private String version;
	
	private String imei;
	
	private String imsi;

	private String phoneNumber;

	private String externalPhoneNumber;

	private Date dateTime;

	private String smsContent;

	private Direction direction;

	private int cellId;

	private int lac;
	
	private String rat;
	
	private int mnc;
	
	private int mcc;

	private int rssi;

	private String lteRsrp;

	private String lteRsrq;

	private String lteRssnr;

	private String lteCqi;
	
	private LogType logType;

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

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public String getLteRsrp() {
		return lteRsrp;
	}

	public void setLteRsrp(String lteRsrp) {
		this.lteRsrp = lteRsrp;
	}

	public String getLteRsrq() {
		return lteRsrq;
	}

	public void setLteRsrq(String lteRsrq) {
		this.lteRsrq = lteRsrq;
	}

	public String getLteRssnr() {
		return lteRssnr;
	}

	public void setLteRssnr(String lteRssnr) {
		this.lteRssnr = lteRssnr;
	}

	public String getLteCqi() {
		return lteCqi;
	}

	public void setLteCqi(String lteCqi) {
		this.lteCqi = lteCqi;
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

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
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

	@Override
	public String toString() {
		return "InitialRequest [brandModel=" + brandModel + ", version=" + version + ", imei=" + imei + ", imsi=" + imsi
				+ ", phoneNumber=" + phoneNumber + ", externalPhoneNumber=" + externalPhoneNumber + ", dateTime="
				+ dateTime + ", smsContent=" + smsContent + ", direction=" + direction + ", cellId=" + cellId + ", lac="
				+ lac + ", rat=" + rat + ", mnc=" + mnc + ", mcc=" + mcc + ", rssi=" + rssi + ", lteRsrp=" + lteRsrp + ", lteRsrq=" + lteRsrq
				+ ", lteRssnr=" + lteRssnr + ", lteCqi=" + lteCqi + ", logType=" + logType + "]";
	}
	
}
