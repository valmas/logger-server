package com.ntua.ote.logger.persistence;

import com.ntua.ote.logger.persistence.jpa.Log;

public interface LoggerDAO {

	long addLog(Log log);
	
	int updateLocation(long id, double longitude, double latitude, double radius);
	
	int updateLocation(long id, double longitude, double latitude);
	
	int updateDuration(long id, int duration);
	
	Log get(Long id);
	
	boolean login(String userName, String password);
}
