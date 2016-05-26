package se.skeppstedt.swimmer.octo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import se.skeppstedt.swimmer.octo.impl.AbstractOctoParser;

public class FileOctoParser extends AbstractOctoParser {
	String fileName = null;

	public FileOctoParser() {
	}
	
	@Override
	protected Document getDocument(String swimmerId) throws IOException,
			MalformedURLException {
		fileName = "swimmerDetails.html";
		return getDocument();
	}
	
	public void setEmptySearch() {
		this.fileName = "emptySearch.html";
	}

	@Override
	protected Document getDocument(String firstName, String lastName,
			String club, String yearOfBirth) throws IOException {
		if(fileName == null) {
			fileName = "searchResult.html";
		}
		return getDocument();
	}

	private Document getDocument() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return Jsoup.parse(file, "UTF-8");
	}
	
}
