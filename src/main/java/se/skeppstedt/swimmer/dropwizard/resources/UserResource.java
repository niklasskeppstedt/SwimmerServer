package se.skeppstedt.swimmer.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import se.skeppstedt.swimmer.dropwizard.SwimmersApplication;
import se.skeppstedt.swimmer.dropwizard.api.User;
import se.skeppstedt.swimmer.persistence.UserDaoImp;
import io.dropwizard.auth.AuthenticationException;

import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.HashMap;

@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {
	@Context private ResourceContext rc;
	private UserDaoImp myDao;
	
	private static HashMap<String, User> userDao = new HashMap<>();
	static {
		User user = new User("niske", "nik00las");
		userDao.put(user.getUsername(), user);
		User user2 = new User("peste", "peste");
		userDao.put(user2.getUsername(), user2);
	}

    public UserResource(UserDaoImp dao) {
		myDao = dao;
	}
    
    public UserResource() {
	}

	@GET
    @Timed
    @Path("/{username}")
    //Example: GET http://localhost:9000/users/111
    public User getUser(@PathParam("username") String username) {
    	User user = userDao.get(username);
		return user;
    }

    @GET
    @Path("/{username}/swimmers")
    //Example: DELETE http://localhost:9000/users/niske
    public User getSwimmers(@PathParam("username") String username) {
		User user = userDao.get(username);
		if(user != null) {
			userDao.remove(username);
		}
    	return user;
    }

	@POST
	@Timed
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
	// Example: POST
	public User login(User inUser) {
//		myDao.findNameById(inUser.getUsername());
//		User user = userDao.get(inUser.getUsername());
//		
//		if(user.getPassword().equals(inUser.getPassword())){
//			return user;
//		}
//		return null;
		Optional<User> res;
		try {
			res = SwimmersApplication.getCachedAuthenticator().authenticate(
					inUser.toCredentials());
		} catch (AuthenticationException e) {
			System.err.println("User " + inUser.getUsername() + " could not be authenticated");
			return null;
		}
		if (res.isPresent()) {
//			return new UserAuthResponse(res.get());
			return res.get();
		} else {
			return null;
//			throw new MessageWrappedException(
//					"Unable to log in with those credentials!");
		}		
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public User saveUser(User user) {
    	String username = user.getUsername();
		if(userDao.get(username) != null) {
			return null;
		}
    	userDao.put(username, user);
        return user;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public User updatePassword(User user) {
    	String username = user.getUsername();
    	if(userDao.get(username) == null) {
    		return null;
    	}
    	userDao.put(username, user);
        return user;
    }

    @DELETE
    @Timed
    @Path("/{username}")
    //Example: DELETE http://localhost:9000/users/niske
    public User deleteUser(@PathParam("username") String username) {
		User user = userDao.get(username);
		if(user != null) {
			userDao.remove(username);
		}
    	return user;
    }


}