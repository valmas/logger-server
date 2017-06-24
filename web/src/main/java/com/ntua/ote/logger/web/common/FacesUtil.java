package com.ntua.ote.logger.web.common;

import java.io.IOException;
import java.io.OutputStream;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil {
	
	/** The Constant RESOURCE_BUNDLE_NAME. */
	static final String RESOURCE_BUNDLE_NAME = "bundle";
	
	/** The Constant MISSING_MESSAGE_PREFIX. */
	private static final String MISSING_MESSAGE_PREFIX = "???";
	
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
	
	/**
	 * Returns the localized message for the given resource bundle key using the
	 * locale from the current page.
	 * 
	 * @param key
	 *            the resource bundle key
	 * @return the localized message
	 */
	public static String getMessage(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, RESOURCE_BUNDLE_NAME);
		String ret = null;
		try {
			ret = bundle.getString(key);
		} catch (MissingResourceException e1) {
			ret = MISSING_MESSAGE_PREFIX + key + MISSING_MESSAGE_PREFIX;
		}

		return ret;
	}
	
	/**
	 * Download.
	 *
	 * @param fileName the file name
	 * @param content the content
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void download(String fileName, byte[] content) throws IOException {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    
	    ec.responseReset(); 
	    ec.setResponseContentType(ec.getMimeType(fileName)); 
	    ec.setResponseContentLength(content.length);
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

	    OutputStream output = ec.getResponseOutputStream();
	    output.write(content);
	    output.flush();
	    fc.responseComplete();
	}
}
