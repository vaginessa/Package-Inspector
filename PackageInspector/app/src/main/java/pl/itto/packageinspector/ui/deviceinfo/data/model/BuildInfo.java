package pl.itto.packageinspector.ui.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class BuildInfo {
    String mBuildType;
    String mBuildDate;
    long mBuildTime;

    public String getBuildType() {
        return mBuildType;
    }

    public void setBuildType(String buildType) {
        mBuildType = buildType;
    }

    public String getBuildDate() {
        return mBuildDate;
    }

    public void setBuildDate(String buildDate) {
        mBuildDate = buildDate;
    }

    public long getBuildTime() {
        return mBuildTime;
    }

    public void setBuildTime(long buildTime) {
        mBuildTime = buildTime;
    }
}
