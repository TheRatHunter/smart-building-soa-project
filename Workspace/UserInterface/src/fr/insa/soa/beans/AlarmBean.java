package fr.insa.soa.beans;

public class AlarmBean {
	public int roomNumber;
	public boolean temperatureLoxAlarm = false;
	public boolean temperatureHighAlarm = false;
	public boolean heaterWindowBothOnAlarm = false;
	
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public boolean isTemperatureLowAlarm() {
		return temperatureLoxAlarm;
	}
	public void setTemperatureLowAlarm(boolean temperatureLoxAlarm) {
		this.temperatureLoxAlarm = temperatureLoxAlarm;
	}
	public boolean isTemperatureHighAlarm() {
		return temperatureHighAlarm;
	}
	public void setTemperatureHighAlarm(boolean temperatureHighAlarm) {
		this.temperatureHighAlarm = temperatureHighAlarm;
	}
	public boolean isHeaterWindowBothOnAlarm() {
		return heaterWindowBothOnAlarm;
	}
	public void setHeaterWindowBothOnAlarm(boolean heaterWindowBothOnAlarm) {
		this.heaterWindowBothOnAlarm = heaterWindowBothOnAlarm;
	}

}
