package pl.itto.packageinspector.ui.pkgmanager.data;

import java.util.List;

import pl.itto.packageinspector.ui.pkgmanager.presenter.IPackageManagerContract;
import pl.itto.packageinspector.ui.pkgmanager.data.model.AppItem;

/**
 * Created by PL_itto on 7/1/2017.
 */

public interface IPackageManagerDataSource {

    interface IActionApkCallback {
        void onSuccess();

        void onError();
    }

    void loadApps(IPackageManagerContract.ILoadAppsCallback callback);

    List<AppItem> getListApps();

    void extractApk(String path, String name, IActionApkCallback callback);

    boolean launchApp(String pkgName);

    String getSaveApkPath();

    boolean uninstallApp(String packageName);

}