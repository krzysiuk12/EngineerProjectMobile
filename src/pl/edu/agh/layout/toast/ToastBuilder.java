package pl.edu.agh.layout.toast;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Krzysiu on 2014-09-13.
 */
public abstract class ToastBuilder {

    protected DisplayMetrics displayMetrics;
    protected Toast toast;
    protected String message;
    protected Activity activity;

    protected ToastBuilder(Activity activity, String message) {
        this.toast = new Toast(activity);
        this.message = message;
        this.displayMetrics = new DisplayMetrics();
        this.activity = activity;
    }

    protected abstract int getLayoutRootElementId();
    protected abstract int getLayoutResourceId();
    protected abstract int getImageResource();
    protected abstract int getImageViewResourceId();
    protected abstract int getTextViewResourceId();

    protected int getMinimumWidth() {
        return (int)(displayMetrics.widthPixels * 0.8);
    }

    protected int getMinimumHeight() {
        return (int)(displayMetrics.heightPixels * 0.1);
    }

    protected String getMessage() {
        return message;
    }

    protected int getDuration() {
        return Toast.LENGTH_LONG;
    }

    protected int getGravityAlignment() {
        return Gravity.TOP;
    }

    protected int getGravityXOffset() {
        return 0;
    }

    protected int getGravityYOffset() {
        return (int)(displayMetrics.heightPixels * 0.1);
    }

    public final Toast build() {
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        View toastLayout = activity.getLayoutInflater().inflate(getLayoutResourceId(), (ViewGroup)activity.findViewById(getLayoutRootElementId()));
        ((ImageView)toastLayout.findViewById(getImageViewResourceId())).setImageResource(getImageResource());
        ((TextView)toastLayout.findViewById(getTextViewResourceId())).setText(getMessage());
        toastLayout.setMinimumWidth(getMinimumWidth());
        toast.setDuration(getDuration());
        toast.setGravity(getGravityAlignment(), getGravityXOffset(), getGravityYOffset());
        toast.setView(toastLayout);
        return toast;
    }

}
