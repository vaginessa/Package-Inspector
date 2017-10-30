package pl.itto.packageinspector.data;

import android.support.annotation.NonNull;

import java.util.List;

import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.model.AppItem;

/**
 * Created by PL_itto on 7/1/2017.
 */

public interface IDataSource {

    interface IActionApkCallback {
        void onSuccess();

        void onError();
    }

    void loadApps(IPackageManagerContract.ILoadAppsCallback callback);

    List<AppItem> getListApps();

    void extractApk(String path, String name, IActionApkCallback callback);

    boolean launchApp(String pkgName);

    String getSaveApkPath();

    void removeApp(String pkg);

    void saveApkPath(@NonNull String path, IActionCallback callback);

}
