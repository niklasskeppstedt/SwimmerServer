package se.skeppstedt.swimmer.guice;

import com.google.inject.AbstractModule;

import se.skeppstedt.swimmer.octo.LiveTimingParser;
import se.skeppstedt.swimmer.octo.OctoParser;
import se.skeppstedt.swimmer.octo.impl.LiveTimingParserImpl;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;
import se.skeppstedt.swimmer.persistence.UserDao;
import se.skeppstedt.swimmer.persistence.UserDaoImpl;

public class SwimmersModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(OctoParser.class).to(OctoOpenParserImpl.class);
		bind(LiveTimingParser.class).to(LiveTimingParserImpl.class);
//		bind(OctoParser.class).to(FileOctoParser.class);
		bind(UserDao.class).to(UserDaoImpl.class);
	}
}
