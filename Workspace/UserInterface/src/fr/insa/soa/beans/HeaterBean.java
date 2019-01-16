package fr.insa.soa.beans;

import java.util.ArrayList;


public class HeaterBean {
	private String id;
	private boolean status;
	private Integer mapCoordX;
	private Integer mapCoordY;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getMapCoordX() {
		return mapCoordX;
	}
	public void setMapCoordX(Integer mapCoordX) {
		this.mapCoordX = mapCoordX;
	}
	public Integer getMapCoordY() {
		return mapCoordY;
	}
	public void setMapCoordY(Integer mapCoordY) {
		this.mapCoordY = mapCoordY;
	}

}
