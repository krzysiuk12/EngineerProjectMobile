package pl.edu.agh.asynctasks.trips;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.TripsPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.util.List;

/**
 * Created by Magda on 2014-12-08.
 */
public class GetAllTripsAsyncTask extends AsyncTask<Void, Void, List<Trip>> {

	private String token;

	public GetAllTripsAsyncTask(String token) {
		this.token = token;
	}

	@Override
	protected List<Trip> doInBackground(Void... voids) {
		ResponseEntity<ResponseSerializer<List<Trip>>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new TripsPathBuilder().buildAllTripsPath(),
						HttpMethod.GET,
						new HttpEntity<Trip>(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
						new ParameterizedTypeReference<ResponseSerializer<List<Trip>>>() {});
		new AndroidLogService().error(responseEntity.getBody().getResult().size() + " downloaded trips");
		new AndroidLogService().error(responseEntity.getStatusCode() + " ");
		new AndroidLogService().error(responseEntity.getBody().getStatus() + "");
		return (List<Trip>) responseEntity.getBody().getResult();
	}

}
