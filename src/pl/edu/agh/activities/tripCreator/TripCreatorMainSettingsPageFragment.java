package pl.edu.agh.activities.tripCreator;

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
                TripCreatorWizardPageFragment fragment = (TripCreatorWizardPageFragment) TripCreatorMainSettingsPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(0), 0);
                getFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
            }
        });

        ((Button)view.findViewById(R.id.TripCreatorMainSettingsPage_PageForwardButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorWizardPageFragment fragment = (TripCreatorWizardPageFragment) TripCreatorMainSettingsPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(2), 2);
                getFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
            }
        });
        return view;
    }

}
