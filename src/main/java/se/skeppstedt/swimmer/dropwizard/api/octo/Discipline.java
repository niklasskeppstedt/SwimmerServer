package se.skeppstedt.swimmer.dropwizard.api.octo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape= JsonFormat.Shape.OBJECT) //This makes JSON serialization use the discipline field instead of Enumeration name which is jackson default
public enum Discipline {
	BREASTSTROKE("Bröstsim"),
	BACKSTROKE("Ryggsim"),
	BUTTERFLY("Fjärilssim"),
	FREESTYLE("Frisim"), 
	MEDLEY("Medley");
	
	private String discipline;
	private Discipline(String distance) {
		this.discipline = distance;
	}
	
	@JsonProperty
	public String getDiscipline() {
		return discipline;
	}
	
	@Override
	public String toString() {
		return discipline;
	}
	
}
