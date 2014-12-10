package pl.edu.agh.activities.locations;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;

import java.util.List;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-11.
 */
public class ShowAllLocationsOnMapActivity extends AbstractShowLocationsOnMapActivity {

    @Override
    protected List<Location> getLocations() throws LocationException {
        return getLocationManagementService().getAllLocations();

    }
}

