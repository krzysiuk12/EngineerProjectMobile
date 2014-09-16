package pl.edu.agh.asynctasks.locations;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.asynctasks.builders.paths.LocationsPathBuilder;
import pl.edu.agh.domain.locations.Location;

/**
 * Created by Krzysiu on 2014-06-20.
 */
public class PostAddNewLocationAsyncTask extends AsyncTask<Void, Void, Long> {

    private String token;
    private Location location;

    public PostAddNewLocationAsyncTask(String token, Location location) {
        this.token = token;
        this.location = location;
    }

    @Override
    protected Long doInBackground(Void... voids) {
        ResponseEntity<Long> responseEntity = HttpRequestBuilder.getRestTemplateWithJacksonConverter()
                .exchange(new LocationsPathBuilder().buildAddNewLocationPath(), HttpMethod.POST, new HttpEntity<Location>(location, HttpRequestBuilder.getHttpHeadersWithHeaderAndJsonContent(token)), Long.class);
        return responseEntity.getBody();
    }
}
