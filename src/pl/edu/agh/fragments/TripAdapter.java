package pl.edu.agh.fragments;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.trips.Trip;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Magda on 2014-12-04.
 */
public class TripAdapter extends AbstractAdapter<Trip> {

	private final SimpleDateFormat simpleDateFormat;

	public TripAdapter(Context context, ArrayList<Trip> items) {
		super(context, R.layout.trip_list_item, items);
		simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
	}

	@Override
	protected void setLabels(View view, Trip item) {
		RenderingTools.setTextViewText(view, R.id.ShowTrips_TripItem_Name, item.getName());
		RenderingTools.setTextViewText(view, R.id.ShowTrips_TripItem_StartDate, simpleDateFormat.format(item.getStartDate()));
		RenderingTools.setTextViewText(view, R.id.ShowTrips_TripItem_EndDate, simpleDateFormat.format(item.getEndDate()));
	}

}
