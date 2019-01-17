# smart-building-soa-project
Use of a Service Oriented Architecture in order do develop a Smart Building Application.

## Setup

### Project import

After downloading the project, extract it in your workplace and import it in Eclipse JAVA-EE.
At this point you may encounter some configuration problem:

- Make sure you have at least you have JDK 1.7 and version 7 of TOMCAT web container
- If you have the error ```"The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path"``` in one of the services --> Include  ```servlet-api-3.1.jar``` in your dependencies on ```TemperatureSensor/pom.xml```, ```Heaters/pom.xml``` and ```Windows/pom.xml``` by adding these lines :

```<dependency>
 <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>```
 
- Try then ```ALT+F5``` and force Updates of ```Snapshots/Releases``` on all the available Maven codebases.
 
- If you have the problem ```"Multiple annotations found at this line:- Integer cannot be resolved to a type- The method getAttribute(String) from the type ServletRequest refers to the missing type Object"``` on the JSP under UserInterface --> it's a  problem of a missing Build path ! To solve this : Right click on UserInteface then Properties go to the JAVA Build path and check the Libraries, remove the JRE System Library the Re-add it.
 
*Bingo !! The project is yours!!!*


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
