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
public class LogInAsyncTask extends AsyncTask<Void, Void, ResponseSerializer<LoginSerializer>> {

	private String login;
	private String password;

	public LogInAsyncTask(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	protected ResponseSerializer<LoginSerializer> doInBackground(Void... params) {
		// TODO: IMPLEMENT SAFE AUTHORIZATION
		ResponseEntity<ResponseSerializer<LoginSerializer>> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
				.exchange(new SessionPathBuilder().buildLogInPath(),
						HttpMethod.POST,
						new HttpEntity<LoginSerializer>(buildLoginSerializer()),
						new ParameterizedTypeReference<ResponseSerializer<LoginSerializer>>() {}
				);
		return responseEntity.getBody();
	}

	private LoginSerializer buildLoginSerializer() {
		LoginSerializer loginSerializer = new LoginSerializer();
		loginSerializer.setLogin(login);
		loginSerializer.setPassword(password);
		return loginSerializer;
	}

}
