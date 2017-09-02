package com.ntua.ote.logger.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntua.ote.logger.persistence.jpa.Log;
import com.ntua.ote.logger.web.rest.model.CellTowers;
import com.ntua.ote.logger.web.rest.model.GeolocateRequest;
import com.ntua.ote.logger.web.rest.model.GeolocateResponse;

public class GeolocateService {
	
	private static String API = "test";
	
	private static final Logger LOGGER = Logger.getLogger(GeolocateService.class);

	/** Call MLS Database with REST Services to retrieve an estimation of the 
	 * location based on the network details of the Log */ 
	public static GeolocateResponse geolocate(Log log){
		try {

		    HttpPost request = new HttpPost("https://location.services.mozilla.com/v1/geolocate?key=" + API);
		    String json = createRequest(log);
		    StringEntity params = new StringEntity(json);
		    LOGGER.info(json);
		    request.addHeader("content-type", "application/x-www-form-urlencoded");
		    request.setEntity(params);
		    HttpClient client = HttpClientBuilder.create().build();
		    HttpResponse response = client.execute(request);
		    BufferedReader rd = new BufferedReader(
		    		new InputStreamReader(response.getEntity().getContent()));
		    
		    StringBuffer result = new StringBuffer();
		    String line = "";
		    while ((line = rd.readLine()) != null) {
		    	result.append(line);
		    }
		    rd.close();
		    Gson builder = new GsonBuilder().create();
		    GeolocateResponse geolocateResponse = builder.fromJson(result.toString(), GeolocateResponse.class);
		    LOGGER.info(result);
		    return geolocateResponse;
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return null;
		}
	}
	
	/** Creates the geolocate Request */ 
	private static String createRequest(Log log){
		GeolocateRequest geolocateRequest = new GeolocateRequest();
		CellTowers cellTowers = new CellTowers();
		geolocateRequest.setCellTowers(new  ArrayList<CellTowers>());
		geolocateRequest.getCellTowers().add(cellTowers);
		cellTowers.setCellId(log.getCellId());
		cellTowers.setLocationAreaCode(log.getLac());
		cellTowers.setMobileCountryCode(log.getMcc());
		cellTowers.setMobileNetworkCode(log.getMnc());
		if("LTE".equalsIgnoreCase(log.getRat()) || "gsm".equalsIgnoreCase(log.getRat()) || "wcdma".equalsIgnoreCase(log.getRat())) {
			cellTowers.setRadioType(log.getRat().toLowerCase());
		} else {
			cellTowers.setRadioType("wcdma");
		}
		cellTowers.setSignalStrength(log.getRssi());
		Gson builder = new GsonBuilder().create();
		return builder.toJson(geolocateRequest);
	}
}
