package com.ntua.ote.logger.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.ntua.ote.logger.core.common.UserSessionBean;
import com.ntua.ote.logger.persistence.LoggerDAO;
import com.ntua.ote.logger.web.common.FacesUtil;

/**
 * The Class LoginController.
 * Controls the login procedure.
 */
@Named
@SessionScoped
public class LoginController implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/** The user session bean. */
	@Inject
	private UserSessionBean userSessionBean;
	
	/** The logger DAO impl. */
	@Inject
	private LoggerDAO loggerDAOImpl;
	
	/**
	 * Compare the login details username/password with
	 * the database. Redirects user to home page upon a successful login
	 *
	 * @return navigation page string
	 */
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
	
	/**
	 * Invalidates the session and redirect the user to login page
	 *
	 * @return navigation page string
	 */
	public String logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "home";
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
