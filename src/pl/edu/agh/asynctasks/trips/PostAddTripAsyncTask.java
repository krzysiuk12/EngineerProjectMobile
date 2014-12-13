package pl.edu.agh.asynctasks.trips;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.TripsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;

/**
 * Created by Magda on 2014-12-13.
 */
public class PostAddTripAsyncTask extends AsyncTask<Void, Void, ResponseSerializer> {

	private String token;
	private Trip trip;

	public PostAddTripAsyncTask(String token, Trip trip) {
		this.token = token;
		this.trip = trip;
	}

	@Override
	protected ResponseSerializer doInBackground(Void... params) {
		ResponseEntity<ResponseSerializer> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new TripsPathBuilder().buildAddNewTripPath(),
						HttpMethod.POST,
						new HttpEntity<Trip>(trip, HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)),
						ResponseSerializer.class);
		AndroidLogService service = new AndroidLogService();    // TODO: test, error handling
		service.error(responseEntity.getStatusCode().toString());
		service.error(responseEntity.getBody().toString());
		return responseEntity.getBody();
	}

}
