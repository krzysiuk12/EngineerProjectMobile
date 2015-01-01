package pl.edu.agh.repositories.implementation;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.dbmodel.accounts.UserAccountMapping;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.repositories.interfaces.IUserAccountRepository;

import java.util.List;

/**
 * Created by Magda on 2014-12-10.
 */
public class OrmLiteUserAccountRepository implements IUserAccountRepository {

	private OrmLiteSqliteOpenHelper openHelper;

	public OrmLiteUserAccountRepository(OrmLiteSqliteOpenHelper openHelper) {
		this.openHelper = openHelper;
	}

	@Override
	public void saveUserAccount(UserAccount userAccount) {
		((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().create(userAccount);
	}

	@Override
	public void updateUserAccount(UserAccount userAccount) {
		((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().update(userAccount);
	}

	public UserAccount getUserAccountByLogin(String login) {
		List<UserAccount> userAccountList = ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryForEq(UserAccountMapping.LOGIN_COLUMN_NAME, login);
		if ( !userAccountList.isEmpty() ) {
			return userAccountList.get(0);
		}
		return null;
	}

	public UserAccount getUserAccountByToken(String token) {
		List<UserAccount> userAccountList = ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryForEq(UserAccountMapping.TOKEN_COLUMN_NAME,token);
		if ( !userAccountList.isEmpty() ) {
			return userAccountList.get(0);
		}
		return null;
	}

	@Override
	public UserAccount getDefaultUser() {
		List<UserAccount> userAccounts = ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryForEq(UserAccountMapping.IS_DEFAULT_USER_COLUMN_NAME, true);
		if ( !userAccounts.isEmpty() )
			return userAccounts.get(0);
		else
			return null;
	}

	@Override
	public List<UserAccount> getAllUsers() {
		return ((TestDatabaseHelper) openHelper).getUserAccountRuntimeExceptionDao().queryForAll();
	}

}
