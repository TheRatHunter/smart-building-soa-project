package fr.insa.soa.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
	
	private HashMap<String, TemperatureSensorBean> temperatureSensorBeans = new HashMap<String, TemperatureSensorBean>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In servlet.");
		
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
		
			
		request.setAttribute("numberOfTemperatureSensors", temperatureSensorBeans.size() );
			
		int i=0;
		for (TemperatureSensorBean bean : temperatureSensorBeans.values()) {
			request.setAttribute("ts"+Integer.toString(i), bean);
			i++;
		}
		
		String ts = request.getParameter( "aa" );		
		request.setAttribute( "ts", ts );
		
		String test = request.getParameter( "test" );		
		request.setAttribute( "test", test );


		this.getServletContext().getRequestDispatcher( "/index.jsp" ).forward( request, response );

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String restApiGet(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().get();
		return restResponse.readEntity(String.class);
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<String> restApiGetArray(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().get();
		return restResponse.readEntity(ArrayList.class);
	}
	

}
