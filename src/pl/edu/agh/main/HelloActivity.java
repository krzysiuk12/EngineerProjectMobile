package pl.edu.agh.main;

import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.asynctasks.TestSpringForAndroidAsyncTask;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.Location;
import pl.edu.agh.repositories.implementation.OrmLiteLocationRepository;
import pl.edu.agh.repositories.interfaces.ILocationRepository;

import java.util.concurrent.ExecutionException;

public class HelloActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        System.out.println("TEST");

        Location location = new Location();
        location.setDescription("Descritption");
        location.setName("Name");
        location.setLatitude(50.061901);
        location.setLongitude(19.937480);

        ILocationRepository locationRepository = new OrmLiteLocationRepository(getHelper());
        locationRepository.saveLocation(location);

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.ShowLocationsOnMapView_Map)).getMap();
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        String result = null;
        try {
            result = new TestSpringForAndroidAsyncTask("Sukiennice Krakow").execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.HelloActivity_TextView)).setText(result);//locationRepository.getAllLocations().toString());

    }
}
