//setTimeout("window.location.reload()",3000);

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");
var modal = document.getElementById("myModal");


var tempSensorsCoords = [];


//Update checkboxes
function updateCheckboxes()
{
	var nbHeaters = parseInt($("#hnb").text());
	for (var i=0; i<nbHeaters; i++) {
		if ($("#hvalheater"+i.toString()).text()==="ON") {
			$("#checkboxheater"+i.toString()).prop('checked', true);
		} else {
			$("#checkboxheater"+i.toString()).prop('checked', false);
		}
	}
}
updateCheckboxes();

function updateHeater(element)
{
	var nbHeaters = parseInt($("#hnb").text());
	for (var i=0; i<nbHeaters; i++) {
		if (element.checked) {
			if(element.attributes.id.nodeValue === ("checkboxheater"+i.toString())) {
				console.log("Sending status on for heater"+i.toString());
				$.get("PostCallServlet?heaterId=heater"+i.toString()+"&state=true", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				$("#hvalheater"+i.toString()).text("ON");
			}
		} else {
			if(element.attributes.id.nodeValue === ("checkboxheater"+i.toString())) {
				console.log("Sending status off for heater"+i.toString());
				$.get("PostCallServlet?heaterId=heater"+i.toString()+"&state=false", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#somediv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
				$("#hvalheater"+i.toString()).text("OFF");
			}
		}
	}
	updateCheckboxes();
  
}
	
var img = new Image();
img.onload = function() {
	ctx.drawImage(img, 0, 0);

	var tempSensorsNb = parseInt($("#tsnb").text());
	for (var i=0; i<tempSensorsNb; i++) {
		var tempSensorsId = $("#tsid"+(i.toString())).text();		
		var x = parseInt($("#"+tempSensorsId+"X").text());
		var y = parseInt($("#"+tempSensorsId+"Y").text());
		
		tempSensorsCoords.push({x: x, y: y, i: i});
		drawTempSensor(x, y, i);
		
	}

	drawLegend();
};
img.src = "img/plan.png";50

// Mouse event

function getMousePos(canvas, evt) {
	var rect = canvas.getBoundingClientRect();
	return {
		x : evt.clientX - rect.left,
		y : evt.clientY - rect.top
	};
}

canvas.addEventListener('click', function(evt) {
	var mousePos = getMousePos(canvas, evt);
	var message = 'Mouse position: ' + mousePos.x + ',' + mousePos.y;
	console.log(message);
}, false);

function drawTempSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(51, 204, 51)', 'rgb(20, 82, 20)');
}

function drawHoveredSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(133, 224, 133)', 'rgb(15, 62, 15)');
}

function drawLumSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(255, 255, 26)', 'rgb(179, 179, 0)');
}

function drawSensor(x, y, nb, color1, color2) {
	ctx.beginPath();
	ctx.arc(x, y, 20, 0, 2 * Math.PI, false);
	ctx.fillStyle = color1; //'rgb(51, 204, 51, 0.8)';
	ctx.fill();
	ctx.lineWidth = 5;
	ctx.strokeStyle = color2; //'rgb(20, 82, 20, 0.8)';
	ctx.stroke();

	ctx.fillStyle = color2 //'rgb(20, 82, 20, 0.8)';
	ctx.font = "16px Arial";
	ctx.fillText(nb.toString(), x-4, y+6); 
}

function drawLegend() {
	
	
	// Title
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "bold 16px Arial";
	ctx.fillText("Sensors :", 30, 40); 
	
	// Temp sensor
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Temperature :", 40, 80); 	
	drawTempSensor(180, 75, 0);
	
	// Lum sensor
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Luminosity :", 40, 135); 
	drawLumSensor(180, 130, 0);
}





// Element hovering
window.onmousemove = function(e) {	
	
	// Correct mouse position:
	var rect = canvas.getBoundingClientRect();
	var x = e.clientX - rect.left;
	var y = e.clientY - rect.top;
	var i = 0;
	var r;
	
	// Variables needed to display modal & chart
	var toggle = false;
	var chartSensorId = "";
	var chartValues = "";
	var chartSensorValues = [];

	// Hovering color
	while(r = tempSensorsCoords[i++]) {
		if ((x>r.x-20) && (x<(r.x+20)) && (y>r.y-20) && (y<(r.y+20))) {
			drawHoveredSensor(r.x, r.y, r.i);
			toggle = true;
			
			// Compute jQuery requests to gather values
			var tempSensorsId = $("#tsid"+((i-1).toString())).text();
			var tempSensorVal = $("#tsval"+tempSensorsId).text();			
			$("#modal-value-sensor").text(tempSensorsId);		
			$("#modal-value-value").text(tempSensorVal);
			
			var tempSensorNbValues = $("#tsnbpoints"+tempSensorsId).text();
			for (var i=0; i<tempSensorNbValues; i++) {
				var tempTimestamp = $("#tspointtime"+tempSensorsId+i.toString()).text();
				var tempValue = $("#tspointval"+tempSensorsId+i.toString()).text();
				chartSensorValues.push({x: new Date(tempTimestamp), y: Math.round(tempValue)});
			}
			
			// Assign variables values with selected sensor
			chartSensorId = tempSensorsId;		
			
		} else {
			drawTempSensor(r.x, r.y, r.i);
		}
	}

	// Modal management
	if (toggle) {
		modal.style.display = "block";
		canvasOpen=true;
		
		//--- Defining graph
		
		var chart = new CanvasJS.Chart("chartContainer", {
			width: 1010,
			animationEnabled: true,
			axisY: {
				title: "Temperature (in °C)",
				includeZero: false,
				suffix: " °C"
			},
			data: [{
				name: chartSensorId,
				type: "spline",
				yValueFormatString: "#0.## °C",
				dataPoints: chartSensorValues
			}]
		});
		chart.render();
		
		function toggleDataSeries(e){
			if (typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
				e.dataSeries.visible = false;
			}
			else{
				e.dataSeries.visible = true;
			}
			chart.render();
		}
		
		//---
	} else {
		modal.style.display = "none";
	}
	

};
