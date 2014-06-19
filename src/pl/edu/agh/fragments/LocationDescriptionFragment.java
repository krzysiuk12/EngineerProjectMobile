package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.main.R;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-14.
 */
public class LocationDescriptionFragment extends Fragment {

	public static LocationDescriptionFragment newInstance(int index) {
		LocationDescriptionFragment f = new LocationDescriptionFragment();

		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getDisplayedItemIndex() {
		return getArguments() != null ? getArguments().getInt("index", 0) : 0;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.location_description_fragment, container, false);
	}
}
