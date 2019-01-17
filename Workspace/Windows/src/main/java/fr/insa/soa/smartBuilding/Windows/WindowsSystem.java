package fr.insa.soa.smartBuilding.Windows;

import java.util.HashMap;
import java.util.Set;


public class WindowsSystem {

	private HashMap<String, Window> windows;
	
	public WindowsSystem() {
		super();
		this.windows = new HashMap<String, Window>();
		windows.putIfAbsent("window0", new Window(0, 393, 80));
		windows.putIfAbsent("window1", new Window(1, 576, 20));
		windows.putIfAbsent("window2", new Window(2, 705, 403));
		// Turn on heater one
		windows.get("window1").setStatus(true);
	}
	
	public Set<String> getWindows() {
		return windows.keySet();
	}
	
	public double getMapCoordXFromWindow(String windowId) {
		return windows.get(windowId).getMapCoordX();
	}
	
	public double getMapCoordYFromWindow(String windowId) {
		return windows.get(windowId).getMapCoordY();
	}
	
	public boolean getStatusFromWindow(String windowId) {
		return windows.get(windowId).getStatus();
	}
	
	public void setStatusOfWindow(String windowId, boolean status) {
		windows.get(windowId).setStatus(status);
	}
}
