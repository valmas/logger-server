package com.ntua.ote.logger.web.rest;

import java.io.File;
import java.sql.Timestamp;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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
public class RestApplicationEndpoint {
	
	private static final Logger LOGGER = Logger.getLogger(RestApplicationEndpoint.class);
	
	@Inject
	private LoggerDAO loggerDAO;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
			if(geolocateResponse != null && geolocateResponse.getError() == null) {
				loggerDAO.updateLocation(locationRequest.getRowId(), geolocateResponse.getLocation().getLng(), geolocateResponse.getLocation().getLat(),  
					geolocateResponse.getAccuracy());
			}
			return 1;
		}
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/duration/")
    public int durationLogging(DurationRequest durationRequest) {
		LOGGER.info("<durationLogging> invoked" + durationRequest);
		return loggerDAO.updateDuration(durationRequest.getRowId(), durationRequest.getDuration());
    }
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
    @Path("/checkVersion/")
    public String checkVersion() {
		String path = System.getProperty("SERVER_PATH") + "../update/";
		File[] files = new File(path).listFiles();
		String tmp;
		String fileName = "";
		for(File apk : files) {
			tmp = apk.getName();
			if(tmp.compareTo(fileName) > 0) {
				fileName = tmp;
			}
		}
		LOGGER.info("<check Version> invoked" + fileName);
		return fileName;
    }
	
	@GET
    @Path("/update/")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response update() {
		String fileName = checkVersion();
		String path = System.getProperty("SERVER_PATH") + "../update/";
	    File file = new File(path + fileName);
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition", "attachment; filename=" + fileName);
	    return response.build();

	}
}
