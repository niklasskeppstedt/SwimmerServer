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

import se.skeppstedt.swimmer.dropwizard.api.octo.Event;
import se.skeppstedt.swimmer.dropwizard.api.octo.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.octo.Swimmer;
import se.skeppstedt.swimmer.octo.OctoParser;

public abstract class AbstractOctoParser implements OctoParser{
	
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //2012-04-15
	protected Document document;
	

	@Override
	public Swimmer getSwimmerDetails(String swimmerId) {
		Swimmer swimmer = null;
		try {
			document = getDocument(swimmerId);
		} catch (IOException e) {
			System.err.println("Could not get document");
			return null;
		}
		String[] names = extractSwimmerName().split(" ");
		String firstName = names[0];
		String lastName = names[1];
		String yearOfBirth = extractSwimmerYearOfBirth();
		String swimmingClub = extractSwimmerClub();
		String octoId = extractOctoId();
		String licence = extractLicence();
		swimmer = new Swimmer(octoId, firstName, lastName, swimmingClub, yearOfBirth, licence);
		extractPersonalBests(swimmer);
		
		return swimmer;
	}

	private String extractLicence() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Swimmer> searchSwimmers(String firstName, String lastName, String club, String yearOfBirth, String licence) {
		try {
			document = getDocument(firstName, lastName, club, yearOfBirth);
		} catch (IOException e) {
			System.err.println("Could not get document");
			return null;
		}
		Set<Swimmer> swimmers = new HashSet<>();
		Elements odd = document.select("tr.odd");
		odd.addAll(document.select("tr.even"));
		for (Element element : odd) {
			String aFirstName = element.select("td.name-column").first().ownText();
			String aLastName = element.select("td").get(1).ownText();
			String aYearOfBirth = element.select("td").get(3).ownText();
			String aClub = element.select("td").get(4).ownText();
			String octoId = extractLinkParameter(element, "id");
			String aLicence= element.select("td.mobile-hide").first().ownText().trim();
			if(licence.trim().isEmpty() || licence.trim().equals(aLicence)) {
				boolean added = swimmers.add(new Swimmer(octoId, aFirstName , aLastName, aClub, aYearOfBirth, aLicence));
				if(!added) {
					System.err.println("Could not add swimmer, already in set");
				}
			}
		}
		return swimmers;
	}
	
	protected abstract Document getDocument(String swimmerId) throws IOException, MalformedURLException;
	protected abstract Document getDocument(String firstName, String lastName, String club, String yearOfBirth) throws IOException;
	
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
