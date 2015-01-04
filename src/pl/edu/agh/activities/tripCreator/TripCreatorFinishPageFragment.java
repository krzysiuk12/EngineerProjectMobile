package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sławek on 2015-01-03.
 */
public class TripCreatorFinishPageFragment extends TripCreatorWizardPageFragment {

    private TextView tripNameTextView;
    private TextView tripStartDateTextView;
    private TextView tripEndDateTextView;
    private Trip trip;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        displayedWizardElement = (TripCreatorWizardElement) getArguments().getSerializable(KEY_ITEM);
        trip = displayedWizardElement.getTrip();

        ((Button)view.findViewById(R.id.TripCreatorFinishPage_PageBackButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorWizardElement wizardElement = (TripCreatorWizardElement)getAdapterInstance().getItem(2);
                wizardElement.setTrip(trip);
                TripCreatorDaysListPageFragment fragment = (TripCreatorDaysListPageFragment) TripCreatorDaysListPageFragment.newInstance(wizardElement, 2);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        ((Button)view.findViewById(R.id.TripCreatorFinishPage_FinishButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        updateWizardPageFields(view, getTrip());

        return view;
    }

    public static TripCreatorFinishPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorFinishPageFragment fragment = new TripCreatorFinishPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }

    public Trip getTrip() {
        if(trip == null) {
            trip = new Trip();
        }
        return trip;
    }

    private void updateWizardPageFields(View view, Trip trip) {
        if(trip.getName() != null && !trip.getName().isEmpty()) {
            getTripNameTextView(view).setText(trip.getName());
        }
        getTripStartDateTextView(view).setText(dateToString(trip.getStartDate()));
        getTripEndDateTextView(view).setText(dateToString(trip.getEndDate()));

    }

    public TextView getTripNameTextView(View view) {
        if(tripNameTextView == null) {
            tripNameTextView = ((TextView)view.findViewById(R.id.TripCreatorFinishPage_TripNameTextView));
        }
        return tripNameTextView;
    }

    public TextView getTripStartDateTextView(View view) {
        if(tripStartDateTextView == null) {
            tripStartDateTextView = ((TextView)view.findViewById(R.id.TripCreatorFinishPage_TripStartDateTextView));
        }
        return tripStartDateTextView;
    }

    public TextView getTripEndDateTextView(View view) {
        if(tripEndDateTextView == null) {
            tripEndDateTextView = ((TextView)view.findViewById(R.id.TripCreatorFinishPage_TripEndDateTextView));
        }
        return tripEndDateTextView;
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

}
