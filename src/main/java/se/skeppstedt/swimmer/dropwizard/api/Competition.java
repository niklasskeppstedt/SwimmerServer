package se.skeppstedt.swimmer.dropwizard.api;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Competition {
    private String id;
    private String name;
    private Set<Session> sessions = new HashSet<>();
    
    public Competition() {
    	
    }
    
    public Competition(String id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
    public void addSession(Session session) {
    	sessions.add(session);
    }

    @JsonProperty
    public String getId() {
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
	
	
}