package pl.itto.packageinspector.pkgmanager.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.ILoadAppsCallback;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;
import pl.itto.packageinspector.utils.AppConstants;
import pl.itto.packageinspector.utils.Logger;
import pl.itto.packageinspector.utils.Utils;


/**
 * Created by PL_itto on 7/1/2017.
 */

public class PackageManagerDataSource implements IPackageManagerDataSource {
    private static final String TAG = "PL_itto.PackageManagerDataSource";
    private Context mContext;
    private List<AppItem> mAppItemList;
    private SharedPreferences mSharedPreferences;

    public PackageManagerDataSource(Context context) {
        mContext = context;
        mAppItemList = new ArrayList<>();
        mSharedPreferences = mContext.getSharedPreferences(AppConstants.Settings.SETTING_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public void loadApps(ILoadAppsCallback callback) {
        new LoadAppAsync(mContext, callback).execute();

    }

    private class LoadAppAsync extends AsyncTask<Void, Void, Void> {

        ILoadAppsCallback mLoadAppsCallback;
        Context mContext;

        public LoadAppAsync(Context context, ILoadAppsCallback callback) {
            mLoadAppsCallback = callback;
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAppItemList.clear();
            PackageManager packageManager = mContext.getPackageManager();
            List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            for (ApplicationInfo app : list) {
                AppItem item = new AppItem();
                item.setApkPath(app.sourceDir);
                String label = app.loadLabel(packageManager).toString();
                String packageName = app.packageName;
                Drawable icon = app.loadIcon(packageManager);
                if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                    item.setSystemApp(true);
                } else {
                    item.setSystemApp(false);
                }
                item.setAppName(label);
                item.setPkgName(packageName);
                item.setDrawableIcon(icon);
                mAppItemList.add(item);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mLoadAppsCallback.onLoaded();
        }
    }

    @Override
    public List<AppItem> getListApps() {
        return mAppItemList;
    }

    @Override
    public void extractApk(String path, String name, IActionApkCallback callback) {
        new ExtractApk(path,name,callback).execute();
    }

    @Override
    public String getSaveApkPath() {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(AppConstants.Settings.SETTING_APK_PATH_KEY, Environment.getExternalStorageDirectory()+"/PackageInspector/Apk");
        }
        return null;
    }

    @Override
    public boolean uninstallApp(String packageName) {
        return false;
    }


    class ExtractApk extends AsyncTask<Void, Void, Boolean> {
        String srcPath, name;
        IActionApkCallback callback;

        ExtractApk(String apkPath, String appNam, IActionApkCallback callback) {
            this.srcPath = apkPath;
            this.name = appNam;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String desDirPath = getSaveApkPath();
            if (desDirPath == null) return false;
            return Utils.copyFile(srcPath, desDirPath, name);
        }

        @Override
        protected void onPostExecute(Boolean done) {
            super.onPostExecute(done);
            if (done) {
                callback.onSuccess();
            } else {
                callback.onError();
            }
        }
    }
}
