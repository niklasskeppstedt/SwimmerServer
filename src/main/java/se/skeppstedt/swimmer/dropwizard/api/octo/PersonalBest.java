package se.skeppstedt.swimmer.dropwizard.api.octo;

import java.time.Duration;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonalBest {
	private Event event;
	private Duration duration;
	private String swimmerId;
	private String competition;
	private Date date;    
    public PersonalBest() {
    	
    }

    public PersonalBest(Event event, Duration duration, String competition, Date date, String swimmerId) {
		this.event = event;
		this.duration = duration;
		this.swimmerId = swimmerId;
		this.competition = competition;
		this.date = date;
	}

    public Duration getDuration() {
		return duration;
	}
	
    @JsonProperty
    public String getTime() {
		return FormatHelper.formatDuration(duration);
	}
	
    @JsonProperty
	public Event getEvent() {
		return event;
	}
	
    @JsonProperty
	public String getSwimmerId() {
		return swimmerId;
	}
	
    @JsonProperty
	public String getCompetition() {
		return competition;
	}
    
    @JsonProperty
    public Date getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return String.format("%15s %-10s", event, FormatHelper.formatDuration(duration));
	}

}