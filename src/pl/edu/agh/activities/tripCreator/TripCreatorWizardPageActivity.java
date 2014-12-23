package pl.edu.agh.activities.tripcreator;

import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardPageActivity extends AbstractWizardPageActivity {

    @Override
    protected int getWizardPageId() { return R.id.TripCreatorActivity_WizardPageView; }

//    @Override
//    protected AbstractWizardPage getWizardPageFragment() {
//        return new TripCreatorWizardPageFragment();
//    }

    @Override
    protected AbstractWizardPage getWizardPageFragment(int index) {
        if(index == 0) {
            return new TripCreatorInitPageFragment();
        }
        else {
            return new TripCreatorWizardPageFragment();
        }
    }
}
