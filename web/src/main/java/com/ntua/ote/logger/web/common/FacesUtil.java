package com.ntua.ote.logger.web.common;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtil {
	
	/**
	 * Optional required validator.
	 *
	 * @return true, if successful
	 */
	public static boolean optionalRequiredValidator() {
		return ValidatorUtil.check();
	}
	
	/**
	 * Adds an ERROR severity message to the current FacesContext. The messages
	 * can be displayed on the page using an h:messages or rich:messages type
	 * component.
	 *
	 * @param summary            the message summary
	 * @param detail            the message detail
	 * @param keepMessage the keep message
	 */
	public static void addErrorMessage(String summary, String detail, boolean keepMessage) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keepMessage);
	}
	
	/**
	 * Adds the info message.
	 *
	 * @param summary the summary
	 * @param detail the detail
	 * @param keepMessage the keep message
	 */
	public static void addInfoMessage(String summary, String detail, boolean keepMessage) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(keepMessage);
	}
}
