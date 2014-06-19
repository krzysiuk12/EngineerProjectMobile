package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.edu.agh.domain.Location;
import pl.edu.agh.tools.HttpRequestsPreparationTools;
import pl.edu.agh.tools.PathTools;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-15.
 */
public class GetAllLocationsAsyncTask  extends AsyncTask<Void, Void, List<Location>> {

    private String token;

    public GetAllLocationsAsyncTask(String token) {
        this.token = token;
    }

    @Override
    protected List<Location> doInBackground(Void... voids) {
        ResponseEntity<Location[]> responseEntity = HttpRequestsPreparationTools.getRestTemplateWithJacksonConverter()
                .exchange(PathTools.getAllLocationsPath(), HttpMethod.GET, new HttpEntity<Location>(HttpRequestsPreparationTools.getHttpHeadersWithHeader(token)), Location[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
