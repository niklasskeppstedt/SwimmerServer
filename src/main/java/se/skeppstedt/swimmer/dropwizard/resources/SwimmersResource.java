package se.skeppstedt.swimmer.dropwizard.resources;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import se.skeppstedt.swimmer.dropwizard.api.Swimmer;

@Produces(MediaType.APPLICATION_JSON)
@Path("/swimmers")
public class SwimmersResource {
//    private final AtomicLong counter;
	private static HashMap<String, Swimmer> repo = new HashMap<>();
	static {
		repo.put("000", new Swimmer("000", "FirstSwimmer", "Täby", "1999"));
	}

    @GET
    @Timed
    @Path("/{id}")
    //Example: GET http://localhost:9000/swimmers/111
    public Swimmer getSwimmer(@PathParam("id") String id) {
    	return repo.get(id);
    }

    @GET
    @Timed
    //Example: GET http://localhost:9000/swimmers
    public Collection<Swimmer> getSwimmers() {
    	return repo.values();
    }

//    @POST
//    @Timed
//    //Example: POST http://localhost:9000/swimmers?id=111&name=Niklas&club=SKK&yob=1999
//    public Collection<Swimmer> updateSwimmer(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("club") String club, @QueryParam("yob") String yearOfBirth) {
//    	repo.put(id, new Swimmer(id, name, club, yearOfBirth));
//        return repo.values();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    //Example: POST http://localhost:9000/swimmers?id=111&name=Niklas&club=SKK&yob=1999
    public Swimmer saveSwimmer(Swimmer swimmer) {
    	String id = swimmer.getId();
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