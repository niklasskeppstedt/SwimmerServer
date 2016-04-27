package se.skeppstedt.swimmer.octo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchSwimmerProvider extends OctoDocumentProvider {
	
	public static String swimmerSearchUrlTemplate = "http://www.octoopen.se/index.php?r=swimmer/index&Swimmer[first_name]=afirstname&Swimmer[last_name]=alastname";
	private static String createUrlString(String firstName, String lastName) {
		String adjustedSearchUrl = swimmerSearchUrlTemplate;
		try {
			adjustedSearchUrl = adjustedSearchUrl.replace("afirstname", URLEncoder.encode(firstName, "UTF-8"));
			adjustedSearchUrl = adjustedSearchUrl.replace("alastname", URLEncoder.encode(lastName, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			//Never happens, UTF-8 is always supported
		}
		return adjustedSearchUrl;
	}
 	
	public SearchSwimmerProvider(String firstName, String lastName, String club, String yearOfBirth) {
		super(createUrlString(firstName, lastName));
	}

}
