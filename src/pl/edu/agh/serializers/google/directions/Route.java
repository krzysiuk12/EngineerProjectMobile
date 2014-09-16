package pl.edu.agh.serializers.google.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    private String copyrights;
    private List<Leg> legs;
    private String summary;
    private List<String> warning;
    private List<Integer> waypoint_order;

    public Route() {
    }

    public Route(String copyrights, List<Leg> legs, String summary, List<String> warning, List<Integer> waypoint_order) {
        this.copyrights = copyrights;
        this.legs = legs;
        this.summary = summary;
        this.warning = warning;
        this.waypoint_order = waypoint_order;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    public List<Integer> getWaypoint_order() {
        return waypoint_order;
    }

    public void setWaypoint_order(List<Integer> waypoint_order) {
        this.waypoint_order = waypoint_order;
    }
}


