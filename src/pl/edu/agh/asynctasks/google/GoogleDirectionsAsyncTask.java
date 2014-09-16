package pl.edu.agh.asynctasks.google;

import android.os.AsyncTask;
import pl.edu.agh.asynctasks.builders.requests.HttpRequestBuilder;
import pl.edu.agh.serializers.google.directions.GoogleDirectionsSerializer;

/**
 * Created by Krzysiu on 2014-09-14.
 */
public class GoogleDirectionsAsyncTask extends AsyncTask<Void, Void, GoogleDirectionsSerializer>  {

    private String path;

    public GoogleDirectionsAsyncTask(String path) {
        this.path = path;
    }

    @Override
    protected GoogleDirectionsSerializer doInBackground(Void... voids) {
        GoogleDirectionsSerializer result = HttpRequestBuilder.getRestTemplateWithJacksonConverter().getForObject(path, GoogleDirectionsSerializer.class);
        return result;
    }
}
