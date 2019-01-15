<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Yo dude</h1>

${test}
<button value="Refresh Page" onClick="window.location.reload()">Refresh</button>

<p>
	Nombre de capteurs : 
	<% 
            Integer numberOfTemperatureSensors = (Integer) request.getAttribute("numberOfTemperatureSensors");
            out.println( Integer.toString(numberOfTemperatureSensors) ); 
    %>
</p>
<p>
 	Valeur des capteurs : 
</p>
<ul><%
 		for (int i=0; i<numberOfTemperatureSensors; i++) {
 			String attrName = "ts"+Integer.toString(i);
 			fr.insa.soa.beans.TemperatureSensorBean bean = (fr.insa.soa.beans.TemperatureSensorBean) request.getAttribute(attrName); 
 			if ( bean == null ){
 				bean = new fr.insa.soa.beans.TemperatureSensorBean();
 			    request.setAttribute( attrName, bean );
 			}
 			out.println("<li>"+bean.getValue()+"</li>");
 		}
 	%>
</ul>
 	
 	
 	
</body>
</html>