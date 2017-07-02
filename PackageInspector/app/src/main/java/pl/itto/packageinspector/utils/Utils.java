package pl.itto.packageinspector.utils;

import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by PL_itto on 6/27/2017.
 */

public class Utils {
    public static final String TAG = "PL_itto.Utils";
    public static final int TYPE_GB = 0, TYPE_MB = 1, TYPE_KB = 2;

    public static String convertSize(long bytes, int type) {
        Log.i(TAG, "convertSize: " + bytes);
        switch (type) {
            case TYPE_GB:
                return byteToGb(bytes);
            case TYPE_MB:
                return byteToMb(bytes);
            case TYPE_KB:
                return byteToKb(bytes);
        }
        return "0.0 KB";
    }

    public static String byteToGb(long bytes) {
        return formatNumber(((double) bytes) / (1024 * 1024 * 1024)) + " GB";
    }

    public static String byteToMb(long bytes) {
        return formatNumber(((double) bytes) / (1024 * 1024)) + " MB";
    }

    private static String byteToKb(long bytes) {
        return formatNumber(((double) bytes) / 1024) + " KB";
    }

    public static String formatNumber(double input) {
        return String.format("%.2f", input);
    }

    public static boolean isSystemApp(ApplicationInfo app) {
        if ((app.flags & ApplicationInfo.FLAG_SYSTEM)==0) {
            return true;
        }
        return false;
    }

}
