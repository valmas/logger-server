package com.ntua.ote.logger.web.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
		    Gson builder = new GsonBuilder().create();
		    GeolocateResponse geolocateResponse = builder.fromJson(result.toString(), GeolocateResponse.class);
		    LOGGER.info(result);
		    return geolocateResponse;
		}catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			return null;
		}
	}
	
	private static String createRequest(Log log){
		GeolocateRequest geolocateRequest = new GeolocateRequest();
		CellTowers cellTowers = new CellTowers();
		geolocateRequest.setCellTowers(new  ArrayList<CellTowers>());
		geolocateRequest.getCellTowers().add(cellTowers);
		//cellTowers.setAge(1);
		cellTowers.setCellId(log.getCellId());
		cellTowers.setLocationAreaCode(log.getLac());
		cellTowers.setMobileCountryCode(log.getMcc());
		cellTowers.setMobileNetworkCode(log.getMnc());
		//cellTowers.setPsc(123);
		if("LTE".equalsIgnoreCase(log.getRat()) || "gsm".equalsIgnoreCase(log.getRat()) || "wcdma".equalsIgnoreCase(log.getRat())) {
			cellTowers.setRadioType(log.getRat());
		} else {
			cellTowers.setRadioType("wcdma");
		}
		cellTowers.setSignalStrength(log.getRssi());
		//cellTowers.setTimingAdvance(4);
		Gson builder = new GsonBuilder().create();
		return builder.toJson(geolocateRequest);
	}
}
