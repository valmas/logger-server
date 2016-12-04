package com.ntua.ote.logger.web;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;

@Named
@ApplicationScoped
public class WelcomeController {
	
	private List<LogDetails> logs;
	
	@Inject
	private LoggerDAOImpl dao;
	
	public void increase(){
		logs = dao.getLogDetails();
	}

	public List<LogDetails> getLogs() {
		return logs;
	}

	public void setLogs(List<LogDetails> logs) {
		this.logs = logs;
	}
	
}
