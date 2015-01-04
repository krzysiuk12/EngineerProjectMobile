package pl.edu.agh.activities.tripCreator;

import android.os.Bundle;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2015-01-03.
 */
public class TripCreatorFinishPageActivity extends TripCreatorWizardPageActivity {

    @Override
    protected AbstractWizardPage getWizardPageFragment(int pageIndex) { return new TripCreatorFinishPageFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_creator_finish_page);
    }
}
