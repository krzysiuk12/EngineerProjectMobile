package pl.edu.agh.activities;


import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.TripDayDetailsFragment;
import pl.edu.agh.fragments.TripDetailsFragment;
import pl.edu.agh.main.R;

/**
 * Created by Magda on 2014-12-04.
 */
public class TripDayDetailsActivity extends AbstractDetailsActivity {

	@Override
	protected int getDetailsPaneId() {
		return R.id.ShowTrips_TripDetails;
	}

	@Override
	protected AbstractDescriptionFragment getDetailsFragment() {
		return new TripDayDetailsFragment();
	}

}
