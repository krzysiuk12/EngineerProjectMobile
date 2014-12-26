package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.LocationSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;

/**
 * Created by Krzysiu on 2014-06-20.
 */
public class PostAddNewLocationAsyncTask extends AsyncTask<Void, Void, ResponseSerializer<Long>> {

    private String token;
    private Location location;

    public PostAddNewLocationAsyncTask(String token, Location location) {
        this.token = token;
        this.location = location;
    }

    @Override
    protected ResponseSerializer<Long> doInBackground(Void... voids) {
	   ResponseEntity<ResponseSerializer<Long>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildAddNewLocationPath(),
		                HttpMethod.POST,
		                new HttpEntity<LocationSerializer>(LocationSerializer.buildLocationSerializerFromLocation(location), HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)),
                        new ParameterizedTypeReference<ResponseSerializer<Long>>() {});
	    AndroidLogService service = new AndroidLogService();
	    service.error(responseEntity.getStatusCode().toString());
	    service.error(responseEntity.getBody().toString());
        return responseEntity.getBody();
    }
}
