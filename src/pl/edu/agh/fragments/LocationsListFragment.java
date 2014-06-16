package pl.edu.agh.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import pl.edu.agh.activities.LocationDescriptionActivity;
import pl.edu.agh.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Magdalena Strzoda (Llostris) on 2014-06-15.
 */
public class LocationsListFragment extends ListFragment {
	private int currentPosition = 0;
	private boolean isDualPane;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View detailsFrame = getActivity().findViewById(R.id.ShowLocations_LocationDescription);
		isDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		// test
		List<String> list = new ArrayList<String>();
		list.add("1111");
		list.add("2222");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, list));

		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt("currentPosition", 0);
		}

		if ( isDualPane ) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);   // enable selected item highlighting
			//showDetails(currentPosition); // uncomment to select first element of the list on startup
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentPosition", currentPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		getListView().setItemChecked(position, true);
		showDetails(position);
	}

	private void showDetails(int index) {
		currentPosition = index;

		if ( isDualPane ) {
			getListView().setItemChecked(index, true);

			LocationDescriptionFragment descriptionFragment = (LocationDescriptionFragment)
					getFragmentManager().findFragmentById(R.id.ShowLocations_LocationDescription);

			if ( descriptionFragment == null || descriptionFragment.getDisplayedItemIndex() != index ) {
				descriptionFragment = LocationDescriptionFragment.newInstance(index);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.ShowLocations_LocationDescription, descriptionFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			// launch a new activity
			Log.d("LocationListFragment", "showDetails() new intent");
			Intent intent = new Intent();
			intent.setClass(getActivity(), LocationDescriptionActivity.class);
			intent.putExtra("index", index);

			startActivity(intent);
		}
	}

}
