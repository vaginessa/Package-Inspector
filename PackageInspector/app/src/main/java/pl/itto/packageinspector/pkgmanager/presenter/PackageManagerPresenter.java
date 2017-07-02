package pl.itto.packageinspector.pkgmanager.presenter;

import android.content.Context;

import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.ILoadAppsCallback;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.IPackageManagerView;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class PackageManagerPresenter implements IPackageManagerContract.IPackageManagerPresenter {
    IPackageManagerDataSource mDataSource;
    IPackageManagerView mView;

    public PackageManagerPresenter(Context context) {

    }

    @Override
    public void start() {

    }

    @Override
    public void loadApps(ILoadAppsCallback callback) {

    }

    @Override
    public void setDataSource(IPackageManagerDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void setView(IPackageManagerView view) {
        mView = view;
    }
}
