package com.ntua.ote.logger.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.ntua.ote.logger.web.common.FacesUtil;

@Named
@ApplicationScoped
public class CodelistController {
	
	private List<String> directions;
	
	private List<String> types;
	
	
	@PostConstruct
	public void init(){
		directions = new ArrayList<>();
		directions.add(FacesUtil.getMessage("codelist.incoming"));
		directions.add(FacesUtil.getMessage("codelist.outgoing"));
		
		types = new ArrayList<>();
		types.add(FacesUtil.getMessage("codelist.call"));
		types.add(FacesUtil.getMessage("codelist.sms"));
	}

	public List<String> getDirections(){
		return directions;
	}
	
	public List<String> getTypes(){
		return types;
	}
}
