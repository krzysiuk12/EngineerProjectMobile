package pl.edu.agh.serializers.google.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.edu.agh.domain.trips.TravelMode;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {

    private TextAndValue distance;
    private TextAndValue duration;
    private Coordinate end_location;
    private Coordinate start_location;
    private TravelMode travel_mode;
    private String html_instructions;

    public Step() {
    }

    public Step(TextAndValue distance, TextAndValue duration, Coordinate end_location, Coordinate start_location, TravelMode travel_mode, String html_instructions) {
        this.distance = distance;
        this.duration = duration;
        this.end_location = end_location;
        this.start_location = start_location;
        this.travel_mode = travel_mode;
        this.html_instructions = html_instructions;
    }

    public TextAndValue getDistance() {
        return distance;
    }

    public void setDistance(TextAndValue distance) {
        this.distance = distance;
    }

    public TextAndValue getDuration() {
        return duration;
    }

    public void setDuration(TextAndValue duration) {
        this.duration = duration;
    }

    public Coordinate getEnd_location() {
        return end_location;
    }

    public void setEnd_location(Coordinate end_location) {
        this.end_location = end_location;
    }

    public Coordinate getStart_location() {
        return start_location;
    }

    public void setStart_location(Coordinate start_location) {
        this.start_location = start_location;
    }

    public TravelMode getTravel_mode() {
        return travel_mode;
    }

    public void setTravel_mode(TravelMode travel_mode) {
        this.travel_mode = travel_mode;
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
    }
}
