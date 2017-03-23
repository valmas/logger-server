package com.ntua.ote.logger.web.rest.model;

import java.util.List;

public class GeolocateRequest {

	private List<CellTowers> cellTowers;

	public  List<CellTowers> getCellTowers() {
		return cellTowers;
	}

	public void setCellTowers( List<CellTowers> cellTowers) {
		this.cellTowers = cellTowers;
	}
	
}
