package se.skeppstedt.swimmer.dropwizard.api;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Session {
	private int sessionId;
	private Set<ProgramEvent> sessionEvents = new HashSet<>();
	
	public Session(int sessionId) {
		this.sessionId = sessionId;
	}

	@JsonProperty
	public int getSessionId() {
		return sessionId;
	}
	
	@JsonProperty
	public Set<ProgramEvent> getSessionEvents() {
		return sessionEvents;
	}
	
	public void addProgramEvent(ProgramEvent programEvent) {
		sessionEvents.add(programEvent);
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", sessionEvents=" + sessionEvents + "]";
	}
	
}
