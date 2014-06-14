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

/**
 * Created by Krzysiu on 2014-06-14.
 */
public class LocationByIdAsyncTask extends AsyncTask<Void, Void, Location> {

    private Long id;

    public LocationByIdAsyncTask(Long id) {
        this.id = id;
    }

    @Override
    protected Location doInBackground(Void... voids) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SomeToken");
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String path = PathTools.getLocationByIdPath(id);
        ResponseEntity<Location> responseEntity = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<Location>(headers), Location.class);
        return responseEntity.getBody();
    }
}
