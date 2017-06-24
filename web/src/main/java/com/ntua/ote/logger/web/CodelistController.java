package com.ntua.ote.logger.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.ntua.ote.logger.web.common.FacesUtil;

/**
 * Contains the codelists of the application
 */
@Named
@ApplicationScoped
public class CodelistController {

	/**
	 * Gets the directions codelist.
	 *
	 * @return the directions
	 */
	public List<String> getDirections(){
		List<String> directions = new ArrayList<>();
		directions.add(FacesUtil.getMessage("codelist.incoming"));
		directions.add(FacesUtil.getMessage("codelist.outgoing"));
		return directions;
	}
	
	/**
	 * Gets the log types codelist.
	 *
	 * @return the types
	 */
	public List<String> getTypes(){
		List<String> types = new ArrayList<>();
		types.add(FacesUtil.getMessage("codelist.call"));
		types.add(FacesUtil.getMessage("codelist.sms"));
		return types;
	}
}
