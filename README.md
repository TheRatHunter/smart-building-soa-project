# smart-building-soa-project
Use of a Service Oriented Architecture in order do develop a Smart Building Application.

## Setup

### Project import

With eclipse.

### Tomcat RAM usage 

It might be necessary to increase the Tomcat server memory

Under the file ```TOMCAT_HOME/bin/setenv.sh``` (UNIX) (create if it doesn't exist), enter :

> export JAVA_OPTS="-Dfile.encoding=UTF-8 -Xms128m -Xmx2048m -XX:PermSize=265m -XX:MaxPermSize=1024m"

## Services

### Temperature sensors REST API

To get a a table containing the names of available sensors (GET) : 

> http://localhost:8080/TemperatureSensors/webapi/sensors

To get a value from a sensor (GET) :

> http://localhost:8080/TemperatureSensors/webapi/sensors/sensor?sensorId=sensorX

To get a sensor's position on the room map (GET) : 

> http://localhost:8080/TemperatureSensors/webapi/sensors/coordX?sensorId==sensorX

> http://localhost:8080/TemperatureSensors/webapi/sensors/coordY?sensorId==sensorX

### Heaters REST API

To get a table of all available heaters :

> http://localhost:8080/Heaters/webapi/heaters

To get the status of a heater (GET) :

> http://localhost:8080/Heaters/webapi/heaters/heater?heaterId=heaterX

To set the status of a heater (PUT) :

> http://localhost:8080/Heaters/webapi/heaters/heater/heaterX/true

To get a heater's potition on the room map (GET) :

> http://localhost:8080/Heaters/webapi/heaters/coordX?heaterId=heaterX

> http://localhost:8080/Heaters/webapi/heaters/coordY?heaterId=heaterX

### Windows REST API

To get a table of all available windows :

> http://localhost:8080/Windows/webapi/windows

To get the status of a window (GET) :

> http://localhost:8080/Windows/webapi/windows/window?windowId=windowX

To set the status of a window (PUT) :

> http://localhost:8080/Windows/webapi/windows/window/windowX/true

To get a window's potition on the room map (GET) :

> http://localhost:8080/Windows/webapi/windows/coordX?windowId=windowX

> http://localhost:8080/Windows/webapi/windows/coordY?windowId=windowX


## Client
