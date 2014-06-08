package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class TestSpringForAndroidAsyncTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String url = "http://192.168.1.101:8080/TourTrip/rest/location/single";//"https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.getForObject(url, String.class, "Android");
        return result;
    }
}
