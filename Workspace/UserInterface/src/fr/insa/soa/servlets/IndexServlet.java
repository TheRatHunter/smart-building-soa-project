package fr.insa.soa.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import fr.insa.soa.beans.AlarmBean;
import fr.insa.soa.beans.HeaterBean;
import fr.insa.soa.beans.TemperatureSensorBean;
import fr.insa.soa.beans.WindowBean;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Bean containers definitions
	private HashMap<String, TemperatureSensorBean> temperatureSensorBeans;
	private HashMap<String, HeaterBean> heaterBeans;
	private HashMap<String, WindowBean> windowBeans;
	private HashMap<String, AlarmBean> alarmBeans;
       
    /**
     * Constructor
     */
    public IndexServlet() {
        super();
        // Bean containers creation
        temperatureSensorBeans = new HashMap<String, TemperatureSensorBean>();
        heaterBeans = new HashMap<String, HeaterBean>();
        windowBeans = new HashMap<String, WindowBean>();
        alarmBeans = new HashMap<String, AlarmBean>();
    }

    /**
     * GET method processing
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request = aggregateTemperatureSensorsData(request);
		request = aggregateHeatersData(request);
		request = aggregateWindowsData(request);
		request = computeAlarmsStatus(request);
		this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );

	}

	/**
     * POST method processing
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generateHttpServletRequestd method stub
		doGet(request, response);
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
	
	/**
	 * Aggregates several attributes to the request :
	 * numberOfTemperatureSensors, the number of temperature sensors
	 * tsX, x between 0 and numberOfTemperatureSensors, the temperature sensor beans
	 * @param  request  The request to process
	 * @return 			The processed request
	 */
	private HttpServletRequest aggregateTemperatureSensorsData(HttpServletRequest request) {
		
		ArrayList<String> temperatureSensorNames = restApiGetArray("http://localhost:8080/TemperatureSensors/webapi/sensors");
		
		for (String sensorName : temperatureSensorNames) {
			// Initialize entry with new bean if absent
			TemperatureSensorBean newBean = new TemperatureSensorBean();
			newBean.setId(sensorName);
			temperatureSensorBeans.putIfAbsent(sensorName, newBean);			
			// Get last temperature value
			String lastValue = restApiGet("http://localhost:8080/TemperatureSensors/webapi/sensors/sensor?sensorId="+sensorName);			
			// Add last value to bean
			temperatureSensorBeans.get(sensorName).addValue(lastValue);
			
			// Get coordinates values (here because sensor can move)
			Integer x = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/TemperatureSensors/webapi/sensors/coordX?sensorId="+sensorName)));
			Integer y = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/TemperatureSensors/webapi/sensors/coordY?sensorId="+sensorName)));
			temperatureSensorBeans.get(sensorName).setMapCoordX(x);
			temperatureSensorBeans.get(sensorName).setMapCoordY(y);
		}		
			
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
		
		return request;
		
	}
	
	/**
	 * Aggregates the heater attributes to the request
	 * @param  request  The request to process
	 * @return 			The processed request
	 */
	private HttpServletRequest aggregateHeatersData(HttpServletRequest request) {
		
		ArrayList<String> heaterNames = restApiGetArray("http://localhost:8080/Heaters/webapi/heaters");
		
		for (String heaterName : heaterNames) {
			// Initialize entry with new bean if absent
			HeaterBean newBean = new HeaterBean();
			newBean.setId(heaterName);
			heaterBeans.putIfAbsent(heaterName, newBean);			
			// Get heater status
			String status = restApiGet("http://localhost:8080/Heaters/webapi/heaters/heater?heaterId="+heaterName);			
			// Add last value to bean
			heaterBeans.get(heaterName).setStatus(status.equals("true"));
			// Get coordinates values
			Integer x = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/Heaters/webapi/heaters/coordX?heaterId="+heaterName)));
			Integer y = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/Heaters/webapi/heaters/coordY?heaterId="+heaterName)));
			heaterBeans.get(heaterName).setMapCoordX(x);
			heaterBeans.get(heaterName).setMapCoordY(y);
		}	
		
		// Pass number of heaters as attribute
		request.setAttribute("numberOfHeaters", heaterBeans.size() );			
		
		// Extract list of heaters and sort it by id
		ArrayList<HeaterBean> beans = new ArrayList<HeaterBean>(heaterBeans.values());
		beans.sort((HeaterBean o1, HeaterBean o2) -> o1.getId().compareTo(o2.getId()));
		
		// Pass heater bean of each heater as attribute
		int i=0;
		for (HeaterBean bean : beans) {
			request.setAttribute("h"+Integer.toString(i), bean);
			i++;
		}	
		
		return request;
		
	}
	
	
	/**
	 * Aggregates the windows attributes to the request
	 * @param  request  The request to process
	 * @return 			The processed request
	 */
	private HttpServletRequest aggregateWindowsData(HttpServletRequest request) {
		
		ArrayList<String> windowNames = restApiGetArray("http://localhost:8080/Windows/webapi/windows");
		
		for (String windowName : windowNames) {
			// Initialize entry with new bean if absent
			WindowBean newBean = new WindowBean();
			newBean.setId(windowName);
			windowBeans.putIfAbsent(windowName, newBean);			
			// Get heater status
			String status = restApiGet("http://localhost:8080/Windows/webapi/windows/window?windowId="+windowName);			
			// Add last value to bean
			windowBeans.get(windowName).setStatus(status.equals("true"));
			// Get coordinates values
			Integer x = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/Windows/webapi/windows/coordX?windowId="+windowName)));
			Integer y = Math.round(Float.parseFloat(restApiGet("http://localhost:8080/Windows/webapi/windows/coordY?windowId="+windowName)));
			windowBeans.get(windowName).setMapCoordX(x);
			windowBeans.get(windowName).setMapCoordY(y);
		}	
		
		// Pass number of heaters as attribute
		request.setAttribute("numberOfWindows", windowBeans.size() );			
		
		// Extract list of heaters and sort it by id
		ArrayList<WindowBean> beans = new ArrayList<WindowBean>(windowBeans.values());
		beans.sort((WindowBean o1, WindowBean o2) -> o1.getId().compareTo(o2.getId()));
		
		// Pass heater bean of each heater as attribute
		int i=0;
		for (WindowBean bean : beans) {
			request.setAttribute("w"+Integer.toString(i), bean);
			i++;
		}	
		
		return request;
		
	}
	
	private HttpServletRequest computeAlarmsStatus(HttpServletRequest request) {
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
		
	}

}
