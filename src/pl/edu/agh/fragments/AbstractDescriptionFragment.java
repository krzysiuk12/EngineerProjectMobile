package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.activities.settings.SettingsIssue;
import pl.edu.agh.services.implementation.AndroidLogService;

import java.io.Serializable;

/**
 * Created by Magda on 2014-10-12.
 */
public abstract class AbstractDescriptionFragment<T extends Serializable> extends Fragment {

	protected View view;

	public static final int REQUEST_CODE = 1;

	public static final String KEY_INDEX = "index";

	public static final String KEY_ITEM = "listItem";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), container, false);
		showDetails();
		return view;
	}

	public long getDisplayedItemIndex() {
		return getArguments() != null ? getArguments().getLong("index", 0) : 0;
	}

	protected void setInitialArguments(long index, T listItem) {
		Bundle args = new Bundle();
		args.putLong(KEY_INDEX, index);
		args.putSerializable(KEY_ITEM, listItem);
		setArguments(args);
	}

	/*
	Requires a following method to be implemented:

	public static AbstractDescriptionFragment<T> newInstance(T item, long index);
	 */

	protected abstract int getLayoutId();

	protected abstract void showDetails();

}
