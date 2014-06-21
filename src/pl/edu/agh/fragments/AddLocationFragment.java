package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import pl.edu.agh.main.R;

/**
 * Created by Sławomir on 20.06.14.
 */
public class AddLocationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_location_fragment, container, false);
    }
}