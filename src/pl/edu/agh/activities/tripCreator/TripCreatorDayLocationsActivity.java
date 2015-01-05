package pl.edu.agh.activities.tripCreator;

import pl.edu.agh.activities.AbstractDetailsActivity;
import pl.edu.agh.fragments.AbstractDescriptionFragment;

/**
 * Created by Magda on 2015-01-05.
 */
public class TripCreatorDayLocationsActivity extends AbstractDetailsActivity {

    @Override
    protected int getDetailsPaneId() {
        return 0;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragment() {
        return new TripCreatorDayLocationsFragment();
    }
}
