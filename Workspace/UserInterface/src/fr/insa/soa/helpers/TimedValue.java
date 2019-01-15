package fr.insa.soa.helpers;
import java.sql.Timestamp;

public class TimedValue {
	
	private Timestamp timestamp;
	private String value;
	
	public TimedValue(Timestamp timestamp, String value) {
		super();
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}

}
