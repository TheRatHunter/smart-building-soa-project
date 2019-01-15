//setTimeout("window.location.reload()",10000);

var canvas = document.getElementById("myCanvas");
var ctx = canvas.getContext("2d");

var img = new Image();
img.onload = function() {
	console.log("Drawing image");
	ctx.drawImage(img, 0, 0);
};
img.src = "img/plan.png";

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
