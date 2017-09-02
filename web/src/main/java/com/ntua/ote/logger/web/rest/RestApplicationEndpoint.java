package com.ntua.ote.logger.web.rest;

import java.io.File;
import java.io.IOException;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntua.ote.logger.persistence.LoggerDAO;
import com.ntua.ote.logger.persistence.jpa.Log;
import com.ntua.ote.logger.web.rest.model.AuthenticationRequest;
import com.ntua.ote.logger.web.rest.model.DurationRequest;
import com.ntua.ote.logger.web.rest.model.GeolocateResponse;
import com.ntua.ote.logger.web.rest.model.InitialRequest;
import com.ntua.ote.logger.web.rest.model.LocationRequest;
import com.ntua.ote.logger.web.rest.model.Version;
import com.ntua.ote.logger.web.service.GeolocateService;

@Stateless
@Path("/log")
public class RestApplicationEndpoint {

	private static final Logger LOGGER = Logger.getLogger(RestApplicationEndpoint.class);

	@Inject
	private LoggerDAO loggerDAO;

	/** REST Service for receiving a Log with the initial information */
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/initial/")
	public long initialLogging(InitialRequest initialRequest) {
		if (loggerDAO.login(initialRequest.getUserName(), initialRequest.getPassword())) {
			LOGGER.info("<initialLogging invoked>" + initialRequest);
			Log log = new Log();
			log.setBrandModel(initialRequest.getBrandModel());
			log.setCellId(initialRequest.getCellId());
			if (initialRequest.getDateTime() != null) {
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
		return -1;
	}

	/**
	 * REST Service for receiving the location that the Log took place. If the
	 * location is unavailable a call to Mozilla Location Services is invoked in
	 * order to retrieve an estimation of the location
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location/")
	public int locationLogging(LocationRequest locationRequest) {
		if (loggerDAO.login(locationRequest.getUserName(), locationRequest.getPassword())) {
			LOGGER.info("<locationLogging> invoked" + locationRequest);
			if (locationRequest.isLocated()) {
				return loggerDAO.updateLocation(locationRequest.getRowId(), locationRequest.getLongitude(),
						locationRequest.getLatitude());
			} else {
				Log log = loggerDAO.get(locationRequest.getRowId());
				GeolocateResponse geolocateResponse = GeolocateService.geolocate(log);
				if (geolocateResponse != null && geolocateResponse.getError() == null) {
					loggerDAO.updateLocation(locationRequest.getRowId(), geolocateResponse.getLocation().getLng(),
							geolocateResponse.getLocation().getLat(), geolocateResponse.getAccuracy());
				}
				return 1;
			}
		}
		return -1;
	}

	/** REST Service for receiving the call duration of a Log */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/duration/")
	public int durationLogging(DurationRequest durationRequest) {
		if (loggerDAO.login(durationRequest.getUserName(), durationRequest.getPassword())) {
			LOGGER.info("<durationLogging> invoked" + durationRequest);
			return loggerDAO.updateDuration(durationRequest.getRowId(), durationRequest.getDuration());
		}
		return -1;
	}

	/**
	 * REST Service for sending the number and the change log of the latest
	 * existing version
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/checkVersion/")
	public String checkVersion(AuthenticationRequest authRequest) {
		if (loggerDAO.login(authRequest.getUserName(), authRequest.getPassword())) {
			Version version = parseVersionXml();
			if (version != null) {
				LOGGER.info("<check Version> invoked" + version.getVersionNumber());
				Gson builder = new GsonBuilder().create();
				return builder.toJson(version);
			}
		}
		return null;
	}

	/** REST Service for sending the apk of the latest existing version */
	@POST
	@Path("/update/")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response update(AuthenticationRequest authRequest) {
		if (loggerDAO.login(authRequest.getUserName(), authRequest.getPassword())) {
			Version version = parseVersionXml();
			if (version != null) {
				LOGGER.info("<update> invoked" + version.getVersionNumber());
				String fileName = "logger_v" + version.getVersionNumber();
				String path = System.getProperty("SERVER_PATH") + "../update/";
				File file = new File(path + fileName);
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment; filename=" + fileName);
				return response.build();
			}
		}
		return null;
	}

	/**
	 * Parses the version.xml which contains the version number and the change
	 * log of the latest version
	 */
	private Version parseVersionXml() {
		String path = System.getProperty("SERVER_PATH") + "../update/";
		File file = new File(path + "version.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("latestVersion");
			Element latestVersion = (Element) nList.item(0);
			String versionNumber = latestVersion.getElementsByTagName("versionNumber").item(0).getTextContent();
			String changeLog = latestVersion.getElementsByTagName("changeLog").item(0).getTextContent();
			Version version = new Version();
			version.setVersionNumber(versionNumber);
			version.setChangeLog(changeLog);
			return version;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			LOGGER.error("<check Version> exception", e);
		}
		return null;
	}
}
