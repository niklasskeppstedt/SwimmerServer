package se.skeppstedt.swimmer.dropwizard.api;

import io.dropwizard.auth.basic.BasicCredentials;

import java.security.Principal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class User implements Principal {
    private String username;
    private String password;
    private Set<String> savedSwimmerIds;

    public User() {

    }

    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getName() {
		return getUsername();
	}

	public BasicCredentials toCredentials() {
		return new BasicCredentials(username, password);
	}
	
}