package se.skeppstedt.swimmer.guice;

import se.skeppstedt.swimmer.octo.OctoParser;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;
import se.skeppstedt.swimmer.persistence.UserDao;
import se.skeppstedt.swimmer.persistence.UserDaoImpl;

import com.google.inject.AbstractModule;

public class SwimmersModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(OctoParser.class).to(OctoOpenParserImpl.class);
//		bind(OctoParser.class).to(FileOctoParser.class);
		bind(UserDao.class).to(UserDaoImpl.class);
	}
}
