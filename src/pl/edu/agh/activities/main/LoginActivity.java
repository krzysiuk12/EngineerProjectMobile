package pl.edu.agh.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.exceptions.SynchronizationException;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.services.implementation.UserAccountManagementService;
import pl.edu.agh.services.interfaces.IUserAccountManagementService;
import pl.edu.agh.tools.ErrorTools;


/**
 * Created by Krzysiu on 2014-06-11.
 */
public class LoginActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	private EditText loginEditText;
	private EditText passwordEditText;
	private IUserAccountManagementService userAccountManagementService;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

		//Tests - moved from MainMenuActivity
		this.deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
		new TestDatabaseHelper(this).getReadableDatabase();
		userAccountManagementService = new UserAccountManagementService(this);

        getActionBar().hide();

        ((Button)findViewById(R.id.LoginActivity_LoginButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonAction(view);
            }
        });

		((Button) findViewById(R.id.LoginActivity_LoginAsDefault)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				logAsDefaultAction(view);
			}
		});
    }

	// <editor-fold description="Actions">

	public void loginButtonAction(View view) {
		boolean loginResult = false;
		try {
			loginResult = userAccountManagementService.logIn(getLoginEditText().getText().toString(), getPasswordEditText().getText().toString());
		} catch (SynchronizationException e) {
			new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e.getExceptionDefinition())).build().show();
		}
		if ( loginResult ) {
			goToMainMenu();
		} else {
			new ErrorToastBuilder(this, "Invalid credentials.").build().show();
		}
    }

	private void logAsDefaultAction(View view) {
		boolean loginResult = false;
		try {
			loginResult = userAccountManagementService.logAsDefault();
		} catch (SynchronizationException e) {
			new ErrorToastBuilder(this, ErrorTools.createExceptionString(getResources(), e.getExceptionDefinition())).build().show();
		}
		if ( loginResult ) {
			goToMainMenu();
		} else {
			new ErrorToastBuilder(this, "No default user.").build().show();
		}
	}

	private void goToMainMenu() {
		finish(); // Removes Activity from Stack - Leaving main menu closes the app
		startActivity(new Intent(this, MainMenuActivity.class));
	}

	// </editor-fold>

	// <editor-fold description="Layout - getters">

	private EditText getLoginEditText() {
		if ( loginEditText == null ) {
			loginEditText = ((EditText) findViewById(R.id.LoginActivity_Login));
		}
		return loginEditText;
	}

	private EditText getPasswordEditText() {
		if ( passwordEditText == null ) {
			passwordEditText = ((EditText) findViewById(R.id.LoginActivity_Password));
		}
		return passwordEditText;
	}

	// </editor-fold.

}