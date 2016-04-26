package se.skeppstedt.swimmer.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import se.skeppstedt.swimmer.dropwizard.resources.PersonalBestResource;
import se.skeppstedt.swimmer.dropwizard.resources.SwimmersResource;
import se.skeppstedt.swimmer.dropwizard.resources.UserResource;

public class SwimmersApplication extends Application<SwimmersConfiguration> {
	public static void main(String[] args) throws Exception {
		new SwimmersApplication().run(args);
	}

	@Override
	public String getName() {
		return "Swimmers";
	}

	@Override
	public void initialize(Bootstrap<SwimmersConfiguration> bootstrap) {
	}

	@Override
	public void run(SwimmersConfiguration configuration, Environment environment) {
		   // Enable CORS headers
	    final FilterRegistration.Dynamic cors =
	        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

	    // Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

	    // Add URL mapping
	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	    
		final SwimmersResource resource = new SwimmersResource();
		environment.jersey().register(resource);
		final PersonalBestResource pbResource = new PersonalBestResource();
		environment.jersey().register(pbResource);
		final UserResource userResource = new UserResource();
		environment.jersey().register(userResource);

//		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
//		environment.healthChecks().register("template", healthCheck);
		
	}

}