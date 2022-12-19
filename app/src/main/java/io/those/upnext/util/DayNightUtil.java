package io.those.upnext.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class DayNightUtil {
    public static boolean isDayMode(Context context) {
        return !isNightMode(context);
    }

    public static boolean isNightMode(Context context) {
        try {
            int nightModeFlags =
                    context.getResources().getConfiguration().uiMode &
                            Configuration.UI_MODE_NIGHT_MASK;
            switch (nightModeFlags) {
                case Configuration.UI_MODE_NIGHT_YES:
                    return true;

                case Configuration.UI_MODE_NIGHT_NO:
                case Configuration.UI_MODE_NIGHT_UNDEFINED:
                    return false;
            }
        } catch (Exception e) {
            Log.e(DayNightUtil.class.getSimpleName(), "isNightMode failed.", e);
        }

        return false;
    }
}
