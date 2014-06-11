package pl.edu.agh.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import pl.edu.agh.main.R;

/**
 * Created by Krzysiu on 2014-06-11.
 */
public class LoginActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        ((Button)findViewById(R.id.LoginActivity_LoginButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonAction(view);
            }
        });
    }

    public void loginButtonAction(View view) {
        finish(); //Removes Activity from Stack - Leaving main menu closes the app
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}