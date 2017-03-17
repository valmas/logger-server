package com.ntua.ote.logger.web.rest;

import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.ntua.ote.logger.persistence.LoggerDAO;
import com.ntua.ote.logger.persistence.jpa.Log;
import com.ntua.ote.logger.web.rest.model.DurationRequest;
import com.ntua.ote.logger.web.rest.model.GeolocateResponse;
import com.ntua.ote.logger.web.rest.model.InitialRequest;
import com.ntua.ote.logger.web.rest.model.LocationRequest;
import com.ntua.ote.logger.web.service.GeolocateService;

@Stateless
@Path("/log")
@Produces("application/json")
@Consumes("application/json")
public class RestApplicationEndpoint {
	
	private static final Logger LOGGER = Logger.getLogger(RestApplicationEndpoint.class);
	
	@Inject
	private LoggerDAO loggerDAO;
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
    @Path("/initial/")
    public long initialLogging(InitialRequest initialRequest) {
		LOGGER.info("<initialLogging invoked>" + initialRequest);
		Log log = new Log();
		log.setBrandModel(initialRequest.getBrandModel());
		log.setCellId(initialRequest.getCellId());
		if(initialRequest.getDateTime() != null) {
			log.setDateTime(new Timestamp(initialRequest.getDateTime().getTime()));
		}
		log.setDirection(initialRequest.getDirection());
		log.setExtPhoneNumber(initialRequest.getExternalPhoneNumber());
		log.setImei(initialRequest.getImei());
		log.setImsi(initialRequest.getImsi());
		log.setLac(initialRequest.getLac());
		log.setLteCQI(initialRequest.getLteCqi());
		log.setLteRSRP(initialRequest.getLteRsrp());
		log.setLteRSRQ(initialRequest.getLteRsrq());
		log.setLteRSSNR(initialRequest.getLteRssnr());
		log.setPhoneNumber(initialRequest.getPhoneNumber());
		log.setRat(initialRequest.getRat());
		log.setMnc(initialRequest.getMnc());
		log.setMcc(initialRequest.getMcc());
		log.setRssi(initialRequest.getRssi());
		log.setSmsContent(initialRequest.getSmsContent());
		log.setVersion(initialRequest.getVersion());
		log.setLogType(initialRequest.getLogType());
		return loggerDAO.addLog(log);
    }
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
    @Path("/location/")
    public int locationLogging(LocationRequest locationRequest) {
		LOGGER.info("<locationLogging> invoked" + locationRequest);
		if(locationRequest.isLocated()) {
			return loggerDAO.updateLocation(locationRequest.getRowId(), locationRequest.getLongitude(), locationRequest.getLatitude());
		} else {
			/*final long id = locationRequest.getRowId();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					Log log = loggerDAO.get(id);
					GeolocateResponse geolocateResponse = GeolocateService.geolocate(log);
					if(geolocateResponse != null) {
						loggerDAO.updateLocation(id, geolocateResponse.getLocation().getLat(), geolocateResponse.getLocation().getLng(),  
							geolocateResponse.getAccuracy());
					}
				}
			}).start();*/
			Log log = loggerDAO.get(locationRequest.getRowId());
			GeolocateResponse geolocateResponse = GeolocateService.geolocate(log);
			if(geolocateResponse != null) {
				loggerDAO.updateLocation(locationRequest.getRowId(), geolocateResponse.getLocation().getLng(), geolocateResponse.getLocation().getLat(),  
					geolocateResponse.getAccuracy());
			}
			return 1;
		}
    }
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
    @Path("/duration/")
    public int durationLogging(DurationRequest durationRequest) {
		LOGGER.info("<durationLogging> invoked" + durationRequest);
		return loggerDAO.updateDuration(durationRequest.getRowId(), durationRequest.getDuration());
    }
}
