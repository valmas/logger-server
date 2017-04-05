package com.ntua.ote.logger.core.common;

import com.ntua.ote.logger.core.models.LogDetails;

public class Utils {

	public static boolean hasLocation(LogDetails log){
		if(log.getLongitude() != 0 && log.getLatitude() != 0) {
			return true;
		}
		return false;
	}
	
	public static String getMarkerTitle(LogDetails log){
		return "Phone number: " + log.getPhoneNumber() + "\n"
				+ "External phone number: " + log.getExternalPhoneNumber() + "\n"
			    + "Date time: " + log.getDateTime();
	}
	
	public static String timeToString(int time){
		int secs = time % 60;
		int mins = (time / 60) % 60;
		int hours = time / 3600;
		if(secs > 0 || mins > 0 || hours > 0) {
			return (hours > 0 ? hours + " hrs " : "") + (mins > 0 ? mins + " mins " : "") + (secs > 0 ? secs + " secs" : "");
		} else {
			return "0 secs";
		}
		
	}
	
	public static double[] calculateLatLngFromRadius(double radius, double lat, double lng){
		double[] result = new double[2];
		result[0] = (radius / 111111);
		result[1] = (radius / (111111 * Math.cos(lat)));
		return result;
	}
	
}
