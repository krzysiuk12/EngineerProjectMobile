package pl.edu.agh.activities.tripCreator;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-12.
 */
public class TripCreatorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_creator_activity);
    }

    public void goNext(int position) {
        FragmentManager fm = getFragmentManager();
        TripCreatorWizardModel wizardModel = (TripCreatorWizardModel)fm.findFragmentById(R.id.TripCreatorActivity_WizardPages);
        wizardModel.goNextPage(position);
    }
}
