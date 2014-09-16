package pl.edu.agh.serializers.google.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDirectionsSerializer {

    private List<Route> routes;
    private String status;

    public GoogleDirectionsSerializer() {
    }

    public GoogleDirectionsSerializer(List<Route> routes, String status) {
        this.routes = routes;
        this.status = status;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
