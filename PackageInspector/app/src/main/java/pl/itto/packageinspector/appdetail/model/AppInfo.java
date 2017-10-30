package pl.itto.packageinspector.appdetail.model;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Date;

import pl.itto.packageinspector.R;

/**
 * Created by PL_itto on 9/21/2017.
 */

public class AppInfo {
    private String mVersionName;
    private String mVersionCode;
    private String[] mPermissionInfos;
    private String mAppName;
    private String mPackageName;
    private Drawable mIcon;
    private long mInstalledDate;
    private long mModifiedDate;
    private String mApkPath;
    private long mAppSize;

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public String getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(String versionCode) {
        mVersionCode = versionCode;
    }

    public String[] getPermissionInfos() {
        return mPermissionInfos;
    }

    public String getPermissions(Context context) {
        StringBuilder builder = new StringBuilder();
        if (mPermissionInfos != null)
            if (mPermissionInfos.length > 0) {
                for (int i = 0; i < mPermissionInfos.length; i++) {
                    builder.append("- "+mPermissionInfos[i]);
                    if (i < mPermissionInfos.length - 1)
                        builder.append("\n");

                }
                return builder.toString();
            }
        return context.getString(R.string.app_no_permission_required);
    }

    public void setPermissionInfos(String[] permissionInfos) {
        mPermissionInfos = permissionInfos;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public long getInstalledDate() {
        return mInstalledDate;
    }

    public void setInstalledDate(long installedDate) {
        mInstalledDate = installedDate;
    }

    public long getModifiedDate() {
        return mModifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        mModifiedDate = modifiedDate;
    }

    public String getApkPath() {
        return mApkPath;
    }

    public void setApkPath(String apkPath) {
        mApkPath = apkPath;
    }

    public String getInstalledDateString() {
        Date date = new Date(mInstalledDate);
        return date.toString();
    }

    public String getModifiedDateString() {
        Date date = new Date(mModifiedDate);
        return date.toString();
    }

    public long getAppSize() {
        return mAppSize;
    }
    public String getAppSizeString() {
        return String.valueOf(mAppSize);
    }

    public void setAppSize(long appSize) {
        mAppSize = appSize;
    }
}
