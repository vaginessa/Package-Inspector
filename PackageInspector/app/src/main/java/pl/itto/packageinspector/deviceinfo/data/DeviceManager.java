package pl.itto.packageinspector.deviceinfo.data;

import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.util.Date;

import pl.itto.packageinspector.deviceinfo.data.model.AndroidInfo;
import pl.itto.packageinspector.deviceinfo.data.model.BuildInfo;
import pl.itto.packageinspector.deviceinfo.data.model.DisplayInfo;
import pl.itto.packageinspector.deviceinfo.data.model.ProductInfo;
import pl.itto.packageinspector.deviceinfo.data.model.StorageInfo;

/**
 * Created by PL_itto on 6/15/2017.
 */
public class DeviceManager implements IDeviceInfoDataSource {
    public static final String TAG = "PL_itto.DeviceManager";
    private BuildInfo mBuildInfo;
    private DisplayInfo mDisplayInfo;
    private PackageInfo mPackageInfo;
    private ProductInfo mProductInfo;
    private AndroidInfo mAndroidInfo;
    private StorageInfo mStorageInfo;

    public static void getInfo() {
        Log.i(TAG, "manufacturer: " + Build.MANUFACTURER);
        Log.i(TAG, "model: " + Build.MODEL);
        Log.i(TAG, "device: " + Build.DEVICE);
        Log.i(TAG, "product: " + Build.PRODUCT);
        Log.i(TAG, "brand: " + Build.BRAND);
        Log.i(TAG, "bootloader: " + Build.BOOTLOADER);
        Log.i(TAG, "type: " + Build.TYPE);
        Log.i(TAG, "codename: " + Build.VERSION.CODENAME);
        Log.i(TAG, "fingerPrint: " + Build.FINGERPRINT);
        Log.i(TAG, "hardware: " + Build.HARDWARE);
        Log.i(TAG, "release: " + Build.VERSION.RELEASE);
        Log.i(TAG, "baseOS: " + Build.VERSION.BASE_OS);
        Date date = new Date(Build.TIME);
        Log.i(TAG, "date: " + date.toString());
        Log.i(TAG, "id: " + Build.ID);
        Log.i(TAG, "idString: " + Build.DISPLAY);
    }

    @Override
    public void loadDeviceInfo(LoadInfoCallback callback) {
        new LoadInfoAsync(callback).execute();
    }

    @Override
    public void loadPackageInfo() {

    }

    @Override
    public void loadDisplayInfo() {

    }

    @Override
    public void loadProductInfo() {
        mProductInfo=new ProductInfo();
        mProductInfo.setProductName(Build.PRODUCT);
        mProductInfo.setModelName(Build.MODEL);
        mProductInfo.setPlatform(Build.HARDWARE);
    }

    @Override
    public void loadBuildInfo() {

    }

    @Override
    public void loadAndroidInfo() {

    }

    @Override
    public void loadStorageInfo() {

    }

    public BuildInfo getBuildInfo() {
        return mBuildInfo;
    }

    public DisplayInfo getDisplayInfo() {
        return mDisplayInfo;
    }

    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    public ProductInfo getProductInfo() {
        return mProductInfo;
    }

    public AndroidInfo getAndroidInfo() {
        return mAndroidInfo;
    }

    public StorageInfo getStorageInfo() {
        return mStorageInfo;
    }

    class LoadInfoAsync extends AsyncTask<Void, Void, Void> {
        LoadInfoCallback mCallback;

        public LoadInfoAsync(LoadInfoCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {

            loadDisplayInfo();
            loadBuildInfo();
            loadProductInfo();
            loadAndroidInfo();
            loadStorageInfo();
            loadPackageInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCallback.onLoaded();
        }
    }

    private void setBuildInfo(BuildInfo buildInfo) {
        mBuildInfo = buildInfo;
    }

    private void setDisplayInfo(DisplayInfo displayInfo) {
        mDisplayInfo = displayInfo;
    }

    private void setPackageInfo(PackageInfo packageInfo) {
        mPackageInfo = packageInfo;
    }

    private void setProductInfo(ProductInfo productInfo) {
        mProductInfo = productInfo;
    }

    private void setAndroidInfo(AndroidInfo androidInfo) {
        mAndroidInfo = androidInfo;
    }

    private void setStorageInfo(StorageInfo storageInfo) {
        mStorageInfo = storageInfo;
    }


}
