package se.skeppstedt.swimmer.dropwizard.api;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProgramEvent implements Comparable<ProgramEvent>{

	private int eventId;
	private String eventName;
	private Set<EventStart> startList = new TreeSet<>();
	
	public ProgramEvent(int eventId, String eventName) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
	}
	
	@JsonProperty
	public int getEventId() {
		return eventId;
	}
	
	@JsonProperty
	public String getEventName() {
		return eventName;
	}
	
	@JsonProperty
	public Set<EventStart> getStartList() {
		return startList;
	}

	public void addStart(EventStart start) {
		startList.add(start);
	}
	
	public void setStartList(Set<EventStart> startList) {
		this.startList = startList;
	}

	@Override
	public String toString() {
		return "ProgramEvent [eventId=" + eventId + ", eventName=" + eventName + "]";
	}

	@Override
	public int compareTo(ProgramEvent otherEvent) {
		return eventId - otherEvent.getEventId();
	}
	

}
