package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-21.
 */
public class TripCreatorInitPageFragment extends TripCreatorWizardPageFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ((Button) view.findViewById(R.id.TripCreatorInitPage_CreateTripButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorMainSettingsPageFragment fragment = (TripCreatorMainSettingsPageFragment) TripCreatorMainSettingsPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(1), 1);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
                ft.commit();
            }
        });
        return view;
    }


    public static TripCreatorInitPageFragment newInstance(TripCreatorWizardElement wizardElement, long index) {
        TripCreatorInitPageFragment fragment = new TripCreatorInitPageFragment();
        fragment.setInitialArguments(index, wizardElement);
        return fragment;
    }


}
