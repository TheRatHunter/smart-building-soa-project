<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Smart Building Dashboard</title>

<!-- Bootstrap core CSS-->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">

<!-- Page level plugin CSS-->
<link href="vendor/datatables/dataTables.bootstrap4.css"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin.css" rel="stylesheet">

<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">

</head>

<body id="page-top">

	<nav class="navbar navbar-expand static-top">

		<div class="title mr-1">Smart Building
			Dashboard</div>
			
		<button class="btn refresh-button" value="Refresh Page" onClick="window.location.reload()">Refresh</button>

	</nav>

	<div id="wrapper">
		<!-- Sidebar -->
		<ul class="sidebar navbar-nav">
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-thermometer-half"></i> <span>&nbsp;&nbsp;Temperature</span>
			</a></li>
			
			
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Sensors</p>
						
						<ul>
							<%
							Integer numberOfTemperatureSensors = (Integer) request.getAttribute("numberOfTemperatureSensors");
							out.println(String.format("<li hidden><span id=\"tsnb\">%s</span></li>",Integer.toString(numberOfTemperatureSensors)));
							for (int i = 0; i < numberOfTemperatureSensors; i++) {
									String attrName = "ts" + Integer.toString(i);
									fr.insa.soa.beans.TemperatureSensorBean bean = (fr.insa.soa.beans.TemperatureSensorBean) request
											.getAttribute(attrName);
									if (bean == null) {
										bean = new fr.insa.soa.beans.TemperatureSensorBean();
										request.setAttribute(attrName, bean);
									}
									out.println(String.format("<li> <span id=\"tsid%s\">%s</span> : <b><span id=\"tsval%s\">%s</span></b> <span hidden id=\"%sX\">%d</span> <span hidden id=\"%sY\">%d</span>", i, bean.getId(), bean.getId(), bean.getValue().substring(0, 4), bean.getId(), bean.getMapCoordX(), bean.getId(), bean.getMapCoordY()));
									int nbValues = bean.getValues().size();
									out.println(String.format("<span hidden id=\"tsnbpoints%s\">%s</span>", bean.getId(), Integer.toString(nbValues)));
									out.println("<ul hidden>");
									for (int j=0; j<nbValues; j++) {
										out.println(String.format("<li><span hidden id=\"tspointtime%s%d\">%s</span><span hidden id=\"tspointval%s%d\">%s</span></li>", bean.getId(), j, bean.getValues().get(j).getTimestamp(), bean.getId(), j, bean.getValues().get(j).getValue()));
									}
									out.println("</ul>");
									out.println(" </li>");
								}
							%>
						</ul>
					</div>
				</div>
			</li>
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Heaters</p>
							
						<ul>
							<%
							Integer numberOfHeaters = (Integer) request.getAttribute("numberOfHeaters");
							out.println(String.format("<li hidden><span id=\"hnb\">%s</span></li>",Integer.toString(numberOfHeaters)));
							for (int i = 0; i < numberOfHeaters; i++) {
									String attrName = "h" + Integer.toString(i);
									fr.insa.soa.beans.HeaterBean bean = (fr.insa.soa.beans.HeaterBean) request
											.getAttribute(attrName);
									if (bean == null) {
										bean = new fr.insa.soa.beans.HeaterBean();
										request.setAttribute(attrName, bean);
									}
									String status = bean.getStatus() ? "ON" : "OFF";
									out.println(String.format("<li> <span id=\"hid%s\">%s</span> : <b><span id=\"hval%s\" class=\"status%s\">%s</span></b> <span hidden id=\"%sX\">%d</span> <span hidden id=\"%sY\">%d</span><br/>", i, bean.getId(), bean.getId(), status, status, bean.getId(), bean.getMapCoordX(), bean.getId(), bean.getMapCoordY()));
									String checkProperty = bean.getStatus() ? "checked" : "";
									out.println("<label class=\"switch\">");
									out.println(String.format("<input onchange=\"updateHeater(this);\" id=\"checkboxheater%d\" type=\"checkbox\" %s>", i, checkProperty));
									out.println("<span class=\"slider round\"></span>");
									out.println("</label>");
									out.println("</li>");
							}
							%>
						</ul>
					</div>
				</div>
			</li>
			
			<li class="nav-item"><a class="nav-link" href="">
					<i class="fas fa-door-open"></i> <span>&nbsp;&nbsp;Room status</span>
			</a></li>
			
			<li>
				<div class="shadow">
					<div class="container">
						<p class="sidebar-element-title">Windows</p>
							
						<ul>
							<%
							Integer numberOfWindows = (Integer) request.getAttribute("numberOfWindows");
							out.println(String.format("<li hidden><span id=\"wnb\">%s</span></li>",Integer.toString(numberOfWindows)));
							for (int i = 0; i < numberOfWindows; i++) {
									String attrName = "w" + Integer.toString(i);
									fr.insa.soa.beans.WindowBean bean = (fr.insa.soa.beans.WindowBean) request
											.getAttribute(attrName);
									if (bean == null) {
										bean = new fr.insa.soa.beans.WindowBean();
										request.setAttribute(attrName, bean);
									}
									String status = bean.getStatus() ? "OPEN" : "CLOSED";
									out.println(String.format("<li> <span id=\"wid%s\">%s</span> : <b><span id=\"wval%s\" class=\"status%s\">%s</span></b> <span hidden id=\"%sX\">%d</span> <span hidden id=\"%sY\">%d</span><br/>", i, bean.getId(), bean.getId(), status, status, bean.getId(), bean.getMapCoordX(), bean.getId(), bean.getMapCoordY()));
									String checkProperty = bean.getStatus() ? "checked" : "";
									out.println("<label class=\"switch\">");
									out.println(String.format("<input onchange=\"updateWindow(this);\" id=\"checkboxwindow%d\" type=\"checkbox\" %s>", i, checkProperty));
									out.println("<span class=\"slider round\"></span>");
									out.println("</label>");
									out.println("</li>");
							}
							%>
						</ul>
					</div>
				</div>
			</li>
			
		</ul>

		<div id="content-wrapper">

			<div class="container-fluid">
				<div class="centered">		
					<canvas id="myCanvas" width=1000 height="500" style="border:1px solid #0f0c29;"></canvas>
				
					 
					<div id="myModal" class="modal">
					  <div class="modal-content" style="height: 500px;">
					    <p><span id="modal-value-sensor"></span> : <b><span id="modal-value-value"></span></b></p><br/>
					    <div id="chartContainer" style=""></div>
					  </div>					
					</div>
					
					<div id="myModalNoGraph" class="modal">					
					  <div class="modal-content">
					    <p><span id="modal-value-actuator"></span> : <b><span id="modal-value-status"></span></b></p><br/>
					  </div>					
					</div> 
					
				</div>
				<div style="display:none;">
				  <img id="plan" src="img/plan.png"
				       width="1000" height="500">
				</div>

			</div>
		</div>
	</div>


	

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Page level plugin JavaScript-->
	<script src="vendor/chart.js/Chart.min.js"></script>

	<script src="js/smartBuilding.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>


</body>

</html>
