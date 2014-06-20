package pl.edu.agh.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Magdalena Strzoda on 2014-06-16.
 */
public class LocationAdapter extends ArrayAdapter<Location> {

	public LocationAdapter(Context context, ArrayList<Location> locations) {
		super(context, R.layout.list_item_location, locations);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Location location = getItem(position);

		// Check if an existing view is being reused, otherwise inflate the view
		if ( convertView == null ) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_location, parent, false);
		}
		TextView tvName = (TextView) convertView.findViewById(R.id.ListItem_Location_Name);
		tvName.setText(location.getName());
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return ((Location) getItem(position)).getId();
	}
}
