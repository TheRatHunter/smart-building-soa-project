package fr.insa.soa.smartBuilding.Orchestrator;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("orchestrate")
public class Orchestrator {

	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String takeActions() {
		
		getStatusAndTakeActions();
        return "OK !";
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
	
	private void restApiPut(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().put(Entity.text("{}"));
	}
	
	private void actOnWindow(String windowId, Boolean status) {
		String putURI = "http://localhost:8080/Windows/webapi/windows/window/"+windowId+"/"+Boolean.toString(status);
		System.out.println("[Orchestrator] Sending put : " + putURI);
		restApiPut(putURI);
	}
	
	private void actOnHeater(String heaterId, Boolean status) {
		String putURI = "http://localhost:8080/Heaters/webapi/heaters/heater/"+heaterId+"/"+Boolean.toString(status);
		System.out.println("[Orchestrator] Sending put : " + putURI);
		restApiPut(putURI);
	}
	
	private void getStatusAndTakeActions() {
		
		ArrayList<String> temperatureSensorNames = restApiGetArray("http://localhost:8080/TemperatureSensors/webapi/sensors");
		ArrayList<String> heaterNames = restApiGetArray("http://localhost:8080/Heaters/webapi/heaters");
		ArrayList<String> windowNames = restApiGetArray("http://localhost:8080/Windows/webapi/windows");
		
		HashMap<String, Float> temperatureSensorsValues = new HashMap<String, Float>();
		HashMap<String, Boolean> heatersStatus = new HashMap<String, Boolean>();
		HashMap<String, Boolean> windowsStatus = new HashMap<String, Boolean>();
		
		for (String sensorName : temperatureSensorNames) {
			// Get last temperature value
			String lastValue = restApiGet("http://localhost:8080/TemperatureSensors/webapi/sensors/sensor?sensorId="+sensorName);			
			temperatureSensorsValues.putIfAbsent(sensorName, Float.parseFloat(lastValue));
		}	
		
		for (String heaterName : heaterNames) {
			// Get heater status
			String status = restApiGet("http://localhost:8080/Heaters/webapi/heaters/heater?heaterId="+heaterName);			
			heatersStatus.putIfAbsent(heaterName, Boolean.parseBoolean(status));
		}
		
		for (String windowName : windowNames) {
			// Get window status
			String status = restApiGet("http://localhost:8080/Windows/webapi/windows/window?windowId="+windowName);		
			windowsStatus.putIfAbsent(windowName, Boolean.parseBoolean(status));
		}
		
		// Everything happening in this loop is atrocious, but I don't have time to do it clean
		for (String sensorName : temperatureSensorNames) {
			// Extract room number from sensor name (really dirty, I should really change this)
			int roomNb = Integer.parseInt(Character.toString(sensorName.charAt(6)));
			
			if (temperatureSensorsValues.get(sensorName)>22.0) {
				// Open window, shut down heater
				actOnHeater(("heater"+Integer.toString(roomNb)), false);
				actOnWindow(("window"+Integer.toString(roomNb)), true);
			} else if (temperatureSensorsValues.get(sensorName)<15.0) {
				// Close window, turn on heater
				actOnHeater(("heater"+Integer.toString(roomNb)), true);
				actOnWindow(("window"+Integer.toString(roomNb)), false);
				
			}
		}	
		/*
		System.out.println("Sensors :");
		for (String sensorName : temperatureSensorNames) {
			System.out.println("\t"+sensorName+" : "+Float.toString(temperatureSensorsValues.get(sensorName)));
		}	
		
		System.out.println("Heaters :");
		for (String heaterName : heaterNames) {
			System.out.println("\t"+heaterName+" : "+Boolean.toString(heatersStatus.get(heaterName)));
			actOnHeater(heaterName, heatersStatus.get(heaterName));
		}	
		
		System.out.println("Windows :");
		for (String windowName : windowNames) {
			System.out.println("\t"+windowName+" : "+Boolean.toString(windowsStatus.get(windowName)));
			actOnWindow(windowName, windowsStatus.get(windowName));
		}*/
		

		
			
		
	}
}
