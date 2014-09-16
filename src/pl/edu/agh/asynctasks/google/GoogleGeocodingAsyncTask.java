package pl.edu.agh.asynctasks.google;

import android.os.AsyncTask;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.serializers.google.geocoding.GoogleGeocodingSerializer;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class GoogleGeocodingAsyncTask extends AsyncTask<Void, Void, GoogleGeocodingSerializer> {

    private String path;

    public GoogleGeocodingAsyncTask(String path) {
        this.path = path;
    }

    @Override
    protected GoogleGeocodingSerializer doInBackground(Void... voids) {
        GoogleGeocodingSerializer result = HttpRequestBuilder.getRestTemplateWithJacksonConverter().getForObject(path, GoogleGeocodingSerializer.class);
        return result;
    }

}
