package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.*;
import pl.edu.agh.domain.Location;
import pl.edu.agh.tools.HttpRequestsPreparationTools;
import pl.edu.agh.tools.PathTools;

/**
 * Created by Krzysiu on 2014-06-19.
 */
public class PutLocationStatusAsyncTask extends AsyncTask<Void, Void, Location> {

    private String token;
    private Long locationId;
    private Location.Status status;

    public PutLocationStatusAsyncTask(String token, Long locationId, Location.Status status) {
        this.token = token;
        this.locationId = locationId;
        this.status = status;
    }

    @Override
    protected Location doInBackground(Void... voids) {
        ResponseEntity<Location> responseEntity = HttpRequestsPreparationTools.getRestTemplateWithJacksonAndStringConverter()
                .exchange(PathTools.changeLocationStatusPath(locationId), HttpMethod.PUT, new HttpEntity<Location.Status>(status, HttpRequestsPreparationTools.getHttpHeadersWithHeaderAndJsonContent(token)), Location.class);
        return responseEntity.getBody();
    }
}
