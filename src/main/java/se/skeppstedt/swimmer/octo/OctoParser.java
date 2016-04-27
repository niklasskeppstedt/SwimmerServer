package se.skeppstedt.swimmer.octo;

import java.io.IOException;
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

import se.skeppstedt.swimmer.dropwizard.api.Event;
import se.skeppstedt.swimmer.dropwizard.api.PersonalBest;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;

public class OctoParser {
	
	DocumentProvider documentProvider;

	private Document document;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //2012-04-15
	
	public OctoParser(DocumentProvider documentProvider) {
		this.documentProvider = documentProvider;
	}

	public Swimmer getSwimmerDetails() {
		Swimmer swimmer = null;
		try {
			document = documentProvider.getDocument();
		} catch (IOException e) {
			System.err.println("Could not parse swimmer url");
		}
		String name = extractSwimmerName();
		String yearOfBirth = extractSwimmerYearOfBirth();
		String swimmingClub = extractSwimmerClub();
		String octoId = extractOctoId();
		swimmer = new Swimmer(octoId, name, swimmingClub, yearOfBirth);
		extractPersonalBests(swimmer);
		
		return swimmer;
	}

	public Set<Swimmer> searchSwimmers() {
		try {
			document = documentProvider.getDocument();
		} catch (IOException e) {
			System.err.println("Could not parse swimmer url");
		}
		Set<Swimmer> swimmers = new HashSet<>();
		Elements odd = document.select("tr.odd");
		odd.addAll(document.select("tr.even"));
		for (Element element : odd) {
			String firstName = element.select("td.name-column").first().ownText();
			String lastName = element.select("td").get(1).ownText();
			String yearOfBirth = element.select("td").get(3).ownText();
			String swimmingClub = element.select("td").get(4).ownText();
			String octoId = extractLinkParameter(element, "id");
			boolean added = swimmers.add(new Swimmer(octoId, firstName + " " + lastName, swimmingClub, yearOfBirth));
			if(!added) {
				System.err.println("Could not add swimmer, already in set");
			}
		}
		return swimmers;
	}

	private String extractOctoId() {
		Element hook = document.select("div.language-widget").first();
		FormElement form = (FormElement) hook.getElementsByTag("form").first();
		String action = form.attr("action");
		String octoId = action.substring(action.indexOf("id=") + 3);
		return octoId;
	}
	
	private void extractPersonalBests(Swimmer swimmer) {
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

	private String extractLinkParameter(Element element, String parameterName) {
		String[] link = element.select("a").attr("href").split(parameterName +"=");
		return link[1];
	}

	private Duration extractTime(Element element) {
		String timeString = element.select("td").get(3).ownText().replace('.', ':').trim();
		String time = extractFromTextWithPattern(timeString, "\\d{2}:\\d{2}:\\d{2}");
		String[] split = time.split(":");
		return Duration.ofMinutes(Long.valueOf(split[0])).plusSeconds(Long.valueOf(split[1])).plusMillis(Long.valueOf(split[2]) * 10);
	}

	private Date extractDate(Element element) {
		String date = element.select("td").get(2).ownText().trim();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			//TODO Log
			System.err.println("Could not parse date string " + date + "with formatter " + format.toPattern());
			return null;
		}
	}

	private String extractCompetition(Element element) {
		return element.select("td").get(1).ownText().trim();
	}

	private String extractSwimmerYearOfBirth() {
		String dateOfBirth = null;
		String bornText = document.getElementsContainingOwnText("Född").text();
		dateOfBirth = extractFromTextWithPattern(bornText, "\\d{4}");
		return dateOfBirth;
	}

	private String extractSwimmerClub() {
		String clubConst = "rening:";
		String swimmingClub = document.getElementsContainingOwnText(clubConst).text();
		//Född: 2003 Förening: Stockholms Kappsimningsklubb Licens: AG3903
		swimmingClub = swimmingClub.substring(swimmingClub.indexOf(clubConst) + clubConst.length(), swimmingClub.indexOf("Licens")).trim();
		return swimmingClub;
	}

	private String extractFromTextWithPattern(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if(matcher.find()) {
			return matcher.group();
		}
		return "Not found";
		
	}

	private String extractSwimmerName() {
		String name;
		name = document.select("h2").text();
		return name;
	}

}