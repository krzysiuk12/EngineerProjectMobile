package pl.edu.agh.activities.tripCreator;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Sławek on 2015-01-05.
 */
public class TripDayLocationAdapter extends AbstractAdapter<TripDayLocation> {

    public TripDayLocationAdapter(Context context, int layoutId, ArrayList<TripDayLocation> items) {
        super(context, R.layout.trip_day_location_list_item, items);
    }

    @Override
    protected void setLabels(View view, TripDayLocation item) {
        RenderingTools.setTextViewText(view, R.id.TripDayLocations_TripDayLocationItem_Name, item.getLocation().getName());
    }
}
