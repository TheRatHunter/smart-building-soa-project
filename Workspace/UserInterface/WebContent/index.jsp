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
 	Valeur du capteur : 
 	
 	<jsp:useBean id="ts" class="fr.insa.soa.beans.TemperatureSensorBean" scope="request" />
 	${ts.value}
</p>
</body>
<script>
//setTimeout("window.location.reload()",10000);
</script>
</html>