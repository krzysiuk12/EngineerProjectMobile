package pl.edu.agh.domain;

import com.google.android.gms.drive.internal.ac;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;

/**
 * Created by Sławek on 12.06.14.
 */

@DatabaseTable(tableName = "UserAccountStatusEvent")
public class UserAccountStatusEvent {

    @DatabaseField(generatedId = true, index = true)
    private Long id;

    @DatabaseField(columnName = "actionType")
    private String actionType;

    @DatabaseField(columnName = "startDate")
    private Timestamp startDate;

    @DatabaseField(columnName = "endDate")
    private Timestamp endDate;

    @DatabaseField(columnName = "userAccount")
    private UserAccount userAccount;

    public UserAccountStatusEvent() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getActionType() {
        return actionType;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "UserAccountStatusEvent{" +
                "id=" + id +
                ", getActionType=" + actionType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userAccount=" + userAccount.toString() +
                "}\n";
    }


}
