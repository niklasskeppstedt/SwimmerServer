package se.skeppstedt.swimmer.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;
import se.skeppstedt.swimmer.dropwizard.api.Swimmer;
import se.skeppstedt.swimmer.octo.impl.OctoOpenParserImpl;

public class OctoopenHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        OctoOpenParserImpl parser = new OctoOpenParserImpl();
        Swimmer swimmer = parser.getSwimmerDetails("297358");
        if(swimmer.getFirstName().equals("Elias")) {
            return Result.healthy();
        }
        return Result.unhealthy("Octoopen does not respond...");
    }
}