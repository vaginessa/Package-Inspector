package pl.itto.packageinspector.deviceinfo.data.model;

/**
 * Created by PL_itto on 6/19/2017.
 */

public class StorageInfo {
    long mTotalRam;
    long mTotalStorage;

    public long getTotalRam() {
        return mTotalRam;
    }

    public void setTotalRam(long totalRam) {
        mTotalRam = totalRam;
    }

    public long getTotalStorage() {
        return mTotalStorage;
    }

    public void setTotalStorage(long totalStorage) {
        mTotalStorage = totalStorage;
    }
}
