package pl.edu.agh.fragments.locations;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import pl.edu.agh.domain.accounts.Address;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

/**
 * Created by Sławomir on 20.06.14.
 */
public class AddLocationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_location_fragment, container, true);

	    Spinner statusSpinner = (Spinner) view.findViewById(R.id.AddLocation_StatusSpinner);
	    ArrayAdapter<Location.Status> adapter = new ArrayAdapter<Location.Status>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Location.Status.values());
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    statusSpinner.setAdapter(adapter);

	    return view;
    }

}