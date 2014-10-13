package pl.edu.agh.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Magda on 2014-10-13.
 */
public abstract class AbstractListFragment extends ListFragment {
	protected int currentPosition = 0;
	protected boolean isDualPane;
	protected int detailsPaneId;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View detailsFrame = getActivity().findViewById(detailsPaneId);
		isDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		setListAdapter();

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

	protected void showDetails(int index) {
		currentPosition = index;

		if ( isDualPane ) {
			// replace fragment in current activity
			getListView().setItemChecked(index, true);

			AbstractDescriptionFragment descriptionFragment = (AbstractDescriptionFragment)
					getFragmentManager().findFragmentById(detailsPaneId);

			if ( descriptionFragment == null || descriptionFragment.getDisplayedItemIndex() != index ) {
				descriptionFragment = getDetailsFragmentInstance(index);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(detailsPaneId, descriptionFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			// launch a new activity
			Intent intent = new Intent();
			setClassForDetailsIntent(intent);
			intent.putExtra("index", getListAdapter().getItemId(index));

//			intent.putExtra("location", (Serializable) getListAdapter().getItem(index)); // to test

			startActivity(intent);
		}
	}

	protected abstract void setListAdapter();

	protected abstract void setClassForDetailsIntent(Intent intent);

	protected abstract AbstractDescriptionFragment getDetailsFragmentInstance(int index);
}
