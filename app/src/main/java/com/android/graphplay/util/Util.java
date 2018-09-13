package com.android.graphplay.util;

import android.content.Context;

import java.util.regex.Pattern;

/**
 * Created by akashshrivatava on 9/12/18.
 */

public class Util {


    private static final String TAG = Util.class.getSimpleName();

    public static int dpToPx(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

}
