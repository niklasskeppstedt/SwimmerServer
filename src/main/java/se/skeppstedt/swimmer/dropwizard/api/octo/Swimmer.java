package se.skeppstedt.swimmer.dropwizard.api.octo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Swimmer {
    private String id;
    private String firstName;
    private String lastName;
    private String club;
    private String yearOfBirth;
    private String licence;
    private List<PersonalBest> personalBests = new ArrayList<>();
    
    public Swimmer() {
    	
    }

    public Swimmer(String id, String firstName, String lastName, String club, String yearOfBirth, String licence) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.club = club;
    	this.yearOfBirth = yearOfBirth;
    	this.licence = licence;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    @JsonProperty
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void addPersonalBest(PersonalBest personalBest) {
		personalBests.add(personalBest);
	}
	
	@JsonProperty
	public List<PersonalBest> getPersonalBests() {
		return personalBests;
	}
	
	public void setPersonalBests(List<PersonalBest> personalBests) {
		this.personalBests = personalBests;
	}
	
	@JsonProperty
	public String getLicence() {
		return licence;
	}
	
	public void setLicence(String licence) {
		this.licence = licence;
	}
}