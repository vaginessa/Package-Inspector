package pl.itto.packageinspector.pkgmanager.data;

import java.util.List;

import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;

/**
 * Created by PL_itto on 7/1/2017.
 */

public interface IPackageManagerDataSource {
    void loadApps(IPackageManagerContract.ILoadAppsCallback callback);

    List<AppItem> getListApps();


}
