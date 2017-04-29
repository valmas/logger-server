package com.ntua.ote.logger.web.rest.model;

public class LocationRequest extends AuthenticationRequest {

	private long rowId;
	private double latitude;
	private double longitude;
	private boolean located;

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
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
	
	public boolean isLocated() {
		return located;
	}

	public void setLocated(boolean located) {
		this.located = located;
	}

	@Override
	public String toString() {
		return "LocationRequest [rowId=" + rowId + ", latitude=" + latitude + ", longitude=" + longitude + ", located="
				+ located + "]";
	}

}
