package fr.insa.soa.smartBuilding;

public class TemperatureSensor {

	private int id;
	private double min = 10.0;
	private double max = 25.0;
	
	public TemperatureSensor(int id) {
		super();
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setMin(double value) {
		min = value;
	}
	
	public void setMax(double value) {
		max = value;
	}

	public double getTemperature() {		
		return min + (Math.random() * (max - min));
	}

}
