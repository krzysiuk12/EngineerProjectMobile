package pl.edu.agh.fragments;

import pl.edu.agh.activities.HelpElementDescriptionActivity;
import pl.edu.agh.activities.help.HelpElement;
import pl.edu.agh.main.R;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-10-15.
 */
public class HelpElementsListFragment extends AbstractListFragment<HelpElement> {

    private int detailsPaneId;

    public HelpElementsListFragment() {
        super();
        detailsPaneId = R.id.HelpActivity_HelpElementDescription; // required
    }

    @Override
    protected AbstractAdapter getAdapterInstance() {

        ArrayList<HelpElement> helpElementsList = new ArrayList<HelpElement>();
//        helpElementsList.add(new HelpElement("TestHelpElement", R.layout.help_element_test_fragment));
        helpElementsList.add(new HelpElement(getString(R.string.HelpElementDescription_MainMenu_Title), R.layout.help_element_main_menu_fragment));

        return new HelpElementAdapter(getActivity(), helpElementsList);
    }

    @Override
    protected Class getClassForDetailsIntent() {
        return HelpElementDescriptionActivity.class;
    }

    @Override
    protected AbstractDescriptionFragment getDetailsFragmentInstance(HelpElement helpElement, int index) {
        return HelpElementDescriptionFragment.newInstance(helpElement, index);
    }

    @Override
    protected int getDetailsPaneId() {
        return detailsPaneId;
    }

}