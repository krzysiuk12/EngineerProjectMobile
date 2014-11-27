package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.CoordinatesPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.serializers.common.ResponseStatus;

import java.util.List;

/**
 * Created by Magda on 2014-11-25.
 */
public class GetAllLocationsInAreaInScopeAsyncTask extends AsyncTask<Void, Void,List<Location>> {

	private String token;
	private double latitude;
	private double longitude;
	private double scope;   // in km

	public GetAllLocationsInAreaInScopeAsyncTask(String token, double latitude, double longitude, double scope) {
		this.token = token;
		this.latitude = latitude;
		this.longitude = longitude;
		this.scope = scope;
	}

	@Override
	protected List<Location> doInBackground(Void... params) {
		ResponseEntity<ResponseSerializer<List<Location>>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new CoordinatesPathBuilder().buildLocationsInAreaInScopePath(latitude, longitude, scope),
						HttpMethod.GET,
						new HttpEntity<Location>(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
						new ParameterizedTypeReference<ResponseSerializer<List<Location>>>() {});
		if ( responseEntity.getStatusCode() ==  HttpStatus.OK && responseEntity.getBody() != null && responseEntity.getBody().getStatus() == ResponseStatus.OK ) {
			return (List<Location>) responseEntity.getBody().getResult();
		} else {
			return null;    // todo: errorhandling (all GetLocationsAsyncTasks)
		}
	}

}
