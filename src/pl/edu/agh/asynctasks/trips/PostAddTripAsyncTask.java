package pl.edu.agh.asynctasks.trips;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.TripsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.serializers.TripCreationSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;

/**
 * Created by Magda on 2014-12-13.
 */
public class PostAddTripAsyncTask extends AsyncTask<Void, Void, ResponseSerializer<Trip>> {

	private String token;
	private TripCreationSerializer serializer;

	public PostAddTripAsyncTask(String token, TripCreationSerializer serializer) {
		this.token = token;
		this.serializer = serializer;
	}

	@Override
	protected ResponseSerializer<Trip> doInBackground(Void... params) {
		ResponseEntity<ResponseSerializer<Trip>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new TripsPathBuilder().buildAddNewTripPath(),
						HttpMethod.POST,
						new HttpEntity<TripCreationSerializer>(serializer, HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)),
						new ParameterizedTypeReference<ResponseSerializer<Trip>>() {});
		return responseEntity.getBody();
	}

}
