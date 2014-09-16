package pl.edu.agh.dbmodel.trips;

import pl.edu.agh.dbmodel.common.BaseMapping;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class TripDirectionMapping extends BaseMapping {

    public static final String TABLE_NAME = "tripdirections";
    public static final String ORDINAL_COLUMN_NAME = "ordinal";
    public static final String TRIP_STEP_COLUMN_NAME = "id_tripstep";
    public static final String DISTANCE_COLUMN_NAME = "distance";
    public static final String DURATION_COLUMN_NAME = "duration";
    public static final String START_LATITUDE_COLUMN_NAME = "startlatitude";
    public static final String START_LONGITUDE_COLUMN_NAME = "startlongitude";
    public static final String END_LATITUDE_COLUMN_NAME = "endlatitude";
    public static final String END_LONGITUDE_COLUMN_NAME = "endlongitude";
    public static final String INSTRUCTION_COLUMN_NAME = "instruction";
    public static final String TRAVEL_MODE_COLUMN_NAME = "travelmode";

}
