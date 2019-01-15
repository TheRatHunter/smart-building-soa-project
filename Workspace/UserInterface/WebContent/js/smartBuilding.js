//setTimeout("window.location.reload()",10000);

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");

var img = new Image();
img.onload = function() {
	console.log("Drawing image");
	ctx.drawImage(img, 0, 0);
	
	var tempSensorsNb = parseInt($("#tsnb").text());
	for (var i=0; i<tempSensorsNb; i++) {
		var tempSensorsId = $("#tsid"+(i.toString())).text();
		
		var x = parseInt($("#"+tempSensorsId+"X:hidden").text());
		var y = parseInt($("#"+tempSensorsId+"Y:hidden").text());
		
		console.log("X : "+x.toString()+", Y : "+y.toString());
		drawTempSensor(x, y, i);
		
	}
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
	ctx.beginPath();
	ctx.arc(x, y, 20, 0, 2 * Math.PI, false);
	ctx.fillStyle = 'rgb(51, 204, 51, 0.8)';
	ctx.fill();
	ctx.lineWidth = 5;
	ctx.strokeStyle = 'rgb(20, 82, 20, 0.8)';
	ctx.stroke();

	ctx.fillStyle = 'rgb(20, 82, 20, 0.8)';
	ctx.font = "16px Arial";
	ctx.fillText(nb.toString(), x-4, y+6); 
}
