package pl.itto.packageinspector.utils;

import android.content.pm.ApplicationInfo;
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

    public static boolean copyFile(String srdFile, String desFolder, String destName) {
        File src = new File(srdFile);
        if (!src.exists()) return false;
        File dst = new File(desFolder, destName);
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir();
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

}
