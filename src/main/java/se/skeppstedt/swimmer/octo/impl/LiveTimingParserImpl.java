package se.skeppstedt.swimmer.octo.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import se.skeppstedt.swimmer.dropwizard.api.Competition;
import se.skeppstedt.swimmer.dropwizard.api.EventStart;
import se.skeppstedt.swimmer.dropwizard.api.ProgramEvent;

public class LiveTimingParserImpl extends AbstractLiveTimingParser {

	public static String rootUrl = "http://www.livetiming.se";
	public static String competitionProgramUrl = "http://www.livetiming.se/program.php?cid=";
	public static String competitionResultsUrl = "http://www.livetiming.se/results.php?cid=";
	public static String competitionEventsUrl = "http://www.livetiming.se/events.php?cid=";
	

	public LiveTimingParserImpl() {
	}

	protected Document getSessionDocument(int competitionId, int session) throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(competitionProgramUrl + competitionId + "&session=" + session), 3000);
	}

	protected Document getCompetitionResultsDocument(int competitionId) throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(competitionResultsUrl + competitionId), 3000);
	}

	protected Document getCompetitionEventsDocument(int competitionId) throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(competitionEventsUrl + competitionId), 3000);
	}

	protected Document getStartListDocument(int competitionId, int eventId) throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(competitionProgramUrl + competitionId + "&type=STS&event=" + eventId), 3000);
	}

	protected Document getRootDocument() throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(rootUrl), 3000);	
	}
	
	public static void main(String[] args) {
		LiveTimingParserImpl parser = new LiveTimingParserImpl();
		Set<Competition> competitions = parser.getCompetitions();
		Competition loadedCompetition = parser.getCompetition(2703);
		Set<EventStart> startList = parser.getStartList(loadedCompetition.getId(), loadedCompetition.getEvents().stream().filter(event -> event.getEventId() == 1).findFirst().get().getEventId());
		System.out.println(startList);
	}
}