package com.ntua.ote.logger.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.ntua.ote.logger.core.common.Constants;
import com.ntua.ote.logger.core.common.Utils;
import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;
import com.ntua.ote.logger.web.common.FacesUtil;
import com.ntua.ote.logger.web.common.LocaleManager;
import com.ntua.ote.logger.web.xls.XLSBuilder;
import com.ntua.ote.logger.web.xls.XLSMappings;

@Named
@SessionScoped
public class MapController implements Serializable {
	
	private static final long serialVersionUID = 2208902766742258519L;
	
	private static final Logger LOGGER = Logger.getLogger(MapController.class);

	@Inject
	private LoggerDAOImpl dao;
	
	@Inject
	private LocaleManager localeManager;

	private SearchCriteria searchCriteria;
	
	private List<LogDetails> logs;
	
	private Map<Long, Marker> markersMap;
	
	private MapModel advancedModel;
	
	private Marker marker;
	
	private String radius;
	
	private Marker gridMarker;
	
	private String mapCenter;
	
	private LogDetails selectedLog;
	
	private String latitude;
	
	private String longitude;
	
	private boolean error;
	
	private boolean collapsed;
	
	public String init(){
		searchCriteria = new SearchCriteria();
		logs = null;
		mapCenter="0,0";
		advancedModel = new DefaultMapModel();
		collapsed = false;
		return "mapSearch";
	}
	
	public void search(){
		error = false;
		if(validation(searchCriteria)) {
			logs = dao.search(searchCriteria);
			boolean add = false;
			markersMap = new HashMap<>();
			advancedModel = new DefaultMapModel();
			if(!logs.isEmpty()) {
				String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
				for(LogDetails log : logs) {
					if(Utils.hasLocation(log)) {
						LatLng loc = new LatLng(log.getLatitude(), log.getLongitude());
						Marker marker = new Marker(loc, Utils.getMarkerTitle(log), log, contextPath + Constants.BLUE_MARKER_ICON);
						advancedModel.addOverlay(marker);
						markersMap.put(log.getId(), marker);
						if(!add) {
							mapCenter = "" + log.getLatitude() +"," + log.getLongitude();
							add = true;
						}
					}
				}	
			} else {
				FacesUtil.addInfoMessage(FacesUtil.getMessage("error.no.records"), null, false);
				error = true;
			}
		}
		searchCriteria.setId(0);
	}
	
	private boolean validation(SearchCriteria searchCriteria){
		if(searchCriteria.getDateFrom() != null && searchCriteria.getDateTo() != null
				&& searchCriteria.getDateFrom().after(searchCriteria.getDateTo())) {
			FacesUtil.addErrorMessage(FacesUtil.getMessage("error.date"), null, false);
			error = true;
			return false;
		}
		return true;
	}

	public void reset(){
		logs = null;
		searchCriteria = new SearchCriteria();
	}
	
	public void export(){
		try {
			byte[] xls = XLSBuilder.exportXLS(logs, XLSMappings.logDetailsMapping, XLSMappings.logDetailsHeader, localeManager.getLanguage());
			FacesUtil.download("logDetails.xls", xls);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        selectedLog = (LogDetails) marker.getData();
        latitude = "" + selectedLog.getLatitude();
        longitude = "" + selectedLog.getLongitude();
        radius = "" + selectedLog.getRadius();
    }
	
	public void selectMarker() {
		if(gridMarker != null) {
			String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
			gridMarker.setIcon(contextPath + Constants.BLUE_MARKER_ICON);
		}
        Marker marker = markersMap.get(selectedLog.getId());
		this.marker = marker;
		marker.setIcon("");
		gridMarker = marker;
		latitude = "" + selectedLog.getLatitude();
        longitude = "" + selectedLog.getLongitude();
        radius = "" + selectedLog.getRadius();
		mapCenter = marker.getLatlng().getLat()+","+marker.getLatlng().getLng();

    }
	
	public LogDetails getSelectedLog() {
		return selectedLog;
	}

	public void setSelectedLog(LogDetails selectedLog) {
		this.selectedLog = selectedLog;
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

	public MapModel getAdvancedModel() {
		return advancedModel;
	}

	public void setAdvancedModel(MapModel advancedModel) {
		this.advancedModel = advancedModel;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public String getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(String mapCenter) {
		this.mapCenter = mapCenter;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public boolean isError() {
		return error;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public boolean isCollapsed() {
		return collapsed;
	}

	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}

}
