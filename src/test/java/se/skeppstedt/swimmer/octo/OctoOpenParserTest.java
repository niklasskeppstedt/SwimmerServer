package se.skeppstedt.swimmer.octo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import se.skeppstedt.swimmer.dropwizard.api.Event;
import se.skeppstedt.swimmer.dropwizard.api.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.octo.OctoParser;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;

public class OctoOpenParserTest {
	@Test
	public void whenFetchingEliasSkeppstedtASwimmerWithPersonalBestsIsReturned() throws Exception {
		OctoParser testee = new FileOctoParser("swimmerDetails.html");
		Swimmer swimmer = testee.getSwimmerDetails("297358");
		assertEquals("Elias Skeppstedt", swimmer.getFirstName());
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
		OctoParser testee = new OctoOpenParserImpl();
		Swimmer swimmer = testee.getSwimmerDetails("297358");
		assertEquals("Elias Skeppstedt", swimmer.getFirstName());
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
		OctoParser testee = new FileOctoParser("searchResult.html");
		Set<Swimmer> searchResult = testee.searchSwimmers("Elias", "Skeppstedt", "", "");
		searchResult.forEach(s -> {
			//Test parsing for one known swimmer
			if(s.getId().equals("297358"))
				assertTrue(s.getFirstName().contains("Elias") && s.getFirstName().contains("Skeppstedt"));
				assertEquals("Stockholms Kappsimningsklubb", s.getClub());
				assertEquals("297358", s.getId());
				assertEquals("2003", s.getYearOfBirth());
			});
	}
	
	@Test
	public void whenSearchingOnlineEliasSkeppstedtAListWithOneSwimmerIsReturned() throws UnsupportedEncodingException {
		OctoParser testee = new OctoOpenParserImpl();

		Set<Swimmer> searchResult = testee.searchSwimmers("Elias", "Skeppstedt", "", "");
		searchResult.forEach(s -> {
			//Test parsing for one known swimmer
			if(s.getId().equals("297358"))
				assertTrue(s.getFirstName().contains("Elias") && s.getFirstName().contains("Skeppstedt"));
				assertEquals("Stockholms Kappsimningsklubb", s.getClub());
				assertEquals("297358", s.getId());
				assertEquals("2003", s.getYearOfBirth());
			});
	}

}
