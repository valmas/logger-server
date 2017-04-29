package com.ntua.ote.logger.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ntua.ote.logger.core.common.UserSessionBean;
import com.ntua.ote.logger.persistence.LoggerDAO;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;
import com.ntua.ote.logger.web.common.FacesUtil;

@Named
@SessionScoped
public class LoginController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userName;
	
	private String password;
	
	@Inject
	private UserSessionBean userSessionBean;
	
	@Inject
	private LoggerDAO loggerDAOImpl;
	
	public String login(){
		if(loggerDAOImpl.login(userName, password)) {
			userSessionBean.setAuthenticated(true);
			userSessionBean.setUserName(userName);
			return "home";
		} else {
			FacesUtil.addErrorMessage(FacesUtil.getMessage("error.authentication"), null, false);
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
