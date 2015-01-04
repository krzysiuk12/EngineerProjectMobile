package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import pl.edu.agh.domain.trips.DistanceUnit;
import pl.edu.agh.domain.trips.TravelMode;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.layout.listeners.AfterTextChangedTextWatcher;
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
    private Trip trip;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);

        displayedWizardElement = (TripCreatorWizardElement) getArguments().getSerializable(KEY_ITEM);
        trip = displayedWizardElement.getTrip();

        ((Button)view.findViewById(R.id.TripCreatorMainSettingsPage_PageBackButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TripCreatorInitPageFragment fragment = (TripCreatorInitPageFragment) TripCreatorInitPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(0), 0);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
//                ft.commit();
            }
        });

        ((Button)view.findViewById(R.id.TripCreatorMainSettingsPage_PageForwardButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TripCreatorDaysListPageFragment fragment = (TripCreatorDaysListPageFragment) TripCreatorDaysListPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(2), 2);
                TripCreatorWizardElement wizardElement = (TripCreatorWizardElement)getAdapterInstance().getItem(2);
                wizardElement.setTrip(trip);
                TripCreatorDaysListPageFragment fragment = (TripCreatorDaysListPageFragment) TripCreatorDaysListPageFragment.newInstance(wizardElement, 2);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });

        updateWizardPageFields(view, getTrip());

        ArrayAdapter<DistanceUnit> distanceUnitAdapter = new ArrayAdapter<DistanceUnit>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, DistanceUnit.values());
        distanceUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getDistanceUnitSpinner(view).setAdapter(distanceUnitAdapter);

        ArrayAdapter<TravelMode> travelModeAdapter = new ArrayAdapter<TravelMode>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, TravelMode.values());
        travelModeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getTravelModeSpinner(view).setAdapter(travelModeAdapter);

        getTripNameEditText(view).addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getTrip().setName(getTripNameEditText(view).getText().toString());
            }
        });

        getTripDescriptionEditText(view).addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                getTrip().setDescription(getTripDescriptionEditText(view).getText().toString());
            }
        });

        getDistanceUnitSpinner(view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTrip().setDistanceUnit((DistanceUnit) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getTrip().setDistanceUnit(DistanceUnit.METRIC);  //default
            }
        });

        getTravelModeSpinner(view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTrip().setTravelMode((TravelMode) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getTrip().setTravelMode(TravelMode.WALKING);  //default
            }
        });

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

        Calendar calendar = dateToCalendar(trip.getStartDate());
        getTripStartDatePicker(view).init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                getTrip().setStartDate(newCalendar.getTime());
            }
        });

        calendar = dateToCalendar(trip.getEndDate());
        getTripEndDatePicker(view).init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, monthOfYear, dayOfMonth);
                getTrip().setEndDate(newCalendar.getTime());
            }
        });
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

    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public Spinner getDistanceUnitSpinner(View view) {
        if(distanceUnitSpinner == null) {
            distanceUnitSpinner = ((Spinner)view.findViewById(R.id.TripCreatorMainSettingsPage_DistanceUnitSpinner));
        }
        return distanceUnitSpinner;
    }

    public Spinner getTravelModeSpinner(View view) {
        if(travelModeSpinner == null) {
            travelModeSpinner = ((Spinner)view.findViewById(R.id.TripCreatorMainSettingsPage_TravelModeSpinner));
        }
        return travelModeSpinner;
    }

    public Trip getTrip() {
        if(trip == null) {
            trip = new Trip();
            trip.setStartDate(new Date());
            trip.setEndDate(new Date());
        }
        return trip;
    }

}
