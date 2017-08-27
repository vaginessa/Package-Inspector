package pl.itto.packageinspector.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
        if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Copy a file from Path to a folder with name
     *
     * @param srdFile   Source File's Path
     * @param desFolder Destination Folder
     * @param destName  Destination Apk file name
     * @return true if Copying succeed false ìf not
     */
    public static boolean copyFile(String srdFile, String desFolder, String destName) {
        Log.i(TAG, "copyFile: " + srdFile + " desFolder: " + desFolder);
        File src = new File(srdFile);
        if (!src.exists()) return false;
        File dst = new File(desFolder, destName + ".apk");
        if (!dst.getParentFile().exists()) {
            boolean result = dst.getParentFile().mkdirs();
            Log.i(TAG, "make dest dir: " + dst.getParentFile().getAbsolutePath() + "   " + result);
            if (!result) return false;
        }

        if (!dst.exists())
            try {
                dst.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(src).getChannel();
            destination = new FileOutputStream(dst).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (source != null)
                    source.close();
                if (destination != null)
                    destination.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;

        }


    }


    /**
     * Launch an app from package Name
     * @param context Context
     * @param packageName package Name
     * @return true if Launch app successfully, false if not
     */
    public static boolean launchApp(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            Intent launchIntent = manager.getLaunchIntentForPackage(packageName);
            if (launchIntent == null) return false;
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        } catch (Exception e) {
            Log.e(TAG, "error on Launching App: " + e.toString());
            return false;
        }
        return true;
    }
}
