package pl.edu.agh.tools;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Magda on 2014-10-15.
 */
public class RenderingTools {

	public static void setTextViewText(View view, int textViewId, String value) {
		TextView tvName = (TextView) view.findViewById(textViewId);
		tvName.setText(value);
	}
}
