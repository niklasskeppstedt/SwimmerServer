package se.skeppstedt.swimmer.octo;

import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.Swimmer;

public interface OctoParser {
	
	Swimmer getSwimmerDetails(String swimmerId);
	
	public Set<Swimmer> searchSwimmers(String firstName, String lastName, String club, String yearOfBirth, String license);

}
