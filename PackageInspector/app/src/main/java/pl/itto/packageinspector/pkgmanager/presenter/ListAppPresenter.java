package pl.itto.packageinspector.pkgmanager.presenter;

import android.content.Context;

import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.IListAppView;

/**
 * Created by PL_itto on 7/1/2017.
 */

public class ListAppPresenter implements IPackageManagerContract.IListAppPresenter {
    IPackageManagerDataSource mDataSource;
    Context mContext;
    private IListAppView mView;

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
    public void extractApk(String path, String name) {
        mDataSource.extractApk(path, name, new IPackageManagerDataSource.IActionApkCallback() {

            @Override
            public void onSuccess() {
                 mView.showExtractSuccess();
            }

            @Override
            public void onError() {
                mView.showExtractFailed();
            }
        });
    }


    @Override
    public void launchApp(int pos) {

    }

    @Override
    public void uninstallApp(int pos) {

    }

    @Override
    public void setView(IListAppView view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
