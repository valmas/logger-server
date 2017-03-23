package com.ntua.ote.logger.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

import com.ntua.ote.logger.core.common.Constants;
import com.ntua.ote.logger.core.common.Utils;
import com.ntua.ote.logger.core.models.LogDetails;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;
import com.ntua.ote.logger.web.common.FacesUtil;
import com.ntua.ote.logger.web.components.ManyInputSelect;

@Named
@SessionScoped
public class MapPathController implements Serializable {
	
	private static final long serialVersionUID = 2208902766742258519L;

	@Inject
	private LoggerDAOImpl dao;

	private SearchCriteria searchCriteria;
	
	private List<LogDetails> logs;
	
	private MapModel advancedModel;
	
	private Marker marker;
	
	private String mapCenter;
	
	private LogDetails selectedLog;
	
	private String latitude;
	
	private String longitude;
	
	private ManyInputSelect inputs;
	
	private boolean error;
	
	public String init(){
		inputs = new ManyInputSelect();
		searchCriteria = new SearchCriteria();
		logs = null;
		mapCenter="0,0";
		advancedModel = new DefaultMapModel();
		return "pathSearch";
	}
	
	public void search(){
		
		inputs.add();
		error = false;
		if(validation(searchCriteria)) {
			logs = new ArrayList<>();
			advancedModel = new DefaultMapModel();
			for(ManyInputSelect.ManyInputSelectItem item : inputs.getInputs()) {
				searchCriteria.setPhoneNumber(item.getInput());
				List<LogDetails> partialLogs = dao.searchPath(searchCriteria);		
				
				Polyline polyline = new Polyline();
				int start = 0;
				String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
				if(partialLogs.size() > 1) {
					start = 1;
					LogDetails startLog = partialLogs.get(0);
					LatLng loc = new LatLng(startLog.getLatitude(), startLog.getLongitude());		
					advancedModel.addOverlay(new Marker(loc, Utils.getMarkerTitle(startLog), startLog, 
							contextPath + Constants.BLUE_MARKER_START_ICON));
					
					LogDetails endLog = partialLogs.get(partialLogs.size() - 1);
					loc = new LatLng(endLog.getLatitude(), endLog.getLongitude());		
					advancedModel.addOverlay(new Marker(loc, Utils.getMarkerTitle(endLog), endLog, 
							contextPath + Constants.BLUE_MARKER_END_ICON));
					
				}
				for(int i = start; i < partialLogs.size() - start; i++) {
					LogDetails log = partialLogs.get(i);
					LatLng loc = new LatLng(log.getLatitude(), log.getLongitude());	
					advancedModel.addOverlay(new Marker(loc, Utils.getMarkerTitle(log), log, 
							contextPath + Constants.BLUE_MARKER_ICON));
				}
				
				if(partialLogs.size() > 1) {
					for(LogDetails log : partialLogs) {
						LatLng loc = new LatLng(log.getLatitude(), log.getLongitude());		
				        polyline.getPaths().add(loc);
				        polyline.setStrokeWeight(10);
				        polyline.setStrokeColor(item.getColor());
				        polyline.setStrokeOpacity(0.7);
				        
						advancedModel.addOverlay(polyline);
					}
				}
				logs.addAll(partialLogs);
			}
			if(!logs.isEmpty()) {
				mapCenter = logs.get(0).getLatitude() + "," + logs.get(0).getLongitude();
			} else {
				FacesUtil.addInfoMessage(FacesUtil.getMessage("error.no.records"), null, false);
				error = true;
			}
		}
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
		inputs = new ManyInputSelect();
		logs = null;
		searchCriteria = new SearchCriteria();
	}
	
	public void onMarkerSelect(OverlaySelectEvent event) {
		if(event.getOverlay() instanceof Marker) {
			marker = (Marker) event.getOverlay();
			selectedLog = (LogDetails) marker.getData();
			latitude = "" + selectedLog.getLatitude();
			longitude = "" + selectedLog.getLongitude();
		} else {
			selectedLog = null;
		}
    }
	
	public void selectMarker() {
		if(marker != null) {
			marker.setIcon(Constants.BLUE_MARKER_ICON);
		}
        LatLng coord = new LatLng(selectedLog.getLatitude(), selectedLog.getLongitude());
        for(Marker marker : advancedModel.getMarkers()) {
        	if(marker.getLatlng().equals(coord)) {
        		this.marker = marker;
        		marker.setIcon("");
        		break;
        	}
        }
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

	public ManyInputSelect getInputs() {
		return inputs;
	}

	public void setInputs(ManyInputSelect inputs) {
		this.inputs = inputs;
	}
	
	public boolean isError() {
		return error;
	}
}
