package com.android.graphplay.util;

import android.animation.TimeInterpolator;

/**
 * Created by akashshrivatava on 9/14/18.
 */

public class BackInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float t) {
            float s = 1.70158f;
            float ft = t - 1.0f;
            return (ft * ft * ((s  +1.0f) * ft + s) + 1.0f);
        }

}

