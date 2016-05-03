package se.skeppstedt.swimmer.dropwizard;

import com.codahale.metrics.health.HealthCheck;
import com.google.common.collect.Multiset;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import se.skeppstedt.swimmer.dropwizard.api.User;
import se.skeppstedt.swimmer.dropwizard.authentication.SimpleAuthorizer;
import se.skeppstedt.swimmer.dropwizard.authentication.SimpleAuthenticator;
import se.skeppstedt.swimmer.dropwizard.health.OctoopenHealthCheck;
import se.skeppstedt.swimmer.dropwizard.resources.PersonalBestResource;
import se.skeppstedt.swimmer.dropwizard.resources.SwimmersResource;
import se.skeppstedt.swimmer.dropwizard.resources.UserResource;
import se.skeppstedt.swimmer.guice.SwimmersModule;

import com.codahale.metrics.MetricRegistry;
import com.hubspot.dropwizard.guice.GuiceBundle;

public class SwimmersApplication extends Application<SwimmersConfiguration> {

	private static CachingAuthenticator<BasicCredentials, User> cachedAuthenticator;
	public static CachingAuthenticator<BasicCredentials, User> getCachedAuthenticator() {
		return cachedAuthenticator;
	}


	private GuiceBundle<SwimmersConfiguration> guiceBundle;
	
	public static void main(String[] args) throws Exception {
		new SwimmersApplication().run(args);
	}

	@Override
	public String getName() {
		return "Swimmers";
	}
	
	@Override
	public void initialize(Bootstrap<SwimmersConfiguration> bootstrap) {
		   guiceBundle = GuiceBundle.<SwimmersConfiguration>newBuilder()
				      .addModule(new SwimmersModule())
				      .setConfigClass(SwimmersConfiguration.class)
				      .build();

		   bootstrap.addBundle(guiceBundle);		
	}

	@Override
	public void run(SwimmersConfiguration configuration, Environment environment) {
		//GUICE INJECTOR
//		Guice.createInjector(new SwimmersModule());
		SimpleAuthenticator authenticator = guiceBundle.getInjector().getInstance(SimpleAuthenticator.class);
		cachedAuthenticator = new CachingAuthenticator<BasicCredentials, User>(
		      new MetricRegistry(), authenticator, configuration.getAuthenticationCachePolicy());
	    environment.jersey().register(new AuthDynamicFeature(
	            new BasicCredentialAuthFilter.Builder<User>()
	                .setAuthenticator(cachedAuthenticator)
	                .setAuthorizer(new SimpleAuthorizer())
	                .setRealm("SUPER SECRET STUFF")
	                .buildAuthFilter()));
	    environment.jersey().register(RolesAllowedDynamicFeature.class);
	    //If you want to use @Auth to inject a custom Principal type into your resource
	    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		
		// Enable CORS headers
	    final FilterRegistration.Dynamic cors =
	        environment.servlets().addFilter("CORS", CrossOriginFilter.class);
	    // Configure CORS parameters
	    cors.setInitParameter("allowedOrigins", "*");
	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin, Authorization");
	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

	    // Add URL mapping
	    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	    
		final SwimmersResource resource = new SwimmersResource();
		environment.jersey().register(resource);
		final PersonalBestResource pbResource = new PersonalBestResource();
		environment.jersey().register(pbResource);
		final UserResource userResource = new UserResource();
		environment.jersey().register(userResource);

		//Add health checks
		final OctoopenHealthCheck healthCheck = new OctoopenHealthCheck();
		environment.healthChecks().register("Octoopen", healthCheck);

		//Run healtchecks
		for (Map.Entry<String, HealthCheck.Result> entry : environment.healthChecks().runHealthChecks().entrySet()) {
			if (entry.getValue().isHealthy()) {
				System.out.println(entry.getKey() + ": OK");
			} else {
				System.out.println(entry.getKey() + ": FAIL");
			}
		}
		
	}

}