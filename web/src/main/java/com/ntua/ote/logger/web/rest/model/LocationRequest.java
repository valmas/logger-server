package com.ntua.ote.logger.web.rest.model;

import java.text.DateFormat;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntua.ote.logger.core.models.LogDetails;

public class LocationRequest {

	private static final Logger LOGGER = Logger.getLogger(LocationRequest.class);

	private long rowId;
	private double latitude;
	private double longitude;

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

	@Override
	public String toString() {
		return "LocationRequest [rowId=" + rowId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
