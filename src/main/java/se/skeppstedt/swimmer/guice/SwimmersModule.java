package se.skeppstedt.swimmer.guice;

import se.skeppstedt.swimmer.octo.OctoParser;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;

import com.google.inject.AbstractModule;

public class SwimmersModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(OctoParser.class).to(OctoOpenParserImpl.class);
	}
}
