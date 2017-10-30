package pl.itto.packageinspector.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class DisplayInfo {
    String mResolution;
    String mDensity;
    String mRes;
    float mQuality;

    public String getResolution() {
        return mResolution;
    }

    public void setResolution(String resolution) {
        mResolution = resolution;
    }

    public String getDensity() {
        return mDensity;
    }

    public void setDensity(String density) {
        mDensity = density;
    }

    public String getRes() {
        return mRes;
    }

    public void setRes(String res) {
        mRes = res;
    }

    public float getQuality() {
        return mQuality;
    }

    public void setQuality(float quality) {
        mQuality = quality;
    }
}
