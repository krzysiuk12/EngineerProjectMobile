package pl.edu.agh.asynctasks.authorization;

import android.os.AsyncTask;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.paths.SessionPathBuilder;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.serializers.LoginSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;

/**
 * Created by Magda on 2014-11-22.
 */
public class LogOutAsyncTask extends AsyncTask<Void, Void, ResponseSerializer> {

	private String token;

	public LogOutAsyncTask(String token) {
		this.token = token;
	}

	@Override
	protected ResponseSerializer doInBackground(Void... params) {
		ResponseEntity<ResponseSerializer> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new SessionPathBuilder().buildLogOutPath(),
						HttpMethod.GET,
						new HttpEntity(HttpRequestBuilder.getHttpHeadersWithHeader(token)),
						ResponseSerializer.class
				);
		return responseEntity.getBody();
	}
}
