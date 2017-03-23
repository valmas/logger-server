package com.ntua.ote.logger.web;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;

@Named
@ApplicationScoped
public class WelcomeController {
	
	private List<LogDetails> logs;
	
	@Inject
	private LoggerDAOImpl dao;
	
	@Inject
	private MapController mapController;
	
	private static final Logger LOGGER = Logger.getLogger(WelcomeController.class);
	
	public void init(){
		logs = dao.getLogDetails(15);
	}
	
	public void refresh(){
		logs = dao.getLogDetails(15);
	}
	
	public String goToMap(LogDetails selectedLog){
		mapController.init();		
		SearchCriteria criteria = new SearchCriteria();
		criteria.setId(selectedLog.getId());
		mapController.setSearchCriteria(criteria);
		mapController.search();
		mapController.setCollapsed(true);
		return "mapSearch";
	}

	public List<LogDetails> getLogs() {
		return logs;
	}

	public void setLogs(List<LogDetails> logs) {
		this.logs = logs;
	}

}
