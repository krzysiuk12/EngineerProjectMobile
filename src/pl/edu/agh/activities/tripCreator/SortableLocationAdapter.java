package pl.edu.agh.activities.tripCreator;

import android.content.Context;
import android.view.View;
import pl.edu.agh.domain.locations.Location;
import pl.edu.agh.domain.trips.TripDay;
import pl.edu.agh.domain.trips.TripDayLocation;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sławek on 2015-01-05.
 */
public class SortableLocationAdapter extends AbstractAdapter<Location> {

    final int INVALID_ID = -1;

    HashMap<Location, Integer> mIdMap = new HashMap<Location, Integer>();

    public SortableLocationAdapter(Context context, ArrayList<Location> items) {
        super(context, R.layout.trip_day_location_list_item, items);
        for (int i = 0; i < items.size(); ++i) {
            mIdMap.put(items.get(i), i);
        }
    }

    @Override
    protected void setLabels(View view, Location item) {
        RenderingTools.setTextViewText(view, R.id.TripDayLocations_TripDayLocationItem_Name, item.getName());
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Location item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void add(Location object) {
        mIdMap.put(object, mIdMap.size());
        super.add(object);
    }

    @Override
    public void remove(Location object) {
        mIdMap.remove(object);
        super.remove(object);
    }

}
