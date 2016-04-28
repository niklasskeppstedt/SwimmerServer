package se.skeppstedt.swimmer.dropwizard;

import org.hibernate.validator.constraints.NotEmpty;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;

public class SwimmersConfiguration extends Configuration {
	
	@JsonProperty
	public CacheBuilderSpec getAuthenticationCachePolicy() {
		return CacheBuilderSpec.parse(authenticationCachePolicy);
	}
	
	@NotEmpty
	private String authenticationCachePolicy;
	
//    @NotEmpty
//    private String template;
//
//    @NotEmpty
//    private String defaultName = "Stranger";
//
//    @JsonProperty
//    public String getTemplate() {
//        return template;
//    }
//
//    @JsonProperty
//    public void setTemplate(String template) {
//        this.template = template;
//    }
//
//    @JsonProperty
//    public String getDefaultName() {
//        return defaultName;
//    }
//
//    @JsonProperty
//    public void setDefaultName(String name) {
//        this.defaultName = name;
//    }
}

