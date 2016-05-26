package se.skeppstedt.swimmer.persistence;

import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.dropwizard.api.User;

public interface SwimmerDao {
	
	Set<Swimmer> getAllSwimmers(User user);
	Swimmer getSwimmer(User user, String swimmerId);

}
