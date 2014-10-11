package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.domain.common.BaseObject;

import java.sql.Timestamp;

/**
 * Created by Sławek on 12.06.14.
 */

@DatabaseTable(tableName = "UserAccounts")
public class UserAccount extends BaseObject {

    @DatabaseField(columnName = "token", canBeNull = false, width = 50)
    private String token;

    @DatabaseField(columnName = "login", canBeNull = false, unique = true, width = 50)
    private String login;

    @DatabaseField(columnName = "password", canBeNull = false)
    private String password;

    @DatabaseField(columnName = "status", canBeNull = false, foreign = true)
    private UserAccountStatusEvent status;

    @DatabaseField(columnName = "invalidSignInAttemptsCounter")
    private int invalidSignInAttemptsCounter;

    @DatabaseField(columnName = "lockedOutCounter")
    private int lockedOutCounter;

    @DatabaseField(columnName = "creationDate")
    private Timestamp creationDate;

    @DatabaseField(columnName = "isWizardDone")
    private boolean isWizardDone;

    @DatabaseField(columnName = "accountGroup")
    private String accountGroup;

    @DatabaseField(columnName = "isDefaultUser")
    private boolean isDefaultUser;

    @DatabaseField(columnName = "individual", foreign = true)
    private Individual individual;

    @DatabaseField(columnName = "language")
    private String language;

    public UserAccount() {
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getInvalidSignInAttemptsCounter() {
        return invalidSignInAttemptsCounter;
    }
    public void setInvalidSignInAttemptsCounter(int invalidSignInAttemptsCounter) {
        this.invalidSignInAttemptsCounter = invalidSignInAttemptsCounter;
    }

    public int getLockedOutCounter() {
        return lockedOutCounter;
    }
    public void setLockedOutCounter(int lockedOutCounter) {
        this.lockedOutCounter = lockedOutCounter;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isWizardDone() {
        return isWizardDone;
    }
    public void setWizardDone(boolean isWizardDone) {
        this.isWizardDone = isWizardDone;
    }

    public String getAccountGroup() {
        return accountGroup;
    }
    public void setAccountGroup(String accountGroup) {
        this.accountGroup = accountGroup;
    }

    public boolean isDefaultUser() {
        return isDefaultUser;
    }
    public void setDefaultUser(boolean isDefaultUser) {
        this.isDefaultUser = isDefaultUser;
    }

    public Individual getIndividual() {
        return individual;
    }
    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public UserAccountStatusEvent getStatus() {
        return status;
    }
    public void setStatus(UserAccountStatusEvent status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                ", login=" + login +
                ", password=" + password +
                ", status=" + status.toString() +
                ", invalidSignInAttemptsCounter=" + invalidSignInAttemptsCounter +
                ", lockedOutCounter=" + lockedOutCounter +
                ", creationDate=" + creationDate +
                ", isWizardDone=" + isWizardDone +
                ", accountGroup=" + accountGroup +
                ", isDefaultUser=" + isDefaultUser +
                ", getIndividual=" + individual.toString() +
                ", getLanguage=" + language +
                "}\n";
    }

}