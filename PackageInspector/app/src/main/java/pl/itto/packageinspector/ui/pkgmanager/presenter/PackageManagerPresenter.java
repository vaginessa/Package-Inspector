package pl.itto.packageinspector.ui.pkgmanager.presenter;

import android.content.Context;
import android.util.Log;

import pl.itto.packageinspector.ui.pkgmanager.data.IPackageManagerDataSource;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class PackageManagerPresenter implements IPackageManagerContract.IPackageManagerPresenter {
    private static final String TAG = "PL_itto.PackageManagerPresenter";
    IPackageManagerDataSource mDataSource;
    IPackageManagerContract.IPackageManagerView mView;
    Context mContext;

    public PackageManagerPresenter(Context context) {
        mContext = context;
    }

    @Override
    public void start() {
        mView.showLoadAppProgress();
        loadApps(new IPackageManagerContract.ILoadAppsCallback() {
            @Override
            public void onLoaded() {
                Log.i(TAG,"onLoaded");
                mView.setupAdapter();
                mView.hideLoadAppProgress();
            }
        });
    }

    @Override
    public void loadApps(IPackageManagerContract.ILoadAppsCallback callback) {
        mDataSource.loadApps(callback);
    }

    @Override
    public void setDataSource(IPackageManagerDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public void setView(IPackageManagerContract.IPackageManagerView view) {
        mView = view;
    }
}
