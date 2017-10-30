package pl.itto.packageinspector.pkgmanager.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class AppItem {
    String mPkgName;
    String mAppName;
    String mVersionName;
    int mVersionCode;
    Bitmap mIcon;
    Drawable mDrawableIcon;
    String[] permissions;

    private String mApkPath;

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public Drawable getDrawableIcon() {
        return mDrawableIcon;
    }

    public void setDrawableIcon(Drawable drawableIcon) {
        mDrawableIcon = drawableIcon;
    }

    boolean mSystemApp;

    public String getPkgName() {
        return mPkgName;
    }

    public void setPkgName(String pkgName) {
        mPkgName = pkgName;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public int getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }

    public Bitmap getIcon() {
        return mIcon;
    }

    public void setIcon(Bitmap icon) {
        mIcon = icon;
    }

    public boolean isSystemApp() {
        return mSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        mSystemApp = systemApp;
    }

    public String getApkPath() {
        return mApkPath;
    }

    public void setApkPath(String apkPath) {
        mApkPath = apkPath;
    }
}
