package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.LocationSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;

/**
 * Created by Krzysiu on 2014-06-20.
 */
public class PostAddNewPrivateLocationAsyncTask extends AsyncTask<Void, Void, ResponseSerializer> {

    private String token;
    private Location location;

    public PostAddNewPrivateLocationAsyncTask(String token, Location location) {
        this.token = token;
        this.location = location;
    }

    @Override
    protected ResponseSerializer doInBackground(Void... voids) {
	    ResponseEntity<ResponseSerializer> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildAddNewPrivateLocationPath(),
		                HttpMethod.POST,
		                new HttpEntity<LocationSerializer>(LocationSerializer.buildLocationSerializerFromLocation(location), HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)),
		                ResponseSerializer.class);
	    AndroidLogService service = new AndroidLogService();
	    service.error(responseEntity.getStatusCode().toString());
	    service.error(responseEntity.getBody().toString());
        return responseEntity.getBody();
    }
}