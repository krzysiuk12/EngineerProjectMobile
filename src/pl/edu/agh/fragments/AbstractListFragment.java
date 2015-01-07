package pl.edu.agh.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.Serializable;

/**
 * Created by Magda on 2014-10-13.
 */
public abstract class AbstractListFragment<T extends Serializable> extends ListFragment {
	protected static final String KEY_CURRENT_POSITION = "currentPosition";
	protected int currentPosition = 0;
	protected boolean isDualPane;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View detailsFrame = getActivity().findViewById(getDetailsPaneId());
		isDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		setListAdapter(getAdapterInstance());

		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
		}

		if ( isDualPane ) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);   // enable selected item highlighting
			//showDetails(currentPosition); // uncomment to select first element of the list on startup
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CURRENT_POSITION, currentPosition);
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
					getFragmentManager().findFragmentById(getDetailsPaneId());

			if ( descriptionFragment == null || descriptionFragment.getDisplayedItemIndex() != index ) {
				descriptionFragment = getDetailsFragmentInstance((T) getListAdapter().getItem(index), index);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(getDetailsPaneId(), descriptionFragment);
				ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				ft.commit();
			}
		} else {
			// launch a new activity
			Intent intent = new Intent();
			intent.setClass(getActivity(), getClassForDetailsIntent());
			intent.putExtra(AbstractDescriptionFragment.KEY_INDEX, getListAdapter().getItemId(index));
			intent.putExtra(AbstractDescriptionFragment.KEY_ITEM, (Serializable) getListAdapter().getItem(index));

			startActivityForResult(intent, AbstractDescriptionFragment.REQUEST_CODE);
		}
	}

	protected abstract AbstractAdapter getAdapterInstance();

	protected abstract Class getClassForDetailsIntent();

	protected abstract AbstractDescriptionFragment getDetailsFragmentInstance(T listItem, int index);

	protected abstract int getDetailsPaneId();
}
