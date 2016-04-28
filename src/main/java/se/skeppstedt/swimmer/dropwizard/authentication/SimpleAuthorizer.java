package se.skeppstedt.swimmer.dropwizard.authentication;

import io.dropwizard.auth.Authorizer;
import se.skeppstedt.swimmer.dropwizard.api.User;

public class SimpleAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User arg0, String arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
