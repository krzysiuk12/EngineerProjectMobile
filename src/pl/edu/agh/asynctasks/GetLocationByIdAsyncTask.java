package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.domain.Location;
import pl.edu.agh.tools.HttpRequestsPreparationTools;
import pl.edu.agh.tools.PathTools;

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class GetLocationByIdAsyncTask extends AsyncTask<Void, Void, Location> {

    private String token;
    private Long id;

    public GetLocationByIdAsyncTask(String token, Long id) {
        this.token = token;
        this.id = id;
    }

    @Override
    protected Location doInBackground(Void... voids) {
        ResponseEntity<Location> responseEntity = HttpRequestsPreparationTools.getRestTemplateWithJacksonConverter()
                .exchange(PathTools.getLocationByIdPath(id), HttpMethod.GET, new HttpEntity<Location>(HttpRequestsPreparationTools.getHttpHeadersWithHeader(token)), Location.class);
        return responseEntity.getBody();
    }
}
