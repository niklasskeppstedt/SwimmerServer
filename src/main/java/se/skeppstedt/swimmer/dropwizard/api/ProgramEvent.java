package se.skeppstedt.swimmer.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramEvent {

	private String eventId;
	private String eventName;
	
	public ProgramEvent(String eventId, String eventName) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
	}
	
	@JsonProperty
	public String getEventId() {
		return eventId;
	}
	
	@JsonProperty
	public String getEventName() {
		return eventName;
	}
	
	

}
