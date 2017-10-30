package pl.itto.packageinspector.deviceinfo.data;

import pl.itto.packageinspector.deviceinfo.data.model.AndroidInfo;
import pl.itto.packageinspector.deviceinfo.data.model.BuildInfo;
import pl.itto.packageinspector.deviceinfo.data.model.DisplayInfo;
import pl.itto.packageinspector.deviceinfo.data.model.PackageInfo;
import pl.itto.packageinspector.deviceinfo.data.model.ProductInfo;
import pl.itto.packageinspector.deviceinfo.data.model.StorageInfo;

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

    BuildInfo getBuildInfo();

    DisplayInfo getDisplayInfo();

    PackageInfo getPackageInfo();

    ProductInfo getProductInfo();

    StorageInfo getStorageInfo();

    AndroidInfo getAndroidInfo();

    public interface LoadInfoCallback {
        void onLoaded();
    }
}
