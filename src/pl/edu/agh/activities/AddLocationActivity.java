package pl.edu.agh.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import pl.edu.agh.domain.Location;
import pl.edu.agh.main.R;

/**
 * Created by Sławomir on 19.06.14.
 */
public class AddLocationActivity extends Activity {

    private EditText locationNameEditText;
    private EditText locationDescriptionEditText;
    private Location location;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_location_activity);

        location = new Location();
        locationNameEditText = (EditText) findViewById(R.id.AddLocation_LocationNameEditText);

        locationNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                location.setName(locationNameEditText.getText().toString());
            }
        });

        locationDescriptionEditText = (EditText) findViewById(R.id.AddLocation_LocationDescription);

        locationDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                location.setDescription(locationDescriptionEditText.getText().toString());
            }
        });
    }
}