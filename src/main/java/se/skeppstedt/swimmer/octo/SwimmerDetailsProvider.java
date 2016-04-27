package se.skeppstedt.swimmer.octo;


public class SwimmerDetailsProvider extends OctoDocumentProvider {
	
	public static String swimmerDetailsUrl = "http://www.octoopen.se/index.php?r=swimmer/view&id=";
	
	private static String createDetailsUrl(String swimmerId) {
		return swimmerDetailsUrl + swimmerId;
	}

	public SwimmerDetailsProvider(String swimmerId) {
		super(createDetailsUrl(swimmerId));
	}

}
