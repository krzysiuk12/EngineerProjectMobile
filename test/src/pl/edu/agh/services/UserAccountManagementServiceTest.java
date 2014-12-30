package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.TestTools;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

/**
 * Created by Magda on 2014-12-27.
 */
public class UserAccountManagementServiceTest extends AndroidTestCase {

	private TestDatabaseHelper helper;

	private IUserAccountManagementService userAccountManagementService;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		helper = new TestDatabaseHelper(getContext());
		userAccountManagementService = new UserAccountManagementService(helper);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		helper.close();
		getContext().deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
	}

	public void testSaveUserAccount() throws Exception {
//		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
//		userAccountManagementService.saveUserAccount(userAccount);
//
//		UserAccount savedAccount = userAccountManagementService.getUserAccountByLogin("login");
//		assertNotNull(savedAccount);
//		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
//		assertEquals(userAccount.getPassword(), savedAccount.getPassword());
	}

	public void testUpdateUserAccount() throws Exception {
		// TODO
	}

	public void testGetUserAccountByLogin() throws Exception {
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		userAccountManagementService.saveUserAccount(userAccount);
		UserAccount userAccount2 = BaseTestObject.createUserAccount("login2", "password");
		userAccountManagementService.saveUserAccount(userAccount2);

		UserAccount savedAccount = userAccountManagementService.getUserAccountByLogin("login");
		assertNotNull(savedAccount);
		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
		assertEquals(userAccount.getPassword(), savedAccount.getPassword());

	}

	public void testGetUserAccountByToken() throws Exception{
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		String token = "logintoken";
		userAccount.setToken(token);
		userAccountManagementService.saveUserAccount(userAccount);

		UserAccount savedAccount = userAccountManagementService.getUserAccountByToken(token);
		assertNotNull(savedAccount);
		assertEquals(userAccount.getToken(), savedAccount.getToken());
		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
		assertEquals(userAccount.getPassword(), savedAccount.getPassword());
	}

	public void testGetDefaultUser() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("login1", "password");
		userAccountManagementService.saveUserAccount(userAccount1);
		userAccountManagementService.setAsDefaultUser(userAccount1, true);

		UserAccount defaultUser = userAccountManagementService.getDefaultUser();
		assertNotNull(defaultUser);
		assertTrue(defaultUser.isDefaultUser());
		TestTools.assertEquals(userAccount1, defaultUser);

		UserAccount userAccount2 = BaseTestObject.createUserAccount("login2", "password");
		userAccountManagementService.saveUserAccount(userAccount2);
		userAccountManagementService.setAsDefaultUser(userAccount2, true);

		defaultUser = userAccountManagementService.getDefaultUser();
		assertNotNull(defaultUser);
		assertTrue(defaultUser.isDefaultUser());
		TestTools.assertEquals(userAccount2, defaultUser);

		// test if previous user is not non-default
		UserAccount nonDefaultUser = userAccountManagementService.getUserAccountByLogin("login1");
		assertFalse(nonDefaultUser.isDefaultUser());
	}

	public void testSetDefaultUser() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("login1", "password");
		userAccountManagementService.saveUserAccount(userAccount1);
		userAccountManagementService.setAsDefaultUser(userAccount1, true);

		UserAccount defaultUser = userAccountManagementService.getDefaultUser();
		assertNotNull(defaultUser);
		assertTrue(defaultUser.isDefaultUser());
		TestTools.assertEquals(userAccount1, defaultUser);
	}

	public void testChangeLanguagePreferenceForUser() throws Exception {
		UserAccount userAccount1 = BaseTestObject.createUserAccount("login1", "password");
		userAccountManagementService.saveUserAccount(userAccount1);

		userAccountManagementService.changeLanguagePreferenceForUser(userAccount1, UserAccount.Language.PL);

		// test if preference was saved in database
		UserAccount userAccount2 = userAccountManagementService.getUserAccountByLogin("login1");
		assertNotNull(userAccount2);
		assertEquals(userAccount2.getLanguage(), UserAccount.Language.PL);
		TestTools.assertEquals(userAccount1, userAccount2);
	}

	public void testLogIn() throws Exception {
		// TODO: test AsycTask
	}

	public void testLogAsDefault() throws Exception {
		// requires running server
//		UserAccount userAccount = BaseTestObject.createUserAccount("admin", "admin");
//		userAccountManagementService.saveUserAccount(userAccount);
//		boolean result = userAccountManagementService.logAsDefault();
//		assertFalse(result);
//
//		userAccountManagementService.setAsDefaultUser(userAccount, true);
//		result = userAccountManagementService.logAsDefault();
//		assertTrue(result);
	}

	public void testLogOut() throws Exception {
		// TODO: test AsycTask
	}

}
