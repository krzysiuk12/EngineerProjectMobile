package pl.edu.agh.fragments;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.trips.TripDirection;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Magda on 2014-12-22.
 */
public class TripDirectionAdapter extends AbstractAdapter<TripDirection> {

	public TripDirectionAdapter(Context context, ArrayList<TripDirection> items) {
		super(context, R.layout.trip_direction_list_item, items);
	}

	@Override
	protected void setLabels(View view, TripDirection item) {
		RenderingTools.setHtmlTextViewText(view, R.id.TripDetails_TripDirection_Instruction, item.getInstruction());
		RenderingTools.setTextViewText(view, R.id.TripDetails_TripDirection_Distance, item.getDistance());
		RenderingTools.setTextViewText(view, R.id.TripDetails_TripDirection_Duration, item.getDuration());
	}

}
