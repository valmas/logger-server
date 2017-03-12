package com.ntua.ote.logger.web.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
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
	    
	    ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    ec.setResponseContentType(ec.getMimeType(fileName)); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
	    ec.setResponseContentLength(content.length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

	    OutputStream output = ec.getResponseOutputStream();
	    output.write(content);
	    output.flush();
	    fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
	}
}
