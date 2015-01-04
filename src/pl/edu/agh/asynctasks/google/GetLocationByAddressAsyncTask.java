package pl.edu.agh.asynctasks.google;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.GooglePathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.serializers.GoogleGeocodeSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Magda on 2015-01-04.
 */
public class GetLocationByAddressAsyncTask extends AsyncTask<Void, Void, ResponseSerializer<Location>> {

	private String token;
	private GoogleGeocodeSerializer googleGeocodeSerializer;

	public GetLocationByAddressAsyncTask(String token, GoogleGeocodeSerializer googleGeocodeSerializer) {
		this.token = token;
		this.googleGeocodeSerializer = googleGeocodeSerializer;
	}

	@Override
	protected ResponseSerializer<Location> doInBackground(Void... voids) {
		ResponseEntity<ResponseSerializer<Location>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(GooglePathBuilder.buildLocationByAddressPath(),
						HttpMethod.POST,
						new HttpEntity<GoogleGeocodeSerializer>(googleGeocodeSerializer, HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)),
						new ParameterizedTypeReference<ResponseSerializer<Location>>() {
						});
		return responseEntity.getBody();
	}
}
