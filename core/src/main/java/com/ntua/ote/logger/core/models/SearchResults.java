package com.ntua.ote.logger.core.models;

import java.util.List;

public class SearchResults {

	private List<Node> nodes;
	
	private boolean relationFound;

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public boolean isRelationFound() {
		return relationFound;
	}

	public void setRelationFound(boolean relationFound) {
		this.relationFound = relationFound;
	}
	
}
