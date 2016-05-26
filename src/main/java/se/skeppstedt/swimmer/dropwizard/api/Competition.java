package se.skeppstedt.swimmer.dropwizard.api;

import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Competition {
    private int id;
    private String name;
    private Set<Session> sessions = new TreeSet<>();
    
    public Competition(int id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    public void addSession(Session session) {
    	sessions.add(session);
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    @JsonProperty
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Competition [id=" + id + ", name=" + name + "]";
	}

	public Set<Session> getSessions() {
		return sessions;
	}
	
	public Set<ProgramEvent> getEvents() {
		Set<ProgramEvent> allEvents = new TreeSet<>();
		Set<Session> sessions = getSessions();
		for (Session session : sessions) {
			allEvents.addAll(session.getEvents());
		}
		return allEvents;
	}
	
	
}