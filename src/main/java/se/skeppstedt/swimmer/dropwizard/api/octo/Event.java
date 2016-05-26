package se.skeppstedt.swimmer.dropwizard.api.octo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum Event {
	FREESTYLE_25("4", Discipline.FREESTYLE, Distance.TWENTY_FIVE),
	FREESTYLE_50("11", Discipline.FREESTYLE, Distance.FIFTY),
	FREESTYLE_50_LC("12", Discipline.FREESTYLE, Distance.FIFTY),
	FREESTYLE_100("13", Discipline.FREESTYLE, Distance.ONE_HUNDRED),
	FREESTYLE_100_LC("14", Discipline.FREESTYLE, Distance.ONE_HUNDRED),
	FREESTYLE_200("15", Discipline.FREESTYLE, Distance.TWO_HUNDRED),
	FREESTYLE_200_LC("16", Discipline.FREESTYLE, Distance.TWO_HUNDRED),
	FREESTYLE_400("17", Discipline.FREESTYLE, Distance.FOUR_HUNDRED),
	FREESTYLE_400_LC("18", Discipline.FREESTYLE, Distance.FOUR_HUNDRED),
	FREESTYLE_800("19", Discipline.FREESTYLE, Distance.EIGHT_HUNDRED),
	FREESTYLE_800_LC("20", Discipline.FREESTYLE, Distance.EIGHT_HUNDRED),
	FREESTYLE_1500("21", Discipline.FREESTYLE, Distance.FIFTEEN_HUNDRED),
	FREESTYLE_1500_LC("22", Discipline.FREESTYLE, Distance.FIFTEEN_HUNDRED),

	BREASTSTROKE_25("5", Discipline.BREASTSTROKE, Distance.TWENTY_FIVE),
	BREASTSTROKE_50("31", Discipline.BREASTSTROKE, Distance.FIFTY),
	BREASTSTROKE_50_LC("32", Discipline.BREASTSTROKE, Distance.FIFTY),
	BREASTSTROKE_100("33", Discipline.BREASTSTROKE, Distance.ONE_HUNDRED),
	BREASTSTROKE_100_LC("34", Discipline.BREASTSTROKE, Distance.ONE_HUNDRED),
	BREASTSTROKE_200("35", Discipline.BREASTSTROKE, Distance.TWO_HUNDRED),
	BREASTSTROKE_200_LC("36", Discipline.BREASTSTROKE, Distance.TWO_HUNDRED),
			
	BACKSTROKE_25("6", Discipline.BACKSTROKE, Distance.TWENTY_FIVE),
	BACKSTROKE_50("41", Discipline.BACKSTROKE, Distance.FIFTY),
	BACKSTROKE_50_LC("42", Discipline.BACKSTROKE, Distance.FIFTY),
	BACKSTROKE_100("43", Discipline.BACKSTROKE, Distance.ONE_HUNDRED),
	BACKSTROKE_100_LC("44", Discipline.BACKSTROKE, Distance.ONE_HUNDRED),
	BACKSTROKE_200("45", Discipline.BACKSTROKE, Distance.TWO_HUNDRED),
	BACKSTROKE_200_LC("46", Discipline.BACKSTROKE, Distance.TWO_HUNDRED),

	BUTTERFLY_25("7", Discipline.BUTTERFLY, Distance.TWENTY_FIVE),
	BUTTERFLY_50("51", Discipline.BUTTERFLY, Distance.FIFTY),
	BUTTERFLY_50_LC("52", Discipline.BUTTERFLY, Distance.FIFTY),
	BUTTERFLY_100("53", Discipline.BUTTERFLY, Distance.ONE_HUNDRED),
	BUTTERFLY_100_LC("54", Discipline.BUTTERFLY, Distance.ONE_HUNDRED),
	BUTTERFLY_200("55", Discipline.BUTTERFLY, Distance.TWO_HUNDRED),
	BUTTERFLY_200_LC("56", Discipline.BUTTERFLY, Distance.TWO_HUNDRED),

	MEDLEY_100("61", Discipline.MEDLEY, Distance.ONE_HUNDRED),
	MEDLEY_100_LC("62", Discipline.MEDLEY, Distance.ONE_HUNDRED),
	MEDLEY_200("63", Discipline.MEDLEY, Distance.TWO_HUNDRED),
	MEDLEY_200_LC("64", Discipline.MEDLEY, Distance.TWO_HUNDRED),
	MEDLEY_400("65", Discipline.MEDLEY, Distance.FOUR_HUNDRED),
	MEDLEY_400_LC("66", Discipline.MEDLEY, Distance.FOUR_HUNDRED);

	@JsonProperty
	public Distance getDistance() {
		return distance;
	}

	@JsonProperty
	public String getCode() {
		return code;
	}

	@JsonProperty
	public Discipline getDiscipline() {
		return discipline;
	}

	private Distance distance;
	private String code;
	private Discipline discipline;

	private Event(String code, Discipline discipline, Distance distance) {
		this.code = code;
		this.discipline = discipline;
		this.distance = distance;
	}

	public static Event fromCode(String string) {
		for (Event event : values()) {
			if(event.code.equals(string)) {
				return event;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return String.format("%4s %-10s", distance, discipline);
	}
}
