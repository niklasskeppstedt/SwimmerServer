package se.skeppstedt.swimmer.dropwizard;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;

public class SwimmersConfiguration extends Configuration {
	
	@JsonProperty
	public CacheBuilderSpec getAuthenticationCachePolicy() {
		return CacheBuilderSpec.parse(authenticationCachePolicy);
	}
	
	@NotEmpty
	private String authenticationCachePolicy;
	
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

}

