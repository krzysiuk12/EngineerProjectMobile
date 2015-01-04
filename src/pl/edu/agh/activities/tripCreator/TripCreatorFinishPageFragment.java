package pl.edu.agh.activities.tripCreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.domain.trips.Trip;

/**
 * Created by Sławek on 2015-01-03.
 */
public class TripCreatorFinishPageFragment extends TripCreatorWizardPageFragment {

    private Trip trip;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        
    }

    public Trip getTrip() {
        if(trip == null) {
            trip = new Trip();
        }
        return trip;
    }

}
