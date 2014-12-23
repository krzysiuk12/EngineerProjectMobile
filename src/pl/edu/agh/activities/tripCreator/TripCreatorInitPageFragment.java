package pl.edu.agh.activities.tripcreator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import pl.edu.agh.activities.main.MainMenuActivity;
import pl.edu.agh.main.R;

/**
 * Created by Sławek on 2014-12-21.
 */
public class TripCreatorInitPageFragment extends TripCreatorWizardPageFragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Button) getView().findViewById(R.id.TripCreatorInitPage_ReturnToStepsViewButton)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().popBackStack();
            }
        });
    }

}
