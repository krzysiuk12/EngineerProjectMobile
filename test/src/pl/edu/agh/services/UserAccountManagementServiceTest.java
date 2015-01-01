package pl.edu.agh.services;

import android.test.AndroidTestCase;
import pl.edu.agh.BaseTestObject;
import pl.edu.agh.TestTools;
import pl.edu.agh.activities.main.MainMenuActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.domain.accounts.UserAccount;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		userAccountManagementService.saveUserAccount(userAccount);

		UserAccount savedAccount = userAccountManagementService.getUserAccountByLogin(userAccount.getLogin());
		assertNotNull(savedAccount);
		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
		assertEquals(userAccount.getPassword(), savedAccount.getPassword());
	}

	public void testUpdateUserAccount() throws Exception {
		UserAccount userAccount = BaseTestObject.createUserAccount("login", "password");
		userAccountManagementService.saveUserAccount(userAccount);

		userAccount.setToken("newtoken");

		userAccountManagementService.updateUserAccount(userAccount);
		UserAccount savedAccount = userAccountManagementService.getUserAccountByLogin(userAccount.getLogin());
		assertNotNull(savedAccount);
		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
		assertEquals(userAccount.getPassword(), savedAccount.getPassword());
		assertEquals(userAccount.getToken(), savedAccount.getToken());
	}

	public void testGetUserAccountByLogin() throws Exception {
		UserAccount nonExistenAccount = userAccountManagementService.getUserAccountByLogin("login");
		assertNull(nonExistenAccount);

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

		UserAccount nonExistenAccount = userAccountManagementService.getUserAccountByToken(userAccount.getToken());
		assertNull(nonExistenAccount);

		userAccountManagementService.saveUserAccount(userAccount);

		UserAccount savedAccount = userAccountManagementService.getUserAccountByToken(userAccount.getToken());
		assertNotNull(savedAccount);
		assertEquals(userAccount.getToken(), savedAccount.getToken());
		assertEquals(userAccount.getLogin(), savedAccount.getLogin());
		assertEquals(userAccount.getPassword(), savedAccount.getPassword());
	}

	public void testGetDefaultUser() throws Exception {
		UserAccount defaultUser = userAccountManagementService.getDefaultUser();
		assertNull(defaultUser);

		UserAccount userAccount1 = BaseTestObject.createUserAccount("login1", "password");
		userAccountManagementService.saveUserAccount(userAccount1);
		userAccountManagementService.setAsDefaultUser(userAccount1, true);

		defaultUser = userAccountManagementService.getDefaultUser();
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

		userAccountManagementService.setAsDefaultUser(userAccount2, false);
		UserAccount nonExistentUser = userAccountManagementService.getDefaultUser();
		assertNull(nonExistentUser);
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
		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					userAccountManagementService.logIn("admin", "admin");
				} catch (SynchronizationException e) {
					e.printStackTrace();
					fail("SynchronizationException: " + e.toString());
				}
				assertNotNull(UserAccountManagementService.getToken());
				assertNotSame("admin", UserAccountManagementService.getToken());
				assertNotNull(UserAccountManagementService.getUserAccount());
				assertEquals("admin", UserAccountManagementService.getUserAccount().getLogin());
				assertEquals("admin", UserAccountManagementService.getUserAccount().getPassword());
				signal.countDown();
			}
		}).start();

		try {
			signal.await(10, TimeUnit.SECONDS);// wait for callback
		} catch (InterruptedException e1) {
			fail("Timeout");
			e1.printStackTrace();
		}
	}

	public void testLogAsDefault() throws Exception {
		// requires running server
		final String login = "admin";
		UserAccount userAccount = BaseTestObject.createUserAccount(login, login);
		userAccountManagementService.saveUserAccount(userAccount);
		boolean result = userAccountManagementService.logAsDefault();
		assertFalse(result);

		userAccountManagementService.setAsDefaultUser(userAccount, true);

		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					boolean result = userAccountManagementService.logAsDefault();
					assertTrue(result);
				} catch (SynchronizationException e) {
					e.printStackTrace();
					fail("SynchronizationException: " + e.toString());
				}
				assertNotNull(UserAccountManagementService.getToken());
				assertNotSame(login, UserAccountManagementService.getToken());
				assertNotNull(UserAccountManagementService.getUserAccount());
				assertEquals(login, UserAccountManagementService.getUserAccount().getLogin());
				assertEquals(login, UserAccountManagementService.getUserAccount().getPassword());
				assertEquals(true, UserAccountManagementService.getUserAccount().isDefaultUser());
				signal.countDown();
			}
		}).start();

		try {
			signal.await(10, TimeUnit.SECONDS);// wait for callback
		} catch (InterruptedException e1) {
			fail("Timeout");
			e1.printStackTrace();
		}
	}

	public void testLogOut() throws Exception {
		// requires running server
		final String login = "admin";

		final CountDownLatch signal = new CountDownLatch(1);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					boolean result = userAccountManagementService.logIn(login, login);
					userAccountManagementService.logOut(UserAccountManagementService.getToken());
					assertNull(UserAccountManagementService.getToken());
					assertNull(UserAccountManagementService.getUserAccount());
					signal.countDown();
				} catch (SynchronizationException e) {
					e.printStackTrace();
					fail("SynchronizationException: " + e.toString());
				}
			}
		}).start();

		try {
			signal.await(10, TimeUnit.SECONDS);// wait for callback
		} catch (InterruptedException e1) {
			fail("Timeout");
			e1.printStackTrace();
		}
	}

}
