package fr.insa.soa.smartBuilding.Heaters;

public class Heater {
	
	private int id;
	private boolean status;
	private int mapCoordX;
	private int mapCoordY;
	
	public Heater(int id, int x, int y) {
		super();
		this.id = id;
		this.status = false;
		this.mapCoordX = x;
		this.mapCoordY = y;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public int getMapCoordX() {
		return mapCoordX;
	}
	public int getMapCoordY() {
		return mapCoordY;
	}

}
