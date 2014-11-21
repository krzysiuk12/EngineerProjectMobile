package pl.edu.agh.tools;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import pl.edu.agh.main.R;

/**
 * Created by Magda on 2014-10-15.
 */
public class RenderingTools {

	public static void setTextViewText(View view, int textViewId, String value) {
		TextView tvName = (TextView) view.findViewById(textViewId);
		tvName.setText(value);
	}

	public static String getValueFromEditText(View view, int editTextId) {
		return ((EditText) view.findViewById(editTextId)).getText().toString();
	}
}
