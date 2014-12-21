package pl.edu.agh.activities.tripcreator;

/**
 * Created by Sławek on 2014-12-13.
 */
public class TripCreatorInitPageActivity extends AbstractWizardPageActivity {

//    @Override
//    protected int getWizardPageId() {
//        return 0;
//    }

    @Override
    protected int getWizardPageId() {
        return 0;
    }

    @Override
    protected AbstractWizardPage getWizardPageFragment() {
        return new TripCreatorInitPage();
    }
}
