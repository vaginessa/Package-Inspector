package pl.itto.packageinspector.pkgmanager.data;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.ILoadAppsCallback;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;


/**
 * Created by PL_itto on 7/1/2017.
 */

public class PackageManagerDataSource implements IPackageManagerDataSource {
    private static final String TAG = "PL_itto.PackageManagerDataSource";
    private Context mContext;
    private List<AppItem> mAppItemList;

    public PackageManagerDataSource(Context context) {
        mContext = context;
        mAppItemList = new ArrayList<>();
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
}
