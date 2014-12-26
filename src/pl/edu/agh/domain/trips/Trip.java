package pl.edu.agh.domain.trips;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.trips.TripMapping;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.domain.common.GlobalBaseObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-09-14.
 */
@DatabaseTable(tableName = TripMapping.TABLE_NAME)
public class Trip extends GlobalBaseObject {

    @DatabaseField(columnName = TripMapping.NAME_COLUMN_NAME, width = 100, canBeNull = false)
    private String name;

    @DatabaseField(columnName = TripMapping.DESCRIPTION_COLUMN_NAME, width = 500)
    private String description;

    @DatabaseField(columnName = TripMapping.AUTHOR_COLUMN_NAME, foreign = true, canBeNull = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private UserAccount author;

    @ForeignCollectionField(eager = true, foreignFieldName = "trip", maxEagerLevel = 6)
    private Collection<TripDay> days;

    @DatabaseField(columnName = TripMapping.START_DATE_COLUMN_NAME, dataType = DataType.DATE, canBeNull = false)
    private Date startDate;

    @DatabaseField(columnName = TripMapping.END_DATE_COLUMN_NAME, dataType = DataType.DATE, canBeNull = false)
    private Date endDate;

    @DatabaseField(columnName = TripMapping.IS_SYNCED_COLUMN_NAME, dataType = DataType.BOOLEAN, canBeNull = false)
    private boolean isSynced;

    public Trip() {
        isSynced = true;
    }

    public Trip(String name, String description, UserAccount author, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccount getAuthor() {
        return author;
    }

    public void setAuthor(UserAccount author) {
        this.author = author;
    }

    public Collection<TripDay> getDays() {
        return new ArrayList<>(days);
    }

    public void setDays(Collection<TripDay> days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name=\'" + name + "\', " +
                "description=\'" + description + "\', " +
                "startDate=" + startDate.toString() + ", " +
                "endDate=" + endDate.toString() + ", " +
                "daysSize=" + days.size() +
                "}";
    }
}
