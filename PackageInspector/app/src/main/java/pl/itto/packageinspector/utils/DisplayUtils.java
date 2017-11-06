package pl.itto.packageinspector.utils;

import android.util.DisplayMetrics;

import pl.itto.packageinspector.MainApp;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class DisplayUtils {
    public static float density = 1;

    static {
        density = MainApp.mApplicationContex.getResources().getDisplayMetrics().density;
    }

    public static int dp(float value) {
        return (int) Math.ceil(density * value);
    }

    public static String getRangeScreen(int dpi) {
        if (dpi <= DisplayMetrics.DENSITY_LOW) {
            return "ldpi";
        } else if (dpi <= DisplayMetrics.DENSITY_MEDIUM) {
            return "mdpi";
        } else if (dpi <= DisplayMetrics.DENSITY_HIGH) {
            return "hdpi";
        } else if (dpi <= DisplayMetrics.DENSITY_XHIGH) {
            return "xhdpi";
        } else if (dpi <= DisplayMetrics.DENSITY_XXHIGH) {
            return "xxhdpi";
        } else if (dpi <= DisplayMetrics.DENSITY_XXXHIGH) {
            return "xxxhdpi";
        } else
            return "xxxhdpi";
    }
}
