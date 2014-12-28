package pl.edu.agh.activities.tripCreator;

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
        ((Button) view.findViewById(R.id.TripCreatorInitPage_ReturnToStepsViewButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripCreatorWizardPageFragment fragment = (TripCreatorWizardPageFragment) TripCreatorMainSettingsPageFragment.newInstance((TripCreatorWizardElement)getAdapterInstance().getItem(1), 1);
                getFragmentManager().beginTransaction().replace(container.getId(), fragment).commit();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        ViewGroup mContainer = (ViewGroup) getActivity().findViewById(((ViewGroup)(getView().getParent())).getId());
        mContainer.removeAllViews();
        super.onDestroyView();
    }
}
