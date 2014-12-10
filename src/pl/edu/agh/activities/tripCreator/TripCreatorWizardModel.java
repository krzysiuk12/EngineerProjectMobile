package pl.edu.agh.activities.tripCreator;

import android.content.Context;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-09.
 */
public class TripCreatorWizardModel extends AbstractWizardModel {

    public TripCreatorWizardModel(Context context) {
        super(context);
    }

    @Override
    protected WizardPageAdapter getAdapterInstance() {
        ArrayList<AbstractWizardPage> wizardPagesList = new ArrayList<>();
        wizardPagesList.add(new TripCreatorInitPage(R.layout.trip_creator_init_page, "Init Page"));
        wizardPagesList.add(new TripCreatorMainSettingsPage(R.layout.trip_creator_main_settings_page, "Main Settings"));
        return new WizardPageAdapter(getActivity(), wizardPagesList);
    }

    @Override
    protected AbstractWizardPage getWizardPageFragmentInstance(AbstractWizardPage page, int index) {
        return null;
    }
}
