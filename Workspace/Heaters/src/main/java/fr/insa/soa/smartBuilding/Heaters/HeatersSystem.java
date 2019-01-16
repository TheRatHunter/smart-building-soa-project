package fr.insa.soa.smartBuilding.Heaters;

import java.util.HashMap;
import java.util.Set;


public class HeatersSystem {

	private HashMap<String, Heater> heaters;
	
	public HeatersSystem() {
		super();
		this.heaters = new HashMap<String, Heater>();
		heaters.putIfAbsent("heater0", new Heater(0, 325, 88));
		heaters.putIfAbsent("heater1", new Heater(1, 701, 210));
		heaters.putIfAbsent("heater2", new Heater(2, 618, 324));
	}
	
	public Set<String> getHeaters() {
		return heaters.keySet();
	}
	
	public double getMapCoordXFromHeater(String heaterId) {
		return heaters.get(heaterId).getMapCoordX();
	}
	
	public double getMapCoordYFromHeater(String heaterId) {
		return heaters.get(heaterId).getMapCoordY();
	}
	
	public boolean getStatusFromHeater(String heaterId) {
		return heaters.get(heaterId).getStatus();
	}
	
	public void setStatusOfHeater(String heaterId, boolean status) {
		heaters.get(heaterId).setStatus(status);
	}
}
