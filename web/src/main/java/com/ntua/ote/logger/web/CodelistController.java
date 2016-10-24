package com.ntua.ote.logger.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class CodelistController {
	
	private List<String> directions;
	
	private List<String> types;
	
	
	@PostConstruct
	public void init(){
		directions = new ArrayList<>();
		directions.add("Incoming");
		directions.add("Outgoing");
		
		types = new ArrayList<>();
		types.add("Call");
		types.add("SMS");
	}

	public List<String> getDirections(){
		return directions;
	}
	
	public List<String> getTypes(){
		return types;
	}
}
