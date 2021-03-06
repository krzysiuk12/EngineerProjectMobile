package pl.edu.agh.fragments.help;

import com.fasterxml.jackson.databind.deser.Deserializers;
import pl.edu.agh.activities.help.BaseHelpElement;
import pl.edu.agh.activities.help.HelpElementDescriptionActivity;
import pl.edu.agh.activities.help.HelpElement;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.fragments.AbstractDescriptionFragment;
import pl.edu.agh.fragments.AbstractListFragment;
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

        for ( BaseHelpElement helpElement : BaseHelpElement.values() ) {
            helpElementsList.add(helpElement.getHelpElement(getResources()));
        }

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