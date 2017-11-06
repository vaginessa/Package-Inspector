package pl.itto.packageinspector.deviceinfo.data;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Date;
import java.util.List;

import pl.itto.packageinspector.deviceinfo.data.model.AndroidInfo;
import pl.itto.packageinspector.deviceinfo.data.model.BuildInfo;
import pl.itto.packageinspector.deviceinfo.data.model.DisplayInfo;
import pl.itto.packageinspector.deviceinfo.data.model.PackageInfo;
import pl.itto.packageinspector.deviceinfo.data.model.ProductInfo;
import pl.itto.packageinspector.deviceinfo.data.model.StorageInfo;
import pl.itto.packageinspector.utils.DisplayUtils;

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
    private Context mContext;

    public static void getInfo() {
//        Log.i(TAG, "manufacturer: " + Build.MANUFACTURER);
//        Log.i(TAG, "model: " + Build.MODEL);
//        Log.i(TAG, "device: " + Build.DEVICE);
//        Log.i(TAG, "product: " + Build.PRODUCT);
//        Log.i(TAG, "brand: " + Build.BRAND);
//        Log.i(TAG, "bootloader: " + Build.BOOTLOADER);
//        Log.i(TAG, "type: " + Build.TYPE);
//        Log.i(TAG, "codename: " + Build.VERSION.CODENAME);
//        Log.i(TAG, "fingerPrint: " + Build.FINGERPRINT);
//        Log.i(TAG, "hardware: " + Build.HARDWARE);
//        Log.i(TAG, "release: " + Build.VERSION.RELEASE);
//        Log.i(TAG, "baseOS: " + Build.VERSION.BASE_OS);
//        Date date = new Date(Build.TIME);
//        Log.i(TAG, "date: " + date.toString());
//        Log.i(TAG, "id: " + Build.ID);
//        Log.i(TAG, "idString: " + Build.DISPLAY);
    }

    public DeviceManager(Context context) {
        mContext = context;
        mDisplayInfo = new DisplayInfo();
        mPackageInfo = new PackageInfo();
        mStorageInfo = new StorageInfo();
    }

    @Override
    public void loadDeviceInfo(LoadInfoCallback callback) {
        new LoadInfoAsync(callback).execute();
    }

    @Override
    public void loadPackageInfo() {
        PackageManager pm = mContext.getPackageManager();
        List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        int countSystem = 0, countOther = 0;
        for (ApplicationInfo packageInfo : list) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                countSystem++;
            } else {
                countOther++;
            }
        }
        mPackageInfo.setTotalApk(countOther + countSystem);
        mPackageInfo.setSystemApk(countSystem);
        mPackageInfo.setNoneSystemApk(countOther);
    }

    @Override
    public void loadDisplayInfo() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        int densityDpi = displayMetrics.densityDpi;
        String resolution = displayMetrics.widthPixels + " x " + displayMetrics.heightPixels;
        String resources = "sw" + displayMetrics.densityDpi + "dp" + "-" + DisplayUtils.getRangeScreen(displayMetrics.densityDpi);
        mDisplayInfo.setRes(resources);
        mDisplayInfo.setResolution(resolution);
        mDisplayInfo.setDensity(String.valueOf(densityDpi));
        mDisplayInfo.setQuality(density);
        Log.i(TAG, "density: " + density + " " + densityDpi);
    }

    @Override
    public void loadProductInfo() {
        mProductInfo = new ProductInfo();
        mProductInfo.setManufacturer(Build.MANUFACTURER);
        mProductInfo.setProductName(Build.PRODUCT);
        mProductInfo.setModelName(Build.MODEL);
        mProductInfo.setPlatform(Build.HARDWARE);
    }

    @Override
    public void loadBuildInfo() {
        mBuildInfo = new BuildInfo();
        Date date = new Date(Build.TIME);
        mBuildInfo.setBuildDate(date.toString());
        mBuildInfo.setBuildType(Build.TYPE);
    }

    @Override
    public void loadAndroidInfo() {
        mAndroidInfo = new AndroidInfo();
        mAndroidInfo.setSdkVer(Build.VERSION.SDK_INT);
        mAndroidInfo.setVersion(Build.VERSION.RELEASE);
    }

    @Override
    public void loadStorageInfo() {
        // Ram Info
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMemory = memoryInfo.totalMem;
        mStorageInfo.setTotalRam(totalMemory);

        // Storage Info
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long totalStorage = statFs.getBlockSizeLong() * statFs.getBlockCountLong();
        mStorageInfo.setTotalStorage(totalStorage);
        Log.i(TAG, "storage: " + totalStorage);
    }

    @Override
    public BuildInfo getBuildInfo() {
        return mBuildInfo;
    }

    @Override
    public DisplayInfo getDisplayInfo() {
        return mDisplayInfo;
    }

    @Override
    public PackageInfo getPackageInfo() {
        return mPackageInfo;
    }

    @Override
    public ProductInfo getProductInfo() {
        return mProductInfo;
    }

    @Override
    public AndroidInfo getAndroidInfo() {
        return mAndroidInfo;
    }

    @Override
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
