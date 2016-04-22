package se.skeppstedt.swimmer.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Swimmer {
    private String id;
    private String name;
    private String club;
    private String yearOfBirth;
    
    public Swimmer() {
    	
    }

    public Swimmer(String id, String name, String club, String yearOfBirth) {
    	this.id = id;
    	this.name = name;
    	this.club = club;
    	this.yearOfBirth = yearOfBirth;
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

    @JsonProperty
	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

    @JsonProperty
	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
}