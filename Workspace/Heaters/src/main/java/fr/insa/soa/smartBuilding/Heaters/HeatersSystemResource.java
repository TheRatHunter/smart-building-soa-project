package fr.insa.soa.smartBuilding.Heaters;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("heaters")
public class HeatersSystemResource {
	
	private HeatersSystem heaters;
	
	public HeatersSystemResource() {
		heaters = new HeatersSystem();		
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getHeaters() {
        return heaters.getHeaters();
    }
    
    
    @GET
    @Path("heater")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getHeaterStatus(@QueryParam("heaterId") String heaterId) {
        return heaters.getStatusFromHeater(heaterId);
    }
    
    @PUT
    @Path("heater/{heaterId}/{status}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void setHeaterStatus(@PathParam("heaterId") String heaterId, @PathParam("status") boolean status) {
    	heaters.setStatusOfHeater(heaterId, status);
    	System.out.println("Updated heater "+heaterId+" with status "+Boolean.toString(status)+".");
    }
    
    @GET
    @Path("coordX")
    @Produces(MediaType.APPLICATION_JSON)
    public double getSensorMapCoordX(@QueryParam("heaterId") String heaterId) {
        return heaters.getMapCoordXFromHeater(heaterId);
    }
    
    @GET
    @Path("coordY")
    @Produces(MediaType.APPLICATION_JSON)
    public double getSensorMapCoordY(@QueryParam("heaterId") String heaterId) {
        return heaters.getMapCoordYFromHeater(heaterId);
    }
}
