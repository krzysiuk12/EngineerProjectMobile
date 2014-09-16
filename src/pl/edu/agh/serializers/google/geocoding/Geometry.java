package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geometry {

    private Coordinate location;

    public Geometry() {
    }

    public Geometry(Coordinate location) {
        this.location = location;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }
}
