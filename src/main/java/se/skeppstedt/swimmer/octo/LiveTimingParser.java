package se.skeppstedt.swimmer.octo;

import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.Competition;
import se.skeppstedt.swimmer.dropwizard.api.EventStart;

public interface LiveTimingParser {
	
	public Set<Competition> getCompetitions();
//	public Competition loadCompetition(Competition competition);
	public Competition getCompetition(int competitionId);
	public Set<EventStart> getStartList(int competitionId, int eventId);
}
