package se.skeppstedt.swimmer.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.dropwizard.api.User;

public class SwimmerDaoImpl implements SwimmerDao {
	
	Map<User, Set<Swimmer>> connections = new HashMap<>();

	@Override
	public Set<Swimmer> getAllSwimmers(User user) {
		return connections.get(user);
	}

	@Override
	public Swimmer getSwimmer(User user, String swimmerId) {
		return getAllSwimmers(user).stream().filter(s -> s.getId().equals(swimmerId)).findFirst().orElse(null);
	}
	
	public void saveSwimmer(Swimmer swimmer) {

	}

}
