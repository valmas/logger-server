package com.ntua.ote.logger.core.common;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.ntua.ote.logger.core.models.LogDetails;

@Named
@SessionScoped
public class UserSessionBean implements Serializable {

	private static final long serialVersionUID = 2850345264338634074L;

	private boolean authenticated;
	
	private String userName;
	
	private LogDetails selectedLog;
	
	public LogDetails getSelectedLog() {
		return selectedLog;
	}

	public void setSelectedLog(LogDetails selectedLog) {
		this.selectedLog = selectedLog;
	}	

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
