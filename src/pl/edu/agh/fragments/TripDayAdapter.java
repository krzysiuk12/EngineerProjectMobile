package pl.edu.agh.fragments;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Magda on 2014-12-22.
 */
public class TripDayAdapter extends AbstractAdapter<TripDay> {

	public TripDayAdapter(Context context, ArrayList<TripDay> items) {
		super(context, R.layout.trip_day_list_item, items);
	}

	@Override
	protected void setLabels(View view, TripDay item) {
		RenderingTools.setTextViewText(view, R.id.TripDetails_TripDayItem_Name, RenderingTools.formatDate(item.getDate()));
	}

}
