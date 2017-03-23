package com.ntua.ote.logger.web.common;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * A custom validator that skips the JSF validation
 * for specific actions
 */
public final class ValidatorUtil {

	/** The Constant DO_VALIDATE. */
	private static final String DO_VALIDATE = "doValidateAnd";
	
	/** The Constant VALIDATE. */
	private static final String VALIDATE = "VALIDATE";

	/**
	 * Gets the context from the current instance and invokes
	 * check(FacesContext context)
	 *
	 * @return the result of check(FacesContext context)
	 */
	public static boolean check() {
		return check(FacesContext.getCurrentInstance());
	}

	/**
	 * Finds the id of the button that triggered the submission of the form
	 * If the id contains "doValidateAnd" then triggers the JSF validation
	 * else the JSF validation is skipped .
	 *
	 * @param context the context
	 * @return true, if the JSF validation should be triggered or false if it should be skipped
	 */
	public static boolean check(final FacesContext context) {
		final ExternalContext externalContext = context.getExternalContext();
		final Object validate = externalContext.getRequestMap().get(VALIDATE);
		if (validate != null) {
			return (Boolean) validate;
		}
		for (final Map.Entry<String, String[]> requestParameters : externalContext.getRequestParameterValuesMap().entrySet()) {
			final String key = requestParameters.getKey();
			if (key.contains(DO_VALIDATE)) {
				externalContext.getRequestMap().put(VALIDATE, Boolean.TRUE);
				return true;
			}
		}
		externalContext.getRequestMap().put(VALIDATE, Boolean.FALSE);
		return false;
	}

}