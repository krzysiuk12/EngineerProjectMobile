package pl.edu.agh.activities.locations;

import pl.edu.agh.activities.AbstractDetailsActivity;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.locations.LocationDescriptionFragment;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-15.
 */
public class LocationDescriptionActivity extends AbstractDetailsActivity {

	@Override
	protected int getDetailsPaneId() {
		return R.id.ShowLocations_LocationDescription;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragment() {
		return new LocationDescriptionFragment();
	}

}
