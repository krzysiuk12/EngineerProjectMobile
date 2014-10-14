package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Magda on 2014-10-12.
 */
public abstract class AbstractDescriptionFragment<T extends Serializable> extends Fragment {

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

	protected void setInitialArguments(long index, T listItem) {
		Bundle args = new Bundle();
		args.putLong("index", index);
		args.putSerializable("listItem", listItem);
		setArguments(args);
	}

	protected void setTextViewText(int textViewId, String value) {
		TextView textView = (TextView) view.findViewById(textViewId);
		textView.setText(value);
	}

	protected abstract void setLayout(LayoutInflater inflater, ViewGroup container);

	protected abstract void showDetails();
}
