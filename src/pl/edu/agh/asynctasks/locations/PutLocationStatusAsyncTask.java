package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysiu on 2014-06-19.
 */
public class PutLocationStatusAsyncTask extends AsyncTask<Void, Void, Location> {

    private String token;
    private Long locationId;
    private Location.Status status;

    public PutLocationStatusAsyncTask(String token, Long locationId, Location.Status status) {
        this.token = token;
        this.locationId = locationId;
        this.status = status;
    }

    @Override
    protected Location doInBackground(Void... voids) {
        ResponseEntity<Location> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildChangeLocationStatusPath(locationId), HttpMethod.PUT, new HttpEntity<Location.Status>(status, HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)), Location.class);
        return responseEntity.getBody();
    }
}
