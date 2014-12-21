package pl.edu.agh.activities.tripcreator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * Created by Sławek on 2014-12-09.
 */
public abstract class AbstractWizardPage<T extends Serializable> extends Fragment {

    protected View view;
    public static final String KEY_INDEX = "index";
    public static final String KEY_ITEM = "listItem";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        showWizardPage();
        return view;
    }

    public long getDisplayedWizardPageIndex() { return getArguments() != null ? getArguments().getLong("index", 0) : 0; }

    protected void setInitialArguments(long index, T listItem) {
        Bundle args = new Bundle();
        args.putLong(KEY_INDEX, index);
        args.putSerializable(KEY_ITEM, listItem);
        setArguments(args);
    }

    protected abstract int getLayoutId();
    protected abstract void showWizardPage();

}
