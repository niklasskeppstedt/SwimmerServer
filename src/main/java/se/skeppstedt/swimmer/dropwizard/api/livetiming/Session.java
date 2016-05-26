package se.skeppstedt.swimmer.dropwizard.api.livetiming;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Session implements Comparable<Session>{
	private int sessionId;
	private Set<ProgramEvent> sessionEvents = new TreeSet<>();
	
	public Session(int sessionId) {
		this.sessionId = sessionId;
	}

	@JsonProperty
	public int getSessionId() {
		return sessionId;
	}
	
	@JsonProperty
	public Set<ProgramEvent> getEvents() {
		return sessionEvents;
	}
	
	public void addProgramEvent(ProgramEvent programEvent) {
		sessionEvents.add(programEvent);
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", sessionEvents=" + sessionEvents + "]";
	}

	@Override
	public int compareTo(Session otherSession) {
		return sessionId - otherSession.getSessionId();
	}
	
}
