//setTimeout("window.location.reload()",3000);

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");


var tempSensorsCoords = [];

var img = new Image();
img.onload = function() {
	console.log("Drawing image.");
	ctx.drawImage(img, 0, 0);

	console.log("Drawing sensors.");
	var tempSensorsNb = parseInt($("#tsnb").text());
	for (var i=0; i<tempSensorsNb; i++) {
		var tempSensorsId = $("#tsid"+(i.toString())).text();
		
		var x = parseInt($("#"+tempSensorsId+"X:hidden").text());
		var y = parseInt($("#"+tempSensorsId+"Y:hidden").text());
		
		console.log("X : "+x.toString()+", Y : "+y.toString());
		tempSensorsCoords.push({x: x, y: y, i: i});
		drawTempSensor(x, y, i);
		
	}

	console.log("Drawing legend.");
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
	drawSensor(x, y, nb, 'rgb(51, 204, 51, 1)', 'rgb(20, 82, 20, 1)');
}

function drawLumSensor(x, y, nb) {
	drawSensor(x, y, nb, 'rgb(255, 255, 26, 1)', 'rgb(179, 179, 0, 1)');
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
	var c = document.getElementById("myCanvas");
	var ctx = c.getContext("2d");
	ctx.fillStyle = 'rgb(0, 0, 0, 0.1)';
	ctx.fillRect(20, 20, 200, 200); 
	
	// Title
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "bold 16px Arial";
	ctx.fillText("Sensors :", 30, 40); 
	
	// Temp sensor
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Temperature :", 40, 70); 	
	drawTempSensor(180, 65, 0);
	
	// Lum sensor
	ctx.fillStyle = 'rgb(0,0,0)';
	ctx.font = "16px Arial";
	ctx.fillText("Luminosity :", 40, 125); 
	drawLumSensor(180, 120, 0);
}






// Element hovering
canvas.onmousemove = function(e) {
	
	// Correct mouse position:
	var rect = this.getBoundingClientRect();
	var x = e.clientX - rect.left;
	var y = e.clientY - rect.top;
	var i = 0;
	var r;
	
	var toggle = false;

	// Hovering color
	while(r = tempSensorsCoords[i++]) {
		if ((x>r.x-20) && (x<(r.x+20)) && (y>r.y-20) && (y<(r.y+20))) {
			console.log("A");
			drawLumSensor(r.x, r.y, r.i);
			toggle = true;
		} else {
			console.log("B");
			drawTempSensor(r.x, r.y, r.i);
		}
	}

	// Tooltip management
	var tooltip = document.getElementById("tooltip");
	if (toggle) {
		tooltip.style.display = "block";
	} else {
		tooltip.style.display = "none";
	}

};