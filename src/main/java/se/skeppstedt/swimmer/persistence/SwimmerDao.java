package se.skeppstedt.swimmer.persistence;

import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.User;
import se.skeppstedt.swimmer.dropwizard.api.octo.Swimmer;

public interface SwimmerDao {
	
	Set<Swimmer> getAllSwimmers(User user);
	Swimmer getSwimmer(User user, String swimmerId);

}
