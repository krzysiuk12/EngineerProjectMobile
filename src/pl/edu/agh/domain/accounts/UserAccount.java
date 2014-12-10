package pl.edu.agh.domain.accounts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import pl.edu.agh.dbmodel.accounts.UserAccountMapping;
import pl.edu.agh.domain.common.BaseObject;

import java.io.Serializable;

/**
 * Created by Sławek on 12.06.14.
 */

@DatabaseTable(tableName = UserAccountMapping.TABLE_NAME)
public class UserAccount extends BaseObject implements Serializable {

    @DatabaseField(columnName = UserAccountMapping.TOKEN_COLUMN_NAME, canBeNull = false, width = 50)
    private String token;

    @DatabaseField(columnName = UserAccountMapping.LOGIN_COLUMN_NAME, canBeNull = false, unique = true, width = 50)
    private String login;

    @DatabaseField(columnName = UserAccountMapping.PASSWORD_COLUMN_NAME, canBeNull = false)
    private String password;

    @DatabaseField(columnName = UserAccountMapping.IS_WIZARD_DONE_COLUMN_NAME)
    private boolean isWizardDone;

    @DatabaseField(columnName = UserAccountMapping.IS_DEFAULT_USER_COLUMN_NAME)
    private boolean isDefaultUser;

    // TODO: remove?
    @DatabaseField(columnName = UserAccountMapping.INDIVIDUAL_COLUMN_NAME, canBeNull = false, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Individual individual;

    @DatabaseField(columnName = UserAccountMapping.LANGUAGE_COLUMN_NAME)
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

    public boolean isWizardDone() {
        return isWizardDone;
    }
    public void setWizardDone(boolean isWizardDone) {
        this.isWizardDone = isWizardDone;
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


    @Override
    public String toString() {
        return "UserAccount{" +
                ", login=" + login +
                ", password=" + password +
                ", isWizardDone=" + isWizardDone +
                ", isDefaultUser=" + isDefaultUser +
                ", getIndividual=" + ( individual != null ? individual.toString() : null) +
                ", getLanguage=" + language +
                "}\n";
    }

}
