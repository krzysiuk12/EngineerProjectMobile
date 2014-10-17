package pl.edu.agh.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.edu.agh.activities.help.HelpElement;
import pl.edu.agh.tools.RenderingTools;

/**
 * Created by Sławek on 2014-10-15.
 */
public class HelpElementDescriptionFragment  extends AbstractDescriptionFragment<HelpElement> {

    private HelpElement displayedHelpElement;

    public static HelpElementDescriptionFragment newInstance(HelpElement helpElement, int index) {
        HelpElementDescriptionFragment fragment = new HelpElementDescriptionFragment();
        fragment.setInitialArguments(index, helpElement);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        displayedHelpElement = (HelpElement) getArguments().getSerializable(KEY_ITEM);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return displayedHelpElement.getLayoutId();
    }

    @Override
    protected void showDetails() {
    }
}