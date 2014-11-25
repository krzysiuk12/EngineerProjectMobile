package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.common.ResponseSerializer;

import java.util.List;

/**
 * Created by Krzysiu on 2014-06-15.
 */
public class GetAllPrivateLocationsAsyncTask extends AsyncTask<Void, Void, List<Location>> {

    private String token;

    public GetAllPrivateLocationsAsyncTask(String token) {
        this.token = token;
    }

    @Override
    protected List<Location> doInBackground(Void... voids) {
        ResponseEntity<ResponseSerializer<List<Location>>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildAllPrivateLocationsPath(),
			                HttpMethod.GET,
			                new HttpEntity<Location>(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
			                new ParameterizedTypeReference<ResponseSerializer<List<Location>>>() {});
	    return (List<Location>) responseEntity.getBody().getResult();
    }
}
