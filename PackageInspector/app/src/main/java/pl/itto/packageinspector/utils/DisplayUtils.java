package pl.itto.packageinspector.utils;

import pl.itto.packageinspector.MainApp;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class DisplayUtils {
    public static float density = 1;

    static {
        density = MainApp.mApplicationContex.getResources().getDisplayMetrics().density;
    }
    public static int dp(float value){
        return (int) Math.ceil(density*value);
    }
}
