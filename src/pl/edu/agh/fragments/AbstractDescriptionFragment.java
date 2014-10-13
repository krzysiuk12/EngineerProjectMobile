package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Magda on 2014-10-12.
 */
public abstract class AbstractDescriptionFragment extends Fragment {

	protected View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		setLayout(inflater, container);
		showDetails();
		return view;
	}

	public long getDisplayedItemIndex() {
		return getArguments() != null ? getArguments().getLong("index", 0) : 0;
	}

	protected void setInitialArguments(long index) {
		Bundle args = new Bundle();
		args.putLong("index", index);
		setArguments(args);
	}

	protected void setTextViewText(int textViewId, String value) {
		TextView textView = (TextView) view.findViewById(textViewId);
		textView.setText(value);
	}

	protected abstract void setLayout(LayoutInflater inflater, ViewGroup container);

	protected abstract void showDetails();
}
