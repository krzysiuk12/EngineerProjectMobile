package pl.edu.agh.activities.tripCreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-12-20.
 */
public class TripCreatorWizardPageFragment extends AbstractWizardPage<TripCreatorWizardElement> {

    private TripCreatorWizardElement displayedWizardElement;

    protected WizardPageAdapter getAdapterInstance() {
        ArrayList<TripCreatorWizardElement> wizardPagesList = new ArrayList<>();
        wizardPagesList.add(new TripCreatorWizardElement(R.layout.trip_creator_init_page, getString(R.string.TripCreatorInitPage_Title), true));
        wizardPagesList.add(new TripCreatorWizardElement(R.layout.trip_creator_main_settings_page, getString(R.string.TripCreatorMainSettingsPage_Title), true));
        wizardPagesList.add(new TripCreatorWizardElement(R.layout.trip_creator_days_list_page, getString(R.string.TripCreatorDaysListPage_Title), false));
        return new WizardPageAdapter(getActivity(), wizardPagesList);
    }

    public static TripCreatorWizardPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorWizardPageFragment fragment = new TripCreatorWizardPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        displayedWizardElement = (TripCreatorWizardElement) getArguments().getSerializable(KEY_ITEM);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return displayedWizardElement.getLayoutId();
    }

    @Override
    protected void showWizardPage() {

    }
}
