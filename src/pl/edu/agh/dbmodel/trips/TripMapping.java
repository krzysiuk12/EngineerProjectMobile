package pl.edu.agh.dbmodel.trips;

import pl.edu.agh.dbmodel.common.BaseMapping;

/**
 * Created by Krzysiu on 2014-09-15.
 */
public class TripMapping extends BaseMapping {

    public static final String TABLE_NAME = "trips";
    public static final String GLOBAL_ID_COLUMN_NAME = "globalid";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String DESCRIPTION_COLUMN_NAME = "description";
    public static final String AUTHOR_COLUMN_NAME = "id_author";
    public static final String START_DATE_COLUMN_NAME = "startdate";
    public static final String END_DATE_COLUMN_NAME = "enddate";
    public static final String IS_SYNCED_COLUMN_NAME = "issynced";
    public static final String TRAVEL_MODE_COLUMN_NAME = "tavelmode";
    public static final String DISTANCE_UNIT_COLUMN_NAME = "distanceunit";

}
