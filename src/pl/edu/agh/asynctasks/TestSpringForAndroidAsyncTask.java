package pl.edu.agh.asynctasks;

import android.os.AsyncTask;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Krzysiu on 2014-06-08.
 */
public class TestSpringForAndroidAsyncTask extends AsyncTask<Void, Void, String> {

    public String address;

    public TestSpringForAndroidAsyncTask(String address) {
        this.address = address;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String url = "http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false%22";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String result = restTemplate.getForObject(url, String.class, "Android");
        return result;
    }
}
