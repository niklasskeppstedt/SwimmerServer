package se.skeppstedt.swimmer.dropwizard.authentication;

import javax.inject.Inject;

import com.google.common.base.Optional;

import se.skeppstedt.swimmer.dropwizard.api.User;
import se.skeppstedt.swimmer.persistence.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {
	
	@Inject
	public SimpleAuthenticator(UserDao userDao) {
		this.userDao = userDao;
	}
	
	private UserDao userDao;
	
	@Override
	public Optional<User> authenticate(BasicCredentials credentials)
			throws AuthenticationException {
		User user = userDao.getUser(credentials.getUsername());
		if (user!= null && user.getPassword().equals(credentials.getPassword())) {
			return Optional.of(new User(credentials.getUsername(), credentials.getPassword()));
		}
		return Optional.absent();
	}
}