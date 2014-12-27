package pl.edu.agh.activities.tripCreator;

import android.os.Bundle;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-13.
 */
public class TripCreatorInitPageActivity extends TripCreatorWizardPageActivity {

    @Override
    protected AbstractWizardPage getWizardPageFragment(int pageIndex) {
        return new TripCreatorInitPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_creator_init_page);
    }

}
