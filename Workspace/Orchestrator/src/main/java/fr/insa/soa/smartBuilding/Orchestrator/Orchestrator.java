package fr.insa.soa.smartBuilding.Orchestrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.insa.soa.beans.AlarmBean;
import fr.insa.soa.beans.HeaterBean;
import fr.insa.soa.beans.TemperatureSensorBean;
import fr.insa.soa.beans.WindowBean;

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
		
		System.out.println("Sensors :");
		for (String sensorName : temperatureSensorNames) {
			System.out.println("\t"+sensorName+" : "+Float.toString(temperatureSensorsValues.get(sensorName)));
		}	
		
		System.out.println("Heaters :");
		for (String heaterName : heaterNames) {
			System.out.println("\t"+heaterName+" : "+Boolean.toString(heatersStatus.get(heaterName)));
		}	
		
		System.out.println("Windows :");
		for (String windowName : windowNames) {
			System.out.println("\t"+windowName+" : "+Boolean.toString(windowsStatus.get(windowName)));
		}	
		
		
		
		/*
			
		// Pass number of temperature sensors as attribute
		request.setAttribute("numberOfTemperatureSensors", temperatureSensorBeans.size() );			
		
		// Extract list of sensors and sort it by id
		ArrayList<TemperatureSensorBean> beans = new ArrayList<TemperatureSensorBean>(temperatureSensorBeans.values());
		beans.sort((TemperatureSensorBean o1, TemperatureSensorBean o2) -> o1.getId().compareTo(o2.getId()));
		
		// Pass sensor bean of each sensor as attribute
		int i=0;
		for (TemperatureSensorBean bean : beans) {
			request.setAttribute("ts"+Integer.toString(i), bean);
			i++;
		}		
		
		
		
		
		
		for (Entry<String, TemperatureSensorBean> ts : temperatureSensorBeans.entrySet()) {
			int roomNumber = Integer.parseInt(ts.getValue().getId().substring((ts.getValue().getId().length()-1), (ts.getValue().getId().length())));
			
			// Initialize entry with new bean if absent
			AlarmBean newBean = new AlarmBean();
			newBean.setRoomNumber(roomNumber);
			alarmBeans.putIfAbsent(Integer.toString(roomNumber), newBean);	
			if (Float.parseFloat(ts.getValue().getValue()) > 22.0) {
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureHighAlarm(true);
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureLowAlarm(false);
			} else if (Float.parseFloat(ts.getValue().getValue()) < 15.0) {
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureLowAlarm(true);
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureHighAlarm(false);
			} else {
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureLowAlarm(false);
				alarmBeans.get(Integer.toString(roomNumber)).setTemperatureHighAlarm(false);
			}

			alarmBeans.get(Integer.toString(roomNumber)).setHeaterWindowBothOnAlarm(false);
			for (Entry<String, WindowBean> win : windowBeans.entrySet()) {
				for (Entry<String, HeaterBean> heater : heaterBeans.entrySet()) {
					if (win.getValue().getId().contains(Integer.toString(roomNumber)) && heater.getValue().getId().contains(Integer.toString(roomNumber))) {
						if (win.getValue().getStatus() && heater.getValue().getStatus()) {
							alarmBeans.get(Integer.toString(roomNumber)).setHeaterWindowBothOnAlarm(true);
						}
					} 
				}
			}
		}
		
		// Pass number of alarms as attribute
		request.setAttribute("numberOfAlarms", alarmBeans.size() );			
				
		// Extract list of heaters and sort it by id
		ArrayList<AlarmBean> beans = new ArrayList<AlarmBean>(alarmBeans.values());
		beans.sort((AlarmBean o1, AlarmBean o2) -> Integer.compare(o1.getRoomNumber(),o2.getRoomNumber()));
		
		// Pass heater bean of each heater as attribute
		int i=0;
		for (AlarmBean bean : beans) {
			request.setAttribute("a"+Integer.toString(i), bean);
			i++;
		}	
		
		return request;
		*/
		
	}
}
