package pl.edu.agh.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import pl.edu.agh.layout.listeners.AfterTextChangedTextWatcher;
import pl.edu.agh.layout.listeners.dialogs.EmptyOnClickListener;
import pl.edu.agh.main.R;

/**
 * Created by Krzysztof Kicinger on 2014-10-11.
 */
public class GeocodeSearchDialogFragment extends DialogFragment {

    public interface GeocodeSearchDialogListener {
        public void onDialogPositiveClick(String locationName);
    }

    private String locationName;
    private GeocodeSearchDialogListener activityListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            setActivityListener((GeocodeSearchDialogListener)activity);
        } catch(ClassCastException ex) {
            //TODO: Logging
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View layout = getActivity().getLayoutInflater().inflate(R.layout.geocode_search_dialog, null);
        ((EditText)layout.findViewById(R.id.GeocodeSearchDialog_Button_Search_EditText)).addTextChangedListener(new AfterTextChangedTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                setLocationName(editable.toString());
            }
        });

        builder.setIcon(getActivity().getResources().getDrawable(R.drawable.ic_search))
                .setTitle(R.string.SearchGeocodeDialogFragment_Title)
                .setView(layout)
                .setPositiveButton(R.string.SearchGeocodeDialogFragment_Button_Search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivityListener().onDialogPositiveClick("agh");
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.SearchGeocodeDialogFragment_Button_Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public GeocodeSearchDialogListener getActivityListener() {
        return activityListener;
    }

    public void setActivityListener(GeocodeSearchDialogListener activityListener) {
        this.activityListener = activityListener;
    }
}
