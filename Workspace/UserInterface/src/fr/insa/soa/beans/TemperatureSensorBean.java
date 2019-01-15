package fr.insa.soa.beans;

import java.util.ArrayList;
import java.util.HashMap;

import fr.insa.soa.helpers.TimedValue;

import java.sql.Timestamp;

public class TemperatureSensorBean {
	private String id;
	private ArrayList<TimedValue> values = new ArrayList<TimedValue>();
	private Integer mapCoordX;
	private Integer mapCoordY;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addValue(String value) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		TimedValue timedValue = new TimedValue(timestamp, value);
		values.add(timedValue);
	}

	public ArrayList<TimedValue> getValues() {
		return values;
	}

	public void setValues(ArrayList<TimedValue> values) {
		this.values = values;
	}

	public String getValue() {
		return values.get(values.size()-1).getValue();
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
