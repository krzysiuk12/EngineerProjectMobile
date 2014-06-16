package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.edu.agh.domain.Location;
import pl.edu.agh.tools.PathTools;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysiu on 2014-06-15.
 */
public class GetAllLocationsAsyncTask  extends AsyncTask<Void, Void, List<Location>> {

    @Override
    protected List<Location> doInBackground(Void... voids) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SomeToken");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Location[]> responseEntity = restTemplate.exchange(PathTools.getAllLocationsPath(), HttpMethod.GET, new HttpEntity<Location>(headers), Location[].class);
        return Arrays.asList(responseEntity.getBody());
    }
}
