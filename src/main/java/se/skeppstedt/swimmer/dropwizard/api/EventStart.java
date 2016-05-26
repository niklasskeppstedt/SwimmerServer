package se.skeppstedt.swimmer.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventStart implements Comparable<EventStart>{
	
	private final int startIndex;
	private final int startId;
	private final int swimmerBorn;
	private final String swimmerClub;
	private final String swimmerLicens;
	private final String swimmerRegistrationPool;
	private final String swimmerRegistrationTime;
	private final String swimmerRegistrationStatus;
	private final String swimmerFirstName;
	private final String swimmerLastName;
	
	public EventStart(int startIndex, int startId, int swimmerBorn, String swimmerClub, String swimmerLicens,
			String swimmerRegistrationPool, String swimmerRegistrationTime, String swimmerRegistrationStatus, String firstName, String lastName) {
		super();
		this.startIndex = startIndex;
		this.startId = startId;
		this.swimmerBorn = swimmerBorn;
		this.swimmerClub = swimmerClub;
		this.swimmerLicens = swimmerLicens;
		this.swimmerRegistrationPool = swimmerRegistrationPool;
		this.swimmerRegistrationTime = swimmerRegistrationTime;
		this.swimmerRegistrationStatus = swimmerRegistrationStatus;
		this.swimmerFirstName = firstName;
		this.swimmerLastName = lastName;
	}

	@JsonProperty
	public int getStartIndex() {
		return startIndex;
	}

	@JsonProperty
	public int getStartId() {
		return startId;
	}

	@JsonProperty
	public int getSwimmerBorn() {
		return swimmerBorn;
	}

	@JsonProperty
	public String getSwimmerClub() {
		return swimmerClub;
	}

	@JsonProperty
	public String getSwimmerLicens() {
		return swimmerLicens;
	}

	@JsonProperty
	public String getSwimmerRegistrationPool() {
		return swimmerRegistrationPool;
	}

	@JsonProperty
	public String getSwimmerRegistrationTime() {
		return swimmerRegistrationTime;
	}

	@JsonProperty
	public String getSwimmerRegistrationStatus() {
		return swimmerRegistrationStatus;
	}
	
	@JsonProperty
	public String getSwimmerFirstName() {
		return swimmerFirstName;
	}

	@JsonProperty
	public String getSwimmerLastName() {
		return swimmerLastName;
	}

	@Override
	public String toString() {
		return "EventStart [startIndex=" + startIndex + ", startId=" + startId + ", swimmerBorn=" + swimmerBorn
				+ ", swimmerClub=" + swimmerClub + ", swimmerLicens=" + swimmerLicens + ", swimmerRegistrationPool="
				+ swimmerRegistrationPool + ", swimmerRegistrationTime=" + swimmerRegistrationTime
				+ ", swimmerRegistrationStatus=" + swimmerRegistrationStatus + "]";
	}

	@Override
	public int compareTo(EventStart start) {
		return startIndex - start.getStartIndex();
	}

}
