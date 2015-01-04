package pl.edu.agh.tools;

import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Magda on 2014-10-15.
 */
public class RenderingTools {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
	private static SimpleDateFormat simpleDateWithTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

	public static void setTextViewText(View view, int textViewId, String value) {
		TextView tvName = (TextView) view.findViewById(textViewId);
		tvName.setText(value);
	}

	public static void setHtmlTextViewText(View view, int textViewId, String value) {
		TextView tvName = (TextView) view.findViewById(textViewId);
		tvName.setText(Html.fromHtml(value));
	}

	public static String getValueFromEditText(View view, int editTextId) {
		return ((EditText) view.findViewById(editTextId)).getText().toString();
	}

	public static String formatDate(Date date) {
		if ( date != null ) {
			return simpleDateFormat.format(date);
		}
		return null;
	}

	public static String formatDateWithTime(Date date) {
		return simpleDateWithTimeFormat.format(date);
	}

    public static void setTextViewColor(View view, int textViewId, int color) {
        TextView tvName = (TextView) view.findViewById(textViewId);
        tvName.setTextColor(color);
    }

}
