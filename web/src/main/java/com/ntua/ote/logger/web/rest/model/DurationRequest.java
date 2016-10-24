package com.ntua.ote.logger.web.rest.model;

import java.text.DateFormat;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntua.ote.logger.core.models.LogDetails;

public class DurationRequest {

	private static final Logger LOGGER = Logger.getLogger(DurationRequest.class);

	private long rowId;
	private int duration;

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "DurationRequest [rowId=" + rowId + ", duration=" + duration + "]";
	}

}
