package se.skeppstedt.swimmer.dropwizard.resources;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import se.skeppstedt.swimmer.dropwizard.api.Competition;
import se.skeppstedt.swimmer.dropwizard.api.ProgramEvent;
import se.skeppstedt.swimmer.dropwizard.api.Session;
import se.skeppstedt.swimmer.octo.LiveTimingParser;

@Produces(MediaType.APPLICATION_JSON)
@Path("/competitions")
public class CompetitionResource {

	@Context private ResourceContext rc;
	
	@Inject
	private LiveTimingParser parser;
	
	//private static HashMap<String, Competition> competitionDao = new HashMap<>();

    @GET
    @Timed
    @Path("/{id}")
    //Example: GET http://localhost:9000/swimmers/111
    public Competition getCompetition(@PathParam("id") String id) {
		return getCompetitionFromId(id);
    }

    //Helper method
	private Competition getCompetitionFromId(String competitionId) {
		return getCompetitions().stream().filter(comp -> comp.getId().equals(competitionId)).findFirst().orElse(null);
	}

    @GET
    @Timed
    @Path("/{id}/sessions")
    //Example: GET http://localhost:9000/swimmers/111
    public Set<Session> getSessions(@PathParam("id") String id) {
		Competition competition = getCompetitionFromId(id);
		Competition competitionWithSessions = parser.loadCompetition(competition);
		for (Session session : competitionWithSessions.getSessions()) {
			competition.addSession(session);
		}
		return competition.getSessions();
    }

    @GET
    @Timed
    @Path("/{id}/sessions/{sessionId}")
    //Example: GET http://localhost:9000/competitions/2703/sessions/1
    public Session getSession(@PathParam("id") String id, @PathParam("sessionId") int sessionId) {
		Competition competition = getCompetitionFromId(id);
		parser.loadCompetition(competition);
		return competition.getSessions().stream().filter(session -> session.getSessionId() == sessionId).findFirst().get();
    }

    @GET
    @Timed
    @Path("/{id}/events/")
    //Example: GET http://localhost:9000/competitions/2703/sessions/1
    public Set<ProgramEvent> getEvents(@PathParam("id") String id) {
		Competition competition = getCompetitionFromId(id);
		parser.loadCompetition(competition);
		Set<ProgramEvent> events = new HashSet<>();
		for (Session session : competition.getSessions()) {
			events.addAll(session.getSessionEvents());
		}
		return events;
    }

    @GET
    @Timed
    //Example: GET http://localhost:9000/swimmers
    public Set<Competition> getCompetitions() {
    	return parser.getCompetitions();
    }

//	@POST
//	@Timed
//    @Path("/search")
//    @Consumes(MediaType.APPLICATION_JSON)
//	// Example: POST
//	public Collection<Swimmer> searchSwimmer(@Auth User user, Swimmer swimmerSearch) {
//		if(swimmerSearch == null) {
//			return Collections.emptyList();
//		}
//		return parser.searchSwimmers(swimmerSearch.getFirstName(), swimmerSearch.getLastName(), swimmerSearch.getClub(), swimmerSearch.getYearOfBirth());
//	}
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Timed
//    public Swimmer saveSwimmer(Swimmer swimmer) {
//    	String id = swimmer.getId();
//    	competitionDao.put(id, swimmer);
//        return swimmer;
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Timed
//    public Swimmer updateSwimmer(Swimmer swimmer) {
//    	String id = swimmer.getId();
//    	if(competitionDao.get(id) == null) {
//    		return null;
//    	}
//    	competitionDao.put(id, swimmer);
//        return swimmer;
//    }
//
//    @DELETE
//    @Timed
//    @Path("/{id}")
//    //Example: DELETE http://localhost:9000/swimmers/111
//    public Swimmer deleteSwimmer(@PathParam("id") String id) {
//    	Swimmer deleted = competitionDao.remove(id);
//    	return deleted;
//    }
//
//
}