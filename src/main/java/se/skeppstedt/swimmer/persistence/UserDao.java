package se.skeppstedt.swimmer.persistence;

import se.skeppstedt.swimmer.dropwizard.api.User;

public interface UserDao {
	
	void saveUser(User user);
	User getUser(String username);

}
