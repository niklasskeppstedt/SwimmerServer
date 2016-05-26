package se.skeppstedt.swimmer.dropwizard.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum Distance {
	TWENTY_FIVE("25"),
	FIFTY("50"),
	ONE_HUNDRED("100"),
	TWO_HUNDRED("200"),
	FOUR_HUNDRED("400"),
	EIGHT_HUNDRED("800"),
	FIFTEEN_HUNDRED("1500");
	
	private String distance;
	private Distance(String distance) {
		this.distance = distance;
	}
	
	@JsonProperty
	public String getDistance() {
		return distance;
	}
	
	@Override
	public String toString() {
		return distance;
	}
}
