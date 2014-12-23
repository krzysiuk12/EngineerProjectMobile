package pl.edu.agh.fragments.help;

import android.content.Context;
import android.view.View;
import pl.edu.agh.activities.help.HelpElement;
import pl.edu.agh.fragments.AbstractAdapter;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-10-17.
 */
public class HelpElementAdapter extends AbstractAdapter<HelpElement> {

    public HelpElementAdapter(Context context, ArrayList<HelpElement> helpElements) {
        super(context, R.layout.list_item, helpElements);
    }

    @Override
    protected void setLabels(View view, HelpElement helpElement) {
        RenderingTools.setTextViewText(view, R.id.ListItem_Name, helpElement.getLabel());
    }
}
