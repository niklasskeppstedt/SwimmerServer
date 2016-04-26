package se.skeppstedt.swimmer.octo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import se.skeppstedt.swimmer.dropwizard.api.Event;
import se.skeppstedt.swimmer.dropwizard.api.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;

public class OctoParserTest {
	@Test
	public void whenFetchingEliasSkeppstedtASwimmerWithPersonalBestsIsReturned() throws Exception {
		OctoParser testee = new OctoParser(new FileDocumentProvider("swimmerDetails.html"));
		Swimmer swimmer = testee.getSwimmerDetails();
		assertEquals("Elias Skeppstedt", swimmer.getName());
		assertEquals("Stockholms Kappsimningsklubb", swimmer.getClub());
		assertEquals("297358", swimmer.getId());
		assertEquals("2003", swimmer.getYearOfBirth());
		List<PersonalBest> personalBests = swimmer.getPersonalBests();
		//Test one of the personal bests, freestyle 25
		personalBests.forEach(pb -> {
			if(pb.getEvent().equals(Event.FREESTYLE_25)) {
				assertEquals("Freestyle Cup", pb.getCompetition());
				assertEquals("Sun Apr 15 00:00:00 CEST 2012", pb.getDate().toString());
				assertEquals("PT25.72S", pb.getDuration().toString());
				assertEquals(Event.FREESTYLE_25, pb.getEvent());
				assertEquals("297358", pb.getSwimmerId());
				assertEquals("00:25:72", pb.getTime());
			}});
	}

	@Test
	public void whenFetchingOnlineEliasSkeppstedtASwimmerWithPersonalBestsIsReturned() throws Exception {
		OctoParser testee = new OctoParser(new OctoDocumentProvider(OctoDocumentProvider.createDetailsUrl("297358")));
		Swimmer swimmer = testee.getSwimmerDetails();
		assertEquals("Elias Skeppstedt", swimmer.getName());
		assertEquals("Stockholms Kappsimningsklubb", swimmer.getClub());
		assertEquals("297358", swimmer.getId());
		assertEquals("2003", swimmer.getYearOfBirth());
		List<PersonalBest> personalBests = swimmer.getPersonalBests();
		//Test one of the personal bests, freestyle 25
		personalBests.forEach(pb -> {
			if(pb.getEvent().equals(Event.FREESTYLE_25)) {
				assertEquals("Freestyle Cup", pb.getCompetition());
				assertEquals("Sun Apr 15 00:00:00 CEST 2012", pb.getDate().toString());
				assertEquals("PT25.72S", pb.getDuration().toString());
				assertEquals(Event.FREESTYLE_25, pb.getEvent());
				assertEquals("297358", pb.getSwimmerId());
				assertEquals("00:25:72", pb.getTime());
			}});
	}

	@Test
	public void whenSearchingEliasSkeppstedtAListWithOneSwimmerIsReturned() {
		OctoParser testee = new OctoParser(new FileDocumentProvider("searchResult.html"));
		Set<Swimmer> searchResult = testee.search();
		searchResult.forEach(s -> {
			//Test parsing for one known swimmer
			if(s.getId().equals("297358"))
				assertTrue(s.getName().contains("Elias") && s.getName().contains("Skeppstedt"));
				assertEquals("Stockholms Kappsimningsklubb", s.getClub());
				assertEquals("297358", s.getId());
				assertEquals("2003", s.getYearOfBirth());
			});
	}
	
	@Test
	public void whenSearchingOnlineEliasSkeppstedtAListWithOneSwimmerIsReturned() throws UnsupportedEncodingException {
		OctoParser testee = new OctoParser(new OctoDocumentProvider(OctoDocumentProvider.createSearchUrl("Elias", "Skeppstedt", "", "") ));
		Set<Swimmer> searchResult = testee.search();
		searchResult.forEach(s -> {
			//Test parsing for one known swimmer
			if(s.getId().equals("297358"))
				assertTrue(s.getName().contains("Elias") && s.getName().contains("Skeppstedt"));
				assertEquals("Stockholms Kappsimningsklubb", s.getClub());
				assertEquals("297358", s.getId());
				assertEquals("2003", s.getYearOfBirth());
			});
	}

}
