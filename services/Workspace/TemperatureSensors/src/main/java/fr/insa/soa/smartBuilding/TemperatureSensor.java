package fr.insa.soa.smartBuilding;

public class TemperatureSensor {

	private int id;
	
	public TemperatureSensor(int id) {
		super();
		this.id = id;
	}
	
	public int getID() {
		return id;
	}

	public double getTemperature() {
		return 0.0;
	}

}
