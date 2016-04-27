package se.skeppstedt.swimmer.octo;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class OctoDocumentProvider implements DocumentProvider {
	
	private String urlString;
	
	public OctoDocumentProvider(String urlString) {
		this.urlString = urlString;
	}

	@Override
	public Document getDocument() throws IOException {
		return Jsoup.parse(new URL(urlString), 3000);
	}

}
