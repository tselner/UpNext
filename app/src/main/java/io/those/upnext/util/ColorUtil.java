package io.those.upnext.util;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import io.those.upnext.R;

public class ColorUtil {
    public static int getTextColor(Context context, int baseColor) {
        // Text color
        int colorFont      = ContextCompat.getColor(context, R.color.font_text);
        int colorFontALt   = ContextCompat.getColor(context, R.color.font_text_alt);
        int chosenFontColor = colorFont;

        try {
            if (ColorUtils.calculateContrast(baseColor, colorFont) <= 4.5f) {
                // need different font (https://miromatech.com/android/contrast-ratio/)
                chosenFontColor = colorFontALt;
            }
        } catch (Exception e) {
            // IllegalArgumentException: background can not be translucent: #0
            Log.e(ColorUtil.class.getSimpleName(), "getTextColor failed.", e);
        }

        return chosenFontColor;
    }
}
