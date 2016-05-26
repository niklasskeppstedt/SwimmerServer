package se.skeppstedt.swimmer.dropwizard.resources;

//import java.util.Iterator;
//import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

import se.skeppstedt.swimmer.dropwizard.api.livetiming.Competition;
import se.skeppstedt.swimmer.dropwizard.api.livetiming.EventStart;
import se.skeppstedt.swimmer.dropwizard.api.livetiming.ProgramEvent;
import se.skeppstedt.swimmer.dropwizard.api.livetiming.Session;
import se.skeppstedt.swimmer.dropwizard.api.octo.Swimmer;
import se.skeppstedt.swimmer.octo.LiveTimingParser;

@Produces(MediaType.APPLICATION_JSON)
@Path("/competitions")
public class CompetitionResource {

	@Context private ResourceContext rc;
	
	@Inject
	private LiveTimingParser parser;

    @GET
    @Timed
    @Path("/{competitionId}")
    //Example: GET http://localhost:9000/competitions/111
    public Competition getCompetition(@PathParam("competitionId") int competitionId) {
		return parser.getCompetition(competitionId);
    }

    @GET
    @Timed
    @Path("/{competitionId}/sessions")
    //Example: GET http://localhost:9000/swimmers/111
    public Set<Session> getSessions(@PathParam("competitionId") int competitionId) {
		Competition competition = parser.getCompetition(competitionId);
		for (Session session : competition.getSessions()) {
			competition.addSession(session);
		}
		return competition.getSessions();
    }

    @GET
    @Timed
    @Path("/{competitionId}/sessions/{sessionId}")
    //Example: GET http://localhost:9000/competitions/2703/sessions/1
    public Session getSession(@PathParam("competitionId") int competitionId, @PathParam("sessionId") int sessionId) {
		Competition competition = parser.getCompetition(competitionId);
		return competition.getSessions().stream().filter(session -> session.getSessionId() == sessionId).findFirst().get();
    }

    @GET
    @Timed
    @Path("/{competitionId}/events")
    //Example: GET http://localhost:9000/competitions/2703/sessions/1
    public Set<ProgramEvent> getEvents(@PathParam("competitionId") int competitionId) {
		Competition competition = parser.getCompetition(competitionId);
		Set<ProgramEvent> events = new TreeSet<>();
		for (Session session : competition.getSessions()) {
			events.addAll(session.getEvents());
		}
		return events;
    }

    @GET
    @Timed
    @Path("/{competitionId}/events/{eventId}")
    //Example: GET http://localhost:9000/competitions/2703/events/1
    public ProgramEvent getEvent(@PathParam("competitionId") int competitionId, @PathParam("eventId") int eventId) {
    	ProgramEvent programEvent = getEvents(competitionId).stream().filter(event -> event.getEventId() == eventId).findFirst().orElse(null);
    	if(programEvent == null) {
    		return null;
    	}
    	programEvent.setStartList(parser.getStartList(competitionId, eventId));
    	return programEvent;
    }

    @GET
    @Timed
    @Path("/{competitionId}/events/{eventId}/{startIndex}")
    //Example: GET http://localhost:9000/competitions/2703/events/1
    public EventStart getEventStart(@PathParam("competitionId") int competitionId, @PathParam("eventId") int eventId, @PathParam("startIndex") int startIndex) {
    	EventStart eventStart = getEvent(competitionId, eventId).getStartList().stream().filter(start -> start.getStartIndex() == startIndex).findFirst().orElse(null);
    	if(eventStart == null) {
    		return null;
    	}
    	return eventStart;
    }
    
    @GET
    @Timed
    @Path("/{competitionId}/events/{eventId}/{startIndex}/swimmer")
    //Example: GET http://localhost:9000/competitions/2703/events/1/swimmer
    public Swimmer getSwimmer(@PathParam("competitionId") int competitionId, @PathParam("eventId") int eventId, @PathParam("startIndex") int startIndex) {
		SwimmersResource resource = rc.getResource(SwimmersResource.class);
		Swimmer searchTerm = new Swimmer("", "Elias", "Skeppstedt", "", "", "");
		Swimmer swimmer = resource.searchSwimmer(null, searchTerm).stream().findFirst().orElse(null);
		return swimmer;
    }



    @GET
    @Timed
    //Example: GET http://localhost:9000/swimmers
    public Set<Competition> getCompetitions() {
    	return parser.getCompetitions();
    }

}