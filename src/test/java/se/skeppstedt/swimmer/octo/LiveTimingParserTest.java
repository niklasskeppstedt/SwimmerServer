package se.skeppstedt.swimmer.octo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;

import se.skeppstedt.swimmer.dropwizard.api.EventStart;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.octo.impl.LiveTimingParserImpl;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;

public class LiveTimingParserTest {
	
	private static final int ELIAS_START_INDEX = 12;
	private static final int SALA_DOPPET_ID = 2703;
	private static final int BACKSTROKE_EVENT = 3;
	@Test
	public void whenGettingStartListForComp2703Event3Start12EliasIsReturned() throws Exception {
		LiveTimingParser liveTimingParser = new LiveTimingParserImpl();
		Set<EventStart> startList = liveTimingParser.getStartList(SALA_DOPPET_ID, BACKSTROKE_EVENT);
		EventStart eventStart = startList.stream().filter(start -> start.getStartIndex() == ELIAS_START_INDEX).findFirst().get();
		assertEquals("Elias", eventStart.getSwimmerFirstName());
		assertEquals("Skeppstedt", eventStart.getSwimmerLastName());
		
		OctoParser octoParser = new OctoOpenParserImpl();
		Set<Swimmer> searchResult = octoParser.searchSwimmers(eventStart.getSwimmerFirstName(), eventStart.getSwimmerLastName(), "", "", eventStart.getSwimmerLicens());
		if(searchResult.isEmpty()) {
			fail("Nothing found when searching in octo on live timing results");
		}
		searchResult.forEach(s -> {
			assertEquals("Elias", s.getFirstName());
			assertEquals("Skeppstedt", s.getLastName());
			assertEquals("Stockholms Kappsimningsklubb", s.getClub());
			assertEquals("297358", s.getId());
			assertEquals("2003", s.getYearOfBirth());
			assertEquals("AG3903", s.getLicence());
		});
		
	}
}
