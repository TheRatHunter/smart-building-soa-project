package fr.insa.soa.smartBuilding;

import java.util.HashMap;
import java.util.Set;

public class TemperatureSensorsSystem {
	
	private HashMap<String, TemperatureSensor> temperatureSensors;
	
	public TemperatureSensorsSystem() {
		super();
		this.temperatureSensors = new HashMap<String, TemperatureSensor>();
		temperatureSensors.putIfAbsent("sensor0", new TemperatureSensor(0, 331, 236));
		temperatureSensors.putIfAbsent("sensor1", new TemperatureSensor(1, 690, 101));
		temperatureSensors.putIfAbsent("sensor2", new TemperatureSensor(2, 571, 471));
	}
	
	public Set<String> getSensors() {
		return temperatureSensors.keySet();
	}
	
	public double getMapCoordXFromSensor(String sensorId) {
		return temperatureSensors.get(sensorId).getMapCoordX();
	}
	
	public double getMapCoordYFromSensor(String sensorId) {
		return temperatureSensors.get(sensorId).getMapCoordY();
	}
	
	public double getTemperatureFromSensor(String sensorId) {
		return temperatureSensors.get(sensorId).getTemperature();
	}
	
}
