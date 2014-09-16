package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.domain.locations.Location;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-15.
 */
public class GetAllLocationsAsyncTask  extends AsyncTask<Void, Void, List<Location>> {

    private String token;

    public GetAllLocationsAsyncTask(String token) {
        this.token = token;
    }

    @Override
    protected List<Location> doInBackground(Void... voids) {
        ResponseEntity<Location[]> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildAllLocationsPath(), HttpMethod.GET, new HttpEntity<Location>(HttpRequestBuilder.getHttpHeadersWithHeader(token)), Location[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
