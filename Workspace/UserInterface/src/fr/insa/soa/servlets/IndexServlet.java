package fr.insa.soa.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import fr.insa.soa.beans.TemperatureSensorBean;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, TemperatureSensorBean> temperatureSensorBeans;
       
    /**
     * Constructor
     */
    public IndexServlet() {
        super();
        temperatureSensorBeans = new HashMap<String, TemperatureSensorBean>();
    }

    /**
     * GET method processing
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request = aggregateTemperatureSensorsData(request);
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
			String lastValue = restApiGet("http://localhost:8080/TemperatureSensors/webapi/sensors/sensor?sensorId=sensor2");			
			// Add last value to bean
			temperatureSensorBeans.get(sensorName).addValue(lastValue);
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
	

}
