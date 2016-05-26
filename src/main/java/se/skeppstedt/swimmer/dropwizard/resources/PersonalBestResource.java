package se.skeppstedt.swimmer.dropwizard.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.skeppstedt.swimmer.dropwizard.api.octo.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.octo.Swimmer;
import se.skeppstedt.swimmer.octo.OctoParser;

import com.codahale.metrics.annotation.Timed;

@Produces(MediaType.APPLICATION_JSON)
@Path("/personalbests")
public class PersonalBestResource {
	
	@Inject
	private OctoParser parser;
	
	private static HashMap<String, List<PersonalBest>> repo = new HashMap<>();

    @GET
    @Timed
    @Path("/{swimmerid}")
    //Example: GET http://localhost:9000/personalbests/1234
    public List<PersonalBest> getPersonalBestsForSwimmer(@PathParam("swimmerid") String id) {
    	List<PersonalBest> pbs = repo.get(id);
		if(pbs == null || pbs.isEmpty()) {
			Swimmer swimmer = parser.getSwimmerDetails(id);
			repo.put(swimmer.getId(), swimmer.getPersonalBests());
			return swimmer.getPersonalBests();
		}
		return pbs;
    }

    @GET
    @Timed
    //Example: GET http://localhost:9000/personalbests
    public Collection<PersonalBest> getAllPersonalBests() {
    	Collection<PersonalBest> retVal = new ArrayList<>();
    	repo.values().forEach(pbs -> retVal.addAll(pbs));
    	return retVal;
    }

//	@POST
//	@Timed
//    @Path("/search")
//    @Consumes(MediaType.APPLICATION_JSON)
//	// Example: POST
//	public Collection<Swimmer> searchSwimmer(PersonalBest personalBest) {
//		Collection<Swimmer> values = repo.values();
//		Stream<Swimmer> stream = values.stream();
//		List<Swimmer> collected = stream
//			.filter(s -> swimmerSearch.getName() == null || swimmerSearch.getName().isEmpty() || s.getName().contains(swimmerSearch.getName()))
//			.filter(s -> swimmerSearch.getClub() == null || swimmerSearch.getClub().isEmpty() || s.getClub().contains(swimmerSearch.getClub()))
//			.filter(s -> swimmerSearch.getId() == null || swimmerSearch.getId().isEmpty() || s.getId().equals(swimmerSearch.getId()))
//			.filter(s -> swimmerSearch.getYearOfBirth() == null || swimmerSearch.getYearOfBirth().isEmpty() || s.getYearOfBirth().equals(swimmerSearch.getYearOfBirth()))
//			.collect(Collectors.toList());
//		return collected;
//	}

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Timed
//    public PersonalBest savePersonalBest(PersonalBest personalBest) {
//    	String id = swimmer.getId();
//    	repo.put(id, swimmer);
//        return swimmer;
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Timed
//    public Swimmer updateSwimmer(Swimmer swimmer) {
//    	String id = swimmer.getId();
//    	if(repo.get(id) == null) {
//    		return null;
//    	}
//    	repo.put(id, swimmer);
//        return swimmer;
//    }

//    @DELETE
//    @Timed
//    @Path("/{id}")
//    //Example: DELETE http://localhost:9000/swimmers/111
//    public Collection<Swimmer> deleteSwimmer(@PathParam("id") String id) {
//    	repo.remove(id);
//    	return repo.values();
//    }


}