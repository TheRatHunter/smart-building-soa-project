package fr.insa.soa.smartBuilding;

import java.util.HashMap;
import java.util.Set;

public class TemperatureSensorsSystem {
	
	private HashMap<String, TemperatureSensor> temperatureSensors;
	
	public TemperatureSensorsSystem() {
		super();
		this.temperatureSensors = new HashMap<String, TemperatureSensor>();
		temperatureSensors.putIfAbsent("sensor0", new TemperatureSensor(0));
		temperatureSensors.putIfAbsent("sensor1", new TemperatureSensor(1));
		temperatureSensors.putIfAbsent("sensor2", new TemperatureSensor(2));
	}
	
	public Set<String> getSensors() {
		return temperatureSensors.keySet();
	}
	
	public double getTemperatureFromSensor(String sensorId) {
		return temperatureSensors.get(sensorId).getTemperature();
	}
	
}
