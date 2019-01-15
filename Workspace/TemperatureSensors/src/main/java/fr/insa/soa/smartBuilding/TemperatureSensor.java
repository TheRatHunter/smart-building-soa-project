package fr.insa.soa.smartBuilding;

public class TemperatureSensor {

	private int id;
	private double min = 10.0;
	private double max = 25.0;
	private int mapCoordX;
	private int mapCoordY;
	
	public TemperatureSensor(int id, int mapCoordX, int mapCoordY) {
		super();
		this.id = id;
		this.mapCoordX = mapCoordX;
		this.mapCoordY = mapCoordY;
	}
	
	public int getID() {
		return id;
	}
	
	public int getMapCoordX() {
		return mapCoordX;
	}
	
	public int getMapCoordY() {
		return mapCoordY;
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
