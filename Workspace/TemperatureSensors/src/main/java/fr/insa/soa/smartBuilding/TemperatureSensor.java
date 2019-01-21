package fr.insa.soa.smartBuilding;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


public class TemperatureSensor {

	private int id;
	private double min = 10.0;
	private double max = 25.0;
	private int mapCoordX;
	private int mapCoordY;
	private double lastTemperature;
	
	public TemperatureSensor(int id, int mapCoordX, int mapCoordY) {
		super();
		this.id = id;
		this.mapCoordX = mapCoordX;
		this.mapCoordY = mapCoordY;
		this.lastTemperature = min + (Math.random() * (max - min));
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
		String windowStatus = "";
		String heaterStatus = "";
		
		ArrayList<String> windowNames = restApiGetArray("http://localhost:8080/Windows/webapi/windows");
		for (String windowName : windowNames) {			
			if (windowName.endsWith(Integer.toString(this.getID()))) {
				// Get heater status
				windowStatus = restApiGet("http://localhost:8080/Windows/webapi/windows/window?windowId="+windowName);			
			
			}
		}	
		ArrayList<String> heaterNames = restApiGetArray("http://localhost:8080/Heaters/webapi/heaters");
		for (String heaterName : heaterNames) {			
			if (heaterName.endsWith(Integer.toString(this.getID()))) {
				// Get heater status
				heaterStatus = restApiGet("http://localhost:8080/Heaters/webapi/heaters/heater?heaterId="+heaterName);			
			
			}
		}	
		
		if (windowStatus.equals("true") && heaterStatus.equals("true")) {
			if (this.lastTemperature>10.0) {
				this.lastTemperature -= 1;
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" -1 ! Now "+Double.toString(this.lastTemperature));
			} else {
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" stable. Now "+Double.toString(this.lastTemperature));
			}
		} else if (windowStatus.equals("false") && heaterStatus.equals("true")) {
			if (this.lastTemperature<25.0) {
				this.lastTemperature += 1;
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" +1 ! Now "+Double.toString(this.lastTemperature));
			} else {
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" stable. Now "+Double.toString(this.lastTemperature));
			}
		} else if (windowStatus.equals("true") && heaterStatus.equals("false")) {
			if (this.lastTemperature>10.0) {
				this.lastTemperature -= 2;
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" -2 ! Now "+Double.toString(this.lastTemperature));
			} else {
				System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" stable. Now "+Double.toString(this.lastTemperature));
			}
		} else {
			System.out.println("[Temperature sensors] Temperature "+Integer.toString(this.getID())+" stable. Now "+Double.toString(this.lastTemperature));
		}
		
		return this.lastTemperature;
	}
	
	/**
     * Perform GET request at provided url and returns result as String
     */
	private String restApiGet(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().get();
		return restResponse.readEntity(String.class);
	}
	
	
	/**
     * Perform GET request at provided url and returns result as ArrayList<String>
     */
	@SuppressWarnings("unchecked")
	private ArrayList<String> restApiGetArray(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().get();
		return restResponse.readEntity(ArrayList.class);
	}

}
