package com.ntua.ote.logger.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ntua.ote.logger.persistence.LoggerDAOImpl;
import com.ntua.ote.logger.web.common.FacesUtil;
import com.ntua.ote.logger.web.common.UserSessionBean;

@Named
@SessionScoped
public class LoginController implements Serializable {
	
	private String userName;
	
	private String password;
	
	@Inject
	private UserSessionBean userSessionBean;
	
	@Inject
	private LoggerDAOImpl loggerDAOImpl;
	
	public String login(){
		if(loggerDAOImpl.login(userName, password)) {
			userSessionBean.setAuthenticated(true);
			userSessionBean.setUserName(userName);
			return "home";
		} else {
			FacesUtil.addErrorMessage("Authentication failed. Please try again", null, false);
			return null;
		}
		
		
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "home";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
