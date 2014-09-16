package pl.edu.agh.dbmodel.trips;

import pl.edu.agh.dbmodel.common.BaseMapping;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class TripStepMapping extends BaseMapping {

    public static final String TABLE_NAME = "tripsteps";
    public static final String TRIP_DAY_COLUMN_NAME = "id_tripday";
    public static final String START_LOCATION_COLUMN_NAME = "id_startlocation";
    public static final String END_LOCATION_COLUMN_NAME = "id_endlocation";
    public static final String DISTANCE_COLUMN_NAME = "distance";
    public static final String DISTANCE_UNIT_COLUMN_NAME = "distanceunit";
    public static final String DURATION_COLUMN_NAME = "description";

}
