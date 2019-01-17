package fr.insa.soa.smartBuilding.Windows;

import java.util.Set;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("windows")
public class WindowsSystemResource {
	
	private WindowsSystem windows;
	
	public WindowsSystemResource() {
		windows = new WindowsSystem();		
	}

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getWindows() {
        return windows.getWindows();
    }
    
    
    @GET
    @Path("window")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean getWindowStatus(@QueryParam("windowId") String windowId) {
        return windows.getStatusFromWindow(windowId);
    }
    
    @PUT
    @Path("window/{windowId}/{status}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void setWindowStatus(@PathParam("windowId") String windowId, @PathParam("status") boolean status) {
    	windows.setStatusOfWindow(windowId, status);
   }
    
    @GET
    @Path("coordX")
    @Produces(MediaType.APPLICATION_JSON)
    public double getWindowMapCoordX(@QueryParam("windowId") String windowId) {
        return windows.getMapCoordXFromWindow(windowId);
    }
    
    @GET
    @Path("coordY")
    @Produces(MediaType.APPLICATION_JSON)
    public double getWindowMapCoordY(@QueryParam("windowId") String windowId) {
        return windows.getMapCoordYFromWindow(windowId);
    }
}
