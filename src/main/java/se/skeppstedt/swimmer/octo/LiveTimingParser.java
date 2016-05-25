package se.skeppstedt.swimmer.octo;

import java.util.Set;

import se.skeppstedt.swimmer.dropwizard.api.Competition;

public interface LiveTimingParser {
	
	public Set<Competition> getCompetitions();
	public Competition loadCompetition(Competition competition);
}
