package se.skeppstedt.swimmer.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import se.skeppstedt.swimmer.dropwizard.api.User;

@Singleton
public class UserDaoImpl implements UserDao {
	
	static Map<String, User> users = new HashMap<>();
	
	static {
		users.put("niske", new User("niske", "nik00las"));
	}

	@Override
	public void saveUser(User user) {
		users.put(user.getUsername(), user);
	}

	@Override
	public User getUser(String username) {
		return users.get(username);

	}

}
