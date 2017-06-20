package pl.itto.packageinspector.deviceinfo.data;

/**
 * Created by PL_itto on 6/15/2017.
 */

public interface IDeviceInfoDataSource {
    void loadDeviceInfo(LoadInfoCallback callback);
    void loadPackageInfo();
    void loadDisplayInfo();
    void loadProductInfo();
    void loadBuildInfo();
    void loadAndroidInfo();
    void loadStorageInfo();
    public interface LoadInfoCallback {
        void onLoaded();
    }
}
