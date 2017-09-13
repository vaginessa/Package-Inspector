package pl.itto.packageinspector.ui.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class PackageInfo {
    int mTotalApk;
    int mSystemApk;
    int mNoneSystemApk;

    public int getTotalApk() {
        return mTotalApk;
    }

    public void setTotalApk(int totalApk) {
        mTotalApk = totalApk;
    }

    public int getSystemApk() {
        return mSystemApk;
    }

    public void setSystemApk(int systemApk) {
        mSystemApk = systemApk;
    }

    public int getNoneSystemApk() {
        return mNoneSystemApk;
    }

    public void setNoneSystemApk(int noneSystemApk) {
        mNoneSystemApk = noneSystemApk;
    }
}
