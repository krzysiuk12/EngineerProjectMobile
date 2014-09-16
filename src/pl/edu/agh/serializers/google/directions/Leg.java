package pl.edu.agh.serializers.google.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {

    private TextAndValue distance;
    private TextAndValue duration;
    private String start_address;
    private String end_address;
    private Coordinate end_location;
    private Coordinate start_location;
    private List<Step> steps;

    public Leg() {
    }

    public Leg(TextAndValue distance, TextAndValue duration, String start_address, String end_address, Coordinate end_location, Coordinate start_location, List<Step> steps) {
        this.distance = distance;
        this.duration = duration;
        this.start_address = start_address;
        this.end_address = end_address;
        this.end_location = end_location;
        this.start_location = start_location;
        this.steps = steps;
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

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
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

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
