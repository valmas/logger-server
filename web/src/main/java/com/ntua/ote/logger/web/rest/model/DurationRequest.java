package com.ntua.ote.logger.web.rest.model;

public class DurationRequest extends AuthenticationRequest {

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
