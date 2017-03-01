package com.ntua.ote.logger.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.BezierConnector;
import org.primefaces.model.diagram.connector.Connector;
import org.primefaces.model.diagram.connector.StraightConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

import com.ntua.ote.logger.core.common.Constants;
import com.ntua.ote.logger.core.models.Node;
import com.ntua.ote.logger.core.models.SearchCriteria;
import com.ntua.ote.logger.persistence.LoggerDAOImpl;
import com.sun.faces.util.CollectionsUtils;

@Named
@SessionScoped
public class RelationFinderController implements Serializable {

	private static final long serialVersionUID = 2208902766742258519L;

	@Inject
	private LoggerDAOImpl dao;

	private DefaultDiagramModel model;

	private SearchCriteria searchCriteria;
	
	private Map<String, Element> elementMap;
	
	private Element selectedElement;

	public String init() {
		searchCriteria = new SearchCriteria();
		model = null;
		return "relationFinder";
	}

	public void search() {
		elementMap = new HashMap<>();
		Map<String, Integer> arrayMap = new HashMap<>();
		List<Node> results = dao.searchRelation(searchCriteria);
		if(!results.isEmpty()) {
			model = new DefaultDiagramModel();
			model.setMaxConnections(-1);
			model.setConnectionsDetachable(false);
			
			Element root = new Element(searchCriteria.getPhoneNumber(), "50em", "2em");
			root.setId(searchCriteria.getPhoneNumber());
			model.addElement(root);
			elementMap.put(searchCriteria.getPhoneNumber(), root);
			int i=0, level=0;
			for(Node node : results) {
				if(level < node.getLevel()) {
					i = 0;
					level = node.getLevel();
				}
				for(Node childNode : node.getChildren()) {
					if(!elementMap.containsKey(childNode.getPhoneNumber())) {
						Element el = new Element(childNode.getPhoneNumber(), (2 + i*10) +"em", 8 + level*6 + "em");
						el.setId(childNode.getPhoneNumber());
						elementMap.put(childNode.getPhoneNumber(), el);
						model.addElement(el);
						i++;
					}
				}
			}
			for(int j = 0; j < results.size(); j++) {
				arrayMap.put(results.get(j).getPhoneNumber(), j);
			}
			
			for(Node node : results) {
				Element el1 = elementMap.get(node.getPhoneNumber());
				if(!node.getChildren().isEmpty()) {
					el1.addEndPoint(createEndPoint(EndPointAnchor.BOTTOM));
				}
				for(Node childNode : node.getChildren()) {
					Element el2 = elementMap.get(childNode.getPhoneNumber());
					if(el2.getEndPoints() == null || el2.getEndPoints().isEmpty()) {
						el2.addEndPoint(createEndPoint(EndPointAnchor.TOP));
					}
					EndPoint endpoint = el1.getEndPoints().size() > 1 ? el1.getEndPoints().get(1) : el1.getEndPoints().get(0);
					model.connect(new Connection(endpoint, el2.getEndPoints().get(0), createConnector()));
					results.get(arrayMap.get(childNode.getPhoneNumber())).getChildren().remove(node);
				}
			}
			
		}
	}
	
	public void onElementClicked(){
		if(selectedElement != null) {
			selectedElement.setStyleClass(null);
			for(Connection connection : model.getConnections()) {
				connection.getConnector().setPaintStyle(Constants.CONNECTOR_PAINT_STYLE);
				connection.getSource().setStyle(Constants.ENDPOINT_STYLE);
				connection.getTarget().setStyle(Constants.ENDPOINT_STYLE);
			}
		}
		
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("elementId");
		selectedElement = elementMap.get(id.split("relationDiagram-")[1]);
		selectedElement.setStyleClass(Constants.ELEMENT_SELECTED_STYLE_CLASS);
		for(Connection connection : model.getConnections()) {
			if(selectedElement.getEndPoints().contains(connection.getSource()) ||
					selectedElement.getEndPoints().contains(connection.getTarget())) {
				connection.getConnector().setPaintStyle(Constants.CONNECTOR_SELECTED_PAINT_STYLE);
				connection.getSource().setStyle(Constants.ENDPOINT_SELECTED_STYLE);
				connection.getTarget().setStyle(Constants.ENDPOINT_SELECTED_STYLE);
			}
		}
	}
	

	private EndPoint createEndPoint(EndPointAnchor anchor) {
		DotEndPoint endPoint = new DotEndPoint(anchor);
		endPoint.setRadius(5);
		endPoint.setStyle(Constants.ENDPOINT_STYLE);
		return endPoint;
	}
	
	private Connector createConnector() {
		StraightConnector con = new StraightConnector();
		con.setPaintStyle(Constants.CONNECTOR_PAINT_STYLE);
		return con;
	}

	public DiagramModel getModel() {
		return model;
	}

	public void reset() {
		searchCriteria = new SearchCriteria();
		model = null;
	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

}
