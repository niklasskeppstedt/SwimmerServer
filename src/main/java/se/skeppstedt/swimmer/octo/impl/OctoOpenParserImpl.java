package se.skeppstedt.swimmer.octo.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class OctoOpenParserImpl extends AbstractOctoParser {

	public static String swimmerSearchUrlTemplate = "http://www.octoopen.se/index.php?r=swimmer/index&Swimmer[first_name]=afirstname&Swimmer[last_name]=alastname";
	public static String swimmerDetailsUrl = "http://www.octoopen.se/index.php?r=swimmer/view&id=";

	public OctoOpenParserImpl() {
	}

	protected Document getDocument(String swimmerId) throws IOException,
	MalformedURLException {
		return Jsoup.parse(new URL(swimmerDetailsUrl + swimmerId), 3000);
	}

	protected Document getDocument(String firstName, String lastName, String club, String yearOfBirth) throws IOException,
	MalformedURLException {
		String adjustedSearchUrl = swimmerSearchUrlTemplate;
		adjustedSearchUrl = adjustedSearchUrl.replace("afirstname", URLEncoder.encode(firstName, "UTF-8"));
		adjustedSearchUrl = adjustedSearchUrl.replace("alastname", URLEncoder.encode(lastName, "UTF-8"));
		return Jsoup.parse(new URL(adjustedSearchUrl), 3000);	
	}
}