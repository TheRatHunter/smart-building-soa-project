package fr.insa.soa.servlets;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 * Servlet implementation class PostCallServlet
 */
@WebServlet("/PostCallServlet")
public class PostCallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostCallServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Prevent JavaSript Ajax error
		response.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
    	
		// Extract query parameters
		String decodedQueryString = URLDecoder.decode(request.getQueryString(), "UTF-8");
		String[] args = decodedQueryString.split("&");
		ArrayList<String> queryNames = new ArrayList<String>();
		ArrayList<String> queryValues = new ArrayList<String>();
		for (String s : args) {
			String[] args2 = s.split("=");	
			String queryName = args2[0];
			String queryValue = args2[1];
			queryNames.add(queryName);
			queryValues.add(queryValue);
		}
		
		// Formulate PUT request to API
		if (queryNames.get(0).contains("heater")) {
			String putURI = "http://localhost:8080/Heaters/webapi/heaters/heater/"+queryValues.get(0)+"/"+queryValues.get(1);
			restApiPut(putURI);
		}
		if (queryNames.get(0).contains("window")) {
			String putURI = "http://localhost:8080/Windows/webapi/windows/window/"+queryValues.get(0)+"/"+queryValues.get(1);
			restApiPut(putURI);
		}
		
    	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void restApiPut(String url) {
		Client client = ClientBuilder.newClient();
		Response restResponse = client.target(url).request().put(Entity.text("{}"));
	}

}
