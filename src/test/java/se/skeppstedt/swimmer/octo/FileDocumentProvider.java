package se.skeppstedt.swimmer.octo;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class FileDocumentProvider implements DocumentProvider {
	
	private String fileName;

	public FileDocumentProvider(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Document getDocument() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return Jsoup.parse(file, "UTF-8");
	}
	
}
