package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.CoordinatesPathBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.common.ResponseSerializer;

import java.util.List;

/**
 * Created by Magda on 2014-11-25.
 */
public class GetAllLocationsInAreaAsyncTask extends AsyncTask<Void, Void,List<Location>> {

	private String token;
	private double latitude;
	private double longitude;

	public GetAllLocationsInAreaAsyncTask(String token, double latitude, double longitude) {
		this.token = token;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	@Override
	protected List<Location> doInBackground(Void... params) {
		ResponseEntity<ResponseSerializer<List<Location>>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new CoordinatesPathBuilder().buildLocationsInAreaPath(latitude, longitude),
						HttpMethod.GET,
						new HttpEntity<Location>(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
						new ParameterizedTypeReference<ResponseSerializer<List<Location>>>() {});
		return (List<Location>) responseEntity.getBody().getResult();
	}

}
