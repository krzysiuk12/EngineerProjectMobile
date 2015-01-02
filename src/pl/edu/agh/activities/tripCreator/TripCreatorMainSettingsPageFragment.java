package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sławek on 2014-12-24.
 */
public class TripCreatorMainSettingsPageFragment extends TripCreatorWizardPageFragment {

    private EditText tripNameEditText;
    private EditText tripDescriptionEditText;
    private DatePicker tripStartDatePicker;
    private DatePicker tripEndDatePicker;
    private Spinner distanceUnitSpinner;
    private Spinner travelModeSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ((Button)view.findViewById(R.id.TripCreatorMainSettingsPage_PageBackButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorInitPageFragment fragment = (TripCreatorInitPageFragment) TripCreatorInitPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(0), 0);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        ((Button)view.findViewById(R.id.TripCreatorMainSettingsPage_PageForwardButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorDaysListPageFragment fragment = (TripCreatorDaysListPageFragment) TripCreatorDaysListPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(2), 2);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        Trip trip = new Trip();
        updateWizardPageFields(view, trip);

        return view;
    }


    public static TripCreatorMainSettingsPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorMainSettingsPageFragment fragment = new TripCreatorMainSettingsPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void updateWizardPageFields(View view, Trip trip) {
        getTripNameEditText(view).setText(trip.getName());
        getTripDescriptionEditText(view).setText(trip.getDescription());
        setDatePicker(trip.getStartDate(), getTripStartDatePicker(view));
        setDatePicker(trip.getEndDate(), getTripEndDatePicker(view));
    }

    public EditText getTripNameEditText(View view) {
        if(tripNameEditText == null) {
            tripNameEditText = ((EditText)view.findViewById(R.id.TripCreatorMainSettingsPage_TripNameEditText));
        }
        return tripNameEditText;
    }

    public EditText getTripDescriptionEditText(View view) {
        if(tripDescriptionEditText == null) {
            tripDescriptionEditText = ((EditText)view.findViewById(R.id.TripCreatorMainSettingPage_TripDescriptionEditText));
        }
        return tripDescriptionEditText;
    }

    public DatePicker getTripStartDatePicker(View view) {
        if(tripStartDatePicker == null) {
            tripStartDatePicker = ((DatePicker)view.findViewById(R.id.TripCreatorMainSettingsPage_TripStartDatePicker));
        }
        return tripStartDatePicker;
    }

    public DatePicker getTripEndDatePicker(View view) {
        if(tripEndDatePicker == null) {
            tripEndDatePicker = ((DatePicker)view.findViewById(R.id.TripCreatorMainSettingsPage_TripEndDatePicker));
        }
        return tripEndDatePicker;
    }

    public void setDatePicker(Date date, DatePicker datePicker) {
        Calendar calendar = dateToCalendar(date);
        datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
}

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
