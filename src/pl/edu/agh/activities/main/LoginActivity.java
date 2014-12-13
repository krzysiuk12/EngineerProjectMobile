package pl.edu.agh.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import pl.edu.agh.activities.main.MainMenuActivity;
import pl.edu.agh.configuration.TestDatabaseHelper;
import pl.edu.agh.layout.toast.ErrorToastBuilder;
import pl.edu.agh.main.R;
import pl.edu.agh.serializers.LoginSerializer;
import pl.edu.agh.serializers.common.ResponseSerializer;
import pl.edu.agh.services.implementation.UserAccountManagementService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Krzysiu on 2014-06-11.
 */
public class LoginActivity extends OrmLiteBaseActivity<TestDatabaseHelper> {

	private EditText loginEditText;
	private EditText passwordEditText;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

		//Tests - moved from MainMenuActivity
		this.deleteDatabase(TestDatabaseHelper.DATABASE_NAME);
		new TestDatabaseHelper(this).getReadableDatabase();

        getActionBar().hide();

        ((Button)findViewById(R.id.LoginActivity_LoginButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonAction(view);
            }
        });
    }

    public void loginButtonAction(View view) {
		boolean loginResult = new UserAccountManagementService(this).logIn(getLoginEditText().getText().toString(), getPasswordEditText().getText().toString());
	    if ( loginResult ) {
		    finish(); // Removes Activity from Stack - Leaving main menu closes the app
		    startActivity(new Intent(this, MainMenuActivity.class));
	    } else {
		    new ErrorToastBuilder(this, "Invalid credentials.").build().show();
	    }
    }

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


}