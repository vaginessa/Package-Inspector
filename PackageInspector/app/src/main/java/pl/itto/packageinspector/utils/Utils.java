package pl.itto.packageinspector.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.List;

import pl.itto.packageinspector.appdetail.IAppDetailContract;
import pl.itto.packageinspector.appdetail.model.AppInfo;
import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.filechooser.model.FolderItem;

/**
 * Created by PL_itto on 6/27/2017.
 */

public class Utils {
    public static final String TAG = "PL_itto.Utils";
    public static final int TYPE_GB = 4, TYPE_MB = 3, TYPE_KB = 2, TYPE_BYTE = 1;

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
     *
     * @param context     Context
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


    public static void getAppInfo(String pkg, Context context, IAppDetailContract.ActionCallback callback) {
        new LoadAppDetail(context, callback).execute(pkg);
    }

    static class LoadAppDetail extends AsyncTask<String, Void, AppInfo> {
        Context mContext;
        IAppDetailContract.ActionCallback mCallback;

        public LoadAppDetail(Context context, IAppDetailContract.ActionCallback callback) {
            mContext = context;
            mCallback = callback;
        }

        @Override
        protected AppInfo doInBackground(String... params) {
            AppInfo info = null;
            PackageManager pm = mContext.getPackageManager();
            try {
                info = new AppInfo();
                PackageInfo packageInfo = pm.getPackageInfo(params[0], PackageManager.GET_META_DATA | PackageManager.GET_PERMISSIONS);
                info.setVersionCode(String.valueOf(packageInfo.versionCode));
                info.setVersionName(packageInfo.versionName);
                String[] permissions = packageInfo.requestedPermissions;
                if (permissions != null && permissions.length > 0) {
                    info.setPermissionInfos(permissions);
                }
                info.setIcon(packageInfo.applicationInfo.loadIcon(pm));
                info.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                info.setPackageName(params[0]);
                info.setApkPath(packageInfo.applicationInfo.sourceDir);
                info.setInstalledDate(packageInfo.firstInstallTime);
                info.setModifiedDate(packageInfo.lastUpdateTime);
                File file = new File(packageInfo.applicationInfo.publicSourceDir);
                if (file.exists()) {
                    info.setAppSize(file.length());
                }

            } catch (PackageManager.NameNotFoundException e) {
                info = null;
                e.printStackTrace();
            }
            return info;
        }

        @Override
        protected void onPostExecute(AppInfo info) {
            if (info != null) {
                mCallback.onSuccess(info);
            } else {
                mCallback.onFailed();
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);// Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * get App Data size
     *
     * @param context     context
     * @param callback    callback to return resule
     * @param packageName packageName of app
     */
    public static void getAppDataSize(String packageName, Context context, final IActionCallback callback) {
        try {
            Method mGetPackageSizeInfoMethod = context.getPackageManager().getClass().getMethod("getPackageSizeInfo", new Class[]{String.class, IPackageStatsObserver.class});
            mGetPackageSizeInfoMethod.invoke(context.getPackageManager(), new Object[]{packageName, new IPackageStatsObserver.Stub() {
                @Override
                public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                    double size = pStats.cacheSize + pStats.codeSize + pStats.dataSize;
                    callback.onSuccess(formatSize(size, TYPE_BYTE));
                }
            }});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            callback.onFailed(e.toString());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "getAppDataSize: " + e.getCause());
            e.printStackTrace();
            callback.onFailed(e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            callback.onFailed(e.toString());
        }
    }

    public static String getSizeType(int type) {
        Log.i(TAG, "getSizeType: " + type);
        switch (type) {
            case TYPE_BYTE:
                return " B";
            case TYPE_KB:
                return " KB";
            case TYPE_MB:
                return " MB";
            case TYPE_GB:
                return " GB";
        }
        return "";
    }

    public static String formatSize(double size, int unit) {
        Log.i(TAG, "formatSize: " + size + " " + unit);
        if (size < 1024) {
            return new DecimalFormat("##.##").format(size) + getSizeType(unit);
        } else {
            if (unit < TYPE_GB)
                return formatSize(size / 1024, unit + 1);
            else
                return new DecimalFormat("##.##").format(size) + getSizeType(TYPE_GB);
        }
    }

    public static void loadDirectory(File file, @NonNull List<FolderItem> list) {
        list.clear();
        list.add(new FolderItem("...", null, true));
        File[] childs = file.listFiles();
        if (childs != null) {
            for (File child : childs) {
                if (child != null && child.isDirectory()) {
                    list.add(new FolderItem(child.getName(), child, false));
                }
            }
        }
    }


}
