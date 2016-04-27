package se.skeppstedt.swimmer.octo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class OctoDocumentProvider implements DocumentProvider {
	
	public static String swimmerDetailsUrl = "http://www.octoopen.se/index.php?r=swimmer/view&id=";
	public static String swimmerSearchUrlTemplate = "http://www.octoopen.se/index.php?r=swimmer/index&Swimmer[first_name]=afirstname&Swimmer[last_name]=alastname";
	
	//Utility method to create search url
	public static String createSearchUrl(String firstName, String lastName, String club, String yearOfBirth) {
		String adjustedSearchUrl = swimmerSearchUrlTemplate;
		try {
			adjustedSearchUrl = adjustedSearchUrl.replace("afirstname", URLEncoder.encode(firstName, "UTF-8"));
			adjustedSearchUrl = adjustedSearchUrl.replace("alastname", URLEncoder.encode(lastName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//Never happens, UTF-8 is always supported
		}
		return adjustedSearchUrl;
	}

	public static String createDetailsUrl(String id) {
		return swimmerDetailsUrl + id;
	}
	
	private String urlString;
	
	public OctoDocumentProvider(String urlString) {
		this.urlString = urlString;
	}

	@Override
	public Document getDocument() throws IOException {
		return Jsoup.parse(new URL(urlString), 3000);
	}

}
