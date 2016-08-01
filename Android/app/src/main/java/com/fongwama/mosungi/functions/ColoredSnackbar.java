package com.fongwama.mosungi.functions;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.fongwama.mosungi.R;

/**
 * Created by Karl on 19/07/2016.
 */
public class ColoredSnackbar {
    private static final int red    = 0xfff44336;
    private static final int green  = 0xff4caf50;
    private static final int blue   = 0xff2195f3;
    private static final int orange = 0xffffc107;
    private static final int white  = R.color.icons;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int colorId, Context context) {
        View snackBarView = getSnackBarLayout(snackbar);
        if (snackBarView != null) {
            //snackBarView.setBackgroundColor(colorId);
            snackbar.setActionTextColor(blue);
            //snackbar.setActionTextColor(context.getResources().getColor(R.color.icons));
        }

        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar, Context context) {
        return colorSnackBar(snackbar, blue, context);
    }

    public static Snackbar warning(Snackbar snackbar, Context context) {
        return colorSnackBar(snackbar, orange, context);
    }

    public static Snackbar alert(Snackbar snackbar, Context context) {
        return colorSnackBar(snackbar, red, context);
    }

    public static Snackbar confirm(Snackbar snackbar, Context context) {
        return colorSnackBar(snackbar, green, context);
    }
}
