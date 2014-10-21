package pl.edu.agh.fragments;

import android.content.Context;
import android.view.View;
import pl.edu.agh.activities.settings.SettingsIssue;
import pl.edu.agh.main.R;
import pl.edu.agh.tools.RenderingTools;

import java.util.ArrayList;

/**
 * Created by Sławek on 2014-10-21.
 */
public class SettingsIssueAdapter extends AbstractAdapter<SettingsIssue> {

    public SettingsIssueAdapter(Context context, ArrayList<SettingsIssue> settingsIssues) {
        super(context, R.layout.list_item, settingsIssues);
    }

    @Override
    protected void setLabels(View view, SettingsIssue settingsIssue) {
        RenderingTools.setTextViewText(view, R.id.ListItem_Name, settingsIssue.getLabel());
    }
}
