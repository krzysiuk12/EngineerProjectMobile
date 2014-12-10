package pl.edu.agh.activities.locations;

import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.exceptions.LocationException;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.List;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-11.
 */
public class ShowPrivateLocationsOnMapActivity extends AbstractShowLocationsOnMapActivity {

    @Override
    protected List<Location> getLocations() throws LocationException {
        return getLocationManagementService().getAllLocations();    // TODO : make it all user's private locations

    }
}

