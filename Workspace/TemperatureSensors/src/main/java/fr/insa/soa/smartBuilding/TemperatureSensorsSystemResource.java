package fr.insa.soa.smartBuilding;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("sensors")
public class TemperatureSensorsSystemResource {
	
	
	private TemperatureSensorsSystem temperatureSensorsSystem;
	
	public TemperatureSensorsSystemResource() {
		temperatureSensorsSystem = new TemperatureSensorsSystem();		
	}
	

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getSensors() {
        return temperatureSensorsSystem.getSensors();
    }
    
    
    @GET
    @Path("sensor")
    @Produces(MediaType.APPLICATION_JSON)
    public double getSensorData(@QueryParam("sensorId") String sensorId) {
        return temperatureSensorsSystem.getTemperatureFromSensor(sensorId);
    }
    
    @GET
    @Path("coordX")
    @Produces(MediaType.APPLICATION_JSON)
    public double getSensorMapCoordX(@QueryParam("sensorId") String sensorId) {
        return temperatureSensorsSystem.getMapCoordXFromSensor(sensorId);
    }
    
    @GET
    @Path("coordY")
    @Produces(MediaType.APPLICATION_JSON)
    public double getSensorMapCoordY(@QueryParam("sensorId") String sensorId) {
        return temperatureSensorsSystem.getMapCoordYFromSensor(sensorId);
    }
}
