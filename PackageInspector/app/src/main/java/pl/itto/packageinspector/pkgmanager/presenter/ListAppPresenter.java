package pl.itto.packageinspector.pkgmanager.presenter;

import android.content.Context;

import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;

/**
 * Created by PL_itto on 7/1/2017.
 */

public class ListAppPresenter implements IPackageManagerContract.IListAppPresenter {
    IPackageManagerDataSource mDataSource;
    Context mContext;

    public ListAppPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void showAppDetail() {

    }

    @Override
    public void setDataSource(IPackageManagerDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void extractApk(int pos) {

    }

    @Override
    public void launchApp(int pos) {

    }

    @Override
    public void uninstallApp(int pos) {

    }

    @Override
    public void start() {

    }
}
