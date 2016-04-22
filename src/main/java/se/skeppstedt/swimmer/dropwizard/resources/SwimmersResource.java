package se.skeppstedt.swimmer.dropwizard.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import se.skeppstedt.swimmer.dropwizard.api.Swimmer;

import com.codahale.metrics.annotation.Timed;

@Produces(MediaType.APPLICATION_JSON)
@Path("/swimmers")
public class SwimmersResource {
//    private final AtomicLong counter;
	private static HashMap<String, Swimmer> repo = new HashMap<>();
	static {
		repo.put("000", new Swimmer("000", "First Swimmer", "Täby", "1999"));
	}

    @GET
    @Timed
    @Path("/{id}")
    //Example: GET http://localhost:9000/swimmers/111
    public Swimmer getSwimmer(@PathParam("id") String id) {
    	Swimmer swimmer = repo.get(id);
		return swimmer;
    }

    @GET
    @Timed
    //Example: GET http://localhost:9000/swimmers
    public Collection<Swimmer> getSwimmers() {
    	return repo.values();
    }

	@POST
	@Timed
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
	// Example: POST
	public Collection<Swimmer> searchSwimmer(Swimmer swimmerSearch) {
		Collection<Swimmer> values = repo.values();
		Stream<Swimmer> stream = values.stream();
		List<Swimmer> collected = stream
			.filter(s -> swimmerSearch.getName() == null || swimmerSearch.getName().isEmpty() || s.getName().contains(swimmerSearch.getName()))
			.filter(s -> swimmerSearch.getClub() == null || swimmerSearch.getClub().isEmpty() || s.getClub().contains(swimmerSearch.getClub()))
			.filter(s -> swimmerSearch.getId() == null || swimmerSearch.getId().isEmpty() || s.getId().equals(swimmerSearch.getId()))
			.filter(s -> swimmerSearch.getYearOfBirth() == null || swimmerSearch.getYearOfBirth().isEmpty() || s.getYearOfBirth().equals(swimmerSearch.getYearOfBirth()))
			.collect(Collectors.toList());
		return collected;
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Swimmer saveSwimmer(Swimmer swimmer) {
    	String id = swimmer.getId();
    	repo.put(id, swimmer);
        return swimmer;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Swimmer updateSwimmer(Swimmer swimmer) {
    	String id = swimmer.getId();
    	if(repo.get(id) == null) {
    		return null;
    	}
    	repo.put(id, swimmer);
        return swimmer;
    }

    @DELETE
    @Timed
    @Path("/{id}")
    //Example: DELETE http://localhost:9000/swimmers/111
    public Collection<Swimmer> deleteSwimmer(@PathParam("id") String id) {
    	repo.remove(id);
    	return repo.values();
    }


}