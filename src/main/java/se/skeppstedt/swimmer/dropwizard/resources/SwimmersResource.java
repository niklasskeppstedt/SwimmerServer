package se.skeppstedt.swimmer.dropwizard.resources;

import io.dropwizard.auth.Auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import se.skeppstedt.swimmer.dropwizard.api.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.dropwizard.api.User;
import se.skeppstedt.swimmer.octo.OctoParser;

import com.codahale.metrics.annotation.Timed;

@Produces(MediaType.APPLICATION_JSON)
@Path("/swimmers")
public class SwimmersResource {

	@Context private ResourceContext rc;
	
	@Inject
	private OctoParser parser;
	
	private static HashMap<String, Swimmer> swimmerDao = new HashMap<>();

    @GET
    @Timed
    @Path("/{id}")
    //Example: GET http://localhost:9000/swimmers/111
    public Swimmer getSwimmer(@Auth User user, @PathParam("id") String id) {
		Swimmer swimmer = swimmerDao.get(id);
		PersonalBestResource resource = rc.getResource(PersonalBestResource.class);
		List<PersonalBest> personalBestsForSwimmer = resource.getPersonalBestsForSwimmer(id);
		swimmer.setPersonalBests(personalBestsForSwimmer);
		return swimmer;
    }

    @GET
    @Timed
    //Example: GET http://localhost:9000/swimmers
    public Collection<Swimmer> getSwimmers() {
    	return swimmerDao.values();
    }

	@POST
	@Timed
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
	// Example: POST
	public Collection<Swimmer> searchSwimmer(@Auth User user, Swimmer swimmerSearch) {
		if(swimmerSearch == null) {
			return Collections.emptyList();
		}
		return parser.searchSwimmers(swimmerSearch.getFirstName(), swimmerSearch.getLastName(), swimmerSearch.getClub(), swimmerSearch.getYearOfBirth());
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Swimmer saveSwimmer(Swimmer swimmer) {
    	String id = swimmer.getId();
    	swimmerDao.put(id, swimmer);
        return swimmer;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Swimmer updateSwimmer(Swimmer swimmer) {
    	String id = swimmer.getId();
    	if(swimmerDao.get(id) == null) {
    		return null;
    	}
    	swimmerDao.put(id, swimmer);
        return swimmer;
    }

    @DELETE
    @Timed
    @Path("/{id}")
    //Example: DELETE http://localhost:9000/swimmers/111
    public Swimmer deleteSwimmer(@PathParam("id") String id) {
    	Swimmer deleted = swimmerDao.remove(id);
    	return deleted;
    }


}