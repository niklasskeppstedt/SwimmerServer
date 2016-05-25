package se.skeppstedt.swimmer.octo.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import se.skeppstedt.swimmer.dropwizard.api.Competition;
import se.skeppstedt.swimmer.dropwizard.api.Event;
import se.skeppstedt.swimmer.dropwizard.api.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.ProgramEvent;
import se.skeppstedt.swimmer.dropwizard.api.Session;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.octo.LiveTimingParser;

public abstract class AbstractLiveTimingParser implements LiveTimingParser {
	
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //2012-04-15
	protected Document document;

	@Override
	public Set<Competition> getCompetitions() {
		try {
			document = getRootDocument();
		} catch (IOException e) {
			System.err.println("Could not get document");
			return null;
		}
		Set<Competition> competitions = new HashSet<>();
		Elements options = document.select("option");
		for (Element option : options) {
			String competitionName = option.ownText();
			String competitionId = option.attr("value");
			boolean added = competitions.add(new Competition(competitionId, competitionName));
			if(!added) {
				System.err.println("Could not add competition, already in set");
			}
		}
		return competitions;
	}
	
	@Override
	public Competition loadCompetition(Competition competition) {
		int sessionCounter = 1;
		while(true) {
			try {
				boolean eventsfound = false;
				document = getSessionDocument(competition.getId(), sessionCounter);
				Elements table = document.select("tbody");
				Elements eventRows = table.select("tr");
				Session session = new Session(sessionCounter);
				for (Element element : eventRows) {
					Elements spec = element.select("td.spec");
					Elements specalt = element.select("td.specalt");
					if(!spec.isEmpty() || !specalt.isEmpty()) {
						eventsfound = true;
						session.addProgramEvent(new ProgramEvent(spec.isEmpty() ? specalt.first().ownText() : spec.first().ownText(), element.child(1).ownText()));
					}
				}
				if(!eventsfound) {
					break;
				}
				competition.addSession(session);
				++sessionCounter;
			} catch (IOException e) {
				System.err.println("Could not get document");
				return null;
			}
		}
		return competition;
	}
	
	protected abstract Document getSessionDocument(String competitionId, int sessionCounter) throws IOException, MalformedURLException;
	protected abstract Document getCompetitionResultsDocument(String competitionId) throws IOException, MalformedURLException;
	protected abstract Document getCompetitionEventsDocument(String competitionId) throws IOException, MalformedURLException;
	protected abstract Document getRootDocument() throws IOException;
	
	protected String extractOctoId() {
		Element hook = document.select("div.language-widget").first();
		FormElement form = (FormElement) hook.getElementsByTag("form").first();
		String action = form.attr("action");
		String octoId = action.substring(action.indexOf("id=") + 3);
		return octoId;
	}
	
	protected void extractPersonalBests(Swimmer swimmer) {
		Elements odds = document.select("tr.odd");
		Elements evens = document.select("tr.even");
		odds.addAll(evens);
		for (Element element : odds) {
			String competition = extractCompetition(element);
			Date date = extractDate(element);
			Duration time = extractTime(element);
			Event event = Event.fromCode(extractLinkParameter(element, "event"));
			swimmer.addPersonalBest(new PersonalBest(event, time, competition, date, swimmer.getId()));
		}
	}

	protected String extractLinkParameter(Element element, String parameterName) {
		String[] link = element.select("a").attr("href").split(parameterName +"=");
		return link[1];
	}

	protected Duration extractTime(Element element) {
		String timeString = element.select("td").get(3).ownText().replace('.', ':').trim();
		String time = extractFromTextWithPattern(timeString, "\\d{2}:\\d{2}:\\d{2}");
		String[] split = time.split(":");
		return Duration.ofMinutes(Long.valueOf(split[0])).plusSeconds(Long.valueOf(split[1])).plusMillis(Long.valueOf(split[2]) * 10);
	}

	protected Date extractDate(Element element) {
		String date = element.select("td").get(2).ownText().trim();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			//TODO Log
			System.err.println("Could not parse date string " + date + "with formatter " + format.toPattern());
			return null;
		}
	}

	protected String extractCompetition(Element element) {
		return element.select("td").get(1).ownText().trim();
	}

	protected String extractSwimmerYearOfBirth() {
		String dateOfBirth = null;
		String bornText = document.getElementsContainingOwnText("Född").text();
		dateOfBirth = extractFromTextWithPattern(bornText, "\\d{4}");
		return dateOfBirth;
	}

	protected String extractSwimmerClub() {
		String clubConst = "rening:";
		String swimmingClub = document.getElementsContainingOwnText(clubConst).text();
		//Född: 2003 Förening: Stockholms Kappsimningsklubb Licens: AG3903
		swimmingClub = swimmingClub.substring(swimmingClub.indexOf(clubConst) + clubConst.length(), swimmingClub.indexOf("Licens")).trim();
		return swimmingClub;
	}

	protected String extractFromTextWithPattern(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()) {
			return matcher.group();
		}
		return "Not found";
		
	}

	protected String extractSwimmerName() {
		String name;
		name = document.select("h2").text();
		return name;
	}	

}
