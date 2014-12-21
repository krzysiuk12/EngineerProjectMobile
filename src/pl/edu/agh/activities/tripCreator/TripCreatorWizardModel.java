package pl.edu.agh.activities.tripcreator;

import android.content.Context;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-09.
 */
public class TripCreatorWizardModel extends AbstractWizardModel<TripCreatorWizardElement> {

    private int wizardPageId;

    public TripCreatorWizardModel() {
        super();
        wizardPageId = R.id.TripCreatorActivity_WizardPageView;
    }

    @Override
    protected WizardPageAdapter getAdapterInstance() {
        ArrayList<TripCreatorWizardElement> wizardPagesList = new ArrayList<>();
        wizardPagesList.add(new TripCreatorWizardElement(R.layout.trip_creator_init_page));
        wizardPagesList.add(new TripCreatorWizardElement(R.layout.trip_creator_main_settings_page));
        return new WizardPageAdapter(getActivity(), wizardPagesList);
    }

    @Override
    protected int getWizardPageId() {
        return wizardPageId;
    }

    @Override
    protected Class getClassForDetailsIntent() {
        return TripCreatorWizardPageActivity.class;
    }

    @Override
    protected AbstractWizardPage getWizardPageFragmentInstance(TripCreatorWizardElement element, int index) {

        return TripCreatorWizardPageFragment.newInstance(element, index);
    }

}
