package se.skeppstedt.swimmer.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.auth.basic.BasicCredentials;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class User implements Principal {
    private String username;
    private String password;

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