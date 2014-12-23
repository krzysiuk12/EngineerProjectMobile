package pl.edu.agh.asynctasks.trips;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.TripsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.serializers.common.ResponseSerializer;


/**
 * Created by Magda on 2014-12-21.
 */
public class GetAllTripDayDetailsAsyncTask extends AsyncTask<Void, Void, TripDay> {

	private String token;
	private int id;

	public GetAllTripDayDetailsAsyncTask(String token, int id) {
		this.token = token;
		this.id = id;
	}

	@Override
	protected TripDay doInBackground(Void... voids) {
		ResponseEntity<ResponseSerializer<TripDay>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new TripsPathBuilder().buildAllTripDayDetailsPath(id),
						HttpMethod.GET,
						new HttpEntity<Trip>(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
						new ParameterizedTypeReference<ResponseSerializer<TripDay>>() {});
		return (TripDay) responseEntity.getBody().getResult();
	}
}
