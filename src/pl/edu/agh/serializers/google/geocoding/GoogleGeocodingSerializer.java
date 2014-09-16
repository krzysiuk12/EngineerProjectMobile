package pl.edu.agh.serializers.google.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Krzysiu on 2014-09-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeocodingSerializer {

    private List<Result> results;
    private String status;

    public GoogleGeocodingSerializer() {
    }

    public GoogleGeocodingSerializer(List<Result> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
