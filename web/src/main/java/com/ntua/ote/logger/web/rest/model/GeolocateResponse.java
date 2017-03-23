package com.ntua.ote.logger.web.rest.model;

public class GeolocateResponse {

	private Location location;
	
	private double accuracy;
	
	private ErrorResponse error;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

	
}
