package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-28.
 */
public class TripCreatorDaysListPageFragment extends TripCreatorWizardPageFragment {

    private Trip trip;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        displayedWizardElement = (TripCreatorWizardElement) getArguments().getSerializable(KEY_ITEM);
        trip = displayedWizardElement.getTrip();

        ((Button)view.findViewById(R.id.TripCreatorDaysListPage_PageBackButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorWizardElement wizardElement = (TripCreatorWizardElement)getAdapterInstance().getItem(1);
                wizardElement.setTrip(trip);
                TripCreatorMainSettingsPageFragment fragment = (TripCreatorMainSettingsPageFragment) TripCreatorMainSettingsPageFragment.newInstance(wizardElement, 1);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        ((Button)view.findViewById(R.id.TripCreatorDaysListPage_PageForwardButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TripCreatorWizardElement wizardElement = (TripCreatorWizardElement)getAdapterInstance().getItem(3);
                wizardElement.setTrip(trip);
                TripCreatorFinishPageFragment fragment = (TripCreatorFinishPageFragment) TripCreatorFinishPageFragment.newInstance(wizardElement, 3);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        TripCreatorDayListFragment tripDayListFragment = TripCreatorDayListFragment.newInstance(trip);
        getFragmentManager().beginTransaction().add(R.id.TripCreatorDaysListPage_ListOfDays, tripDayListFragment).commit();

        return view;
    }

    public static TripCreatorDaysListPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorDaysListPageFragment fragment = new TripCreatorDaysListPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }

    public Trip getTrip() {
        if(trip == null) {
            trip = new Trip();
        }
        return trip;
    }
}
