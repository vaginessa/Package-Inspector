package pl.itto.packageinspector.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/19/2017.
 */

public class AndroidInfo {
    String mVersion;
    int mSdkVer;

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public int getSdkVer() {
        return mSdkVer;
    }

    public void setSdkVer(int sdkVer) {
        mSdkVer = sdkVer;
    }
}
