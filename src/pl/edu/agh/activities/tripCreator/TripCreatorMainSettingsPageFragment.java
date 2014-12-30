package pl.edu.agh.activities.tripCreator;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-24.
 */
public class TripCreatorMainSettingsPageFragment extends TripCreatorWizardPageFragment {

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


}
