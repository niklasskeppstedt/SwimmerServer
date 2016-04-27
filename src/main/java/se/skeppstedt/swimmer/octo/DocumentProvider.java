package se.skeppstedt.swimmer.octo;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface DocumentProvider {
	
	Document getDocument() throws IOException;

}
