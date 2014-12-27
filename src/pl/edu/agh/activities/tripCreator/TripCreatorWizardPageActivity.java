package pl.edu.agh.activities.tripCreator;

import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardPageActivity extends AbstractWizardPageActivity {

    @Override
    protected int getWizardPageId() { return R.id.TripCreatorActivity_WizardPageView; }

    @Override
    protected AbstractWizardPage getWizardPageFragment(int index) {
        return new TripCreatorWizardPageFragment();
    }
}
