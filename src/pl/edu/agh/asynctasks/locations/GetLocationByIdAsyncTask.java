package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class GetLocationByIdAsyncTask extends AsyncTask<Void, Void, Location> {

    private String token;
    private Long id;

    public GetLocationByIdAsyncTask(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    @Override
    protected Location doInBackground(Void... voids) {
        ResponseEntity<ResponseSerializer<Location>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildLocationByIdPath(id), HttpMethod.GET, new HttpEntity<Location>(HttpRequestBuilder.getHttpHeadersWithHeader(token)), new ParameterizedTypeReference<ResponseSerializer<Location>>() {});
        return responseEntity.getBody().getResult();
    }
}
