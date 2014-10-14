package pl.edu.agh.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import pl.edu.agh.domain.common.BaseObject;

import java.util.ArrayList;

/**
 * Created by Magda on 2014-10-14.
 */
public abstract class AbstractAdapter<T extends BaseObject> extends ArrayAdapter<T> {

	protected int layoutId;

	public AbstractAdapter(Context context, int layoutId, ArrayList<T> items) {
		super(context, layoutId, items);
		this.layoutId = layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Check if an existing view is being reused, otherwise inflate the view
		if ( convertView == null ) {
			convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
		}
		T item = getItem(position);
		setLabels(convertView, item);
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	// renders view for specific item
	protected abstract void setLabels(View view, T item);
}
