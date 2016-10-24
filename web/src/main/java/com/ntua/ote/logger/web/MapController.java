package com.ntua.ote.logger.web;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;

@Named
@SessionScoped
public class MapController implements Serializable {
	
	@Inject
	private LoggerDAOImpl dao;

	private SearchCriteria searchCriteria;
	
	private List<LogDetails> logs;
	
	public String init(){
		searchCriteria = new SearchCriteria();
		logs = null;
		return "mapSearch";
	}
	
	public void search(){
		logs = dao.search(searchCriteria);
	}

	public void reset(){
		logs = null;
		searchCriteria = new SearchCriteria();
	}
	
	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public List<LogDetails> getLogs() {
		return logs;
	}

	public void setLogs(List<LogDetails> logs) {
		this.logs = logs;
	}
	
}
