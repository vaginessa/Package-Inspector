package pl.itto.packageinspector.pkgmanager.presenter;

import android.content.Context;
import android.util.Log;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.IListAppView;

/**
 * Created by PL_itto on 7/1/2017.
 */

public class ListAppPresenter implements IPackageManagerContract.IListAppPresenter {
    private static final String TAG = "PL_itto.ListAppPresenter";
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
//                mView.showExtractSuccess();
                mView.showMessage(mContext.getString(R.string.pkg_extract_success));
            }

            @Override
            public void onError() {
//                mView.showExtractFailed();
                mView.showMessage(mContext.getString(R.string.pkg_extract_failed));
            }
        });
    }


    @Override
    public void launchApp(String pkgName) {
        if (mDataSource.launchApp(pkgName)) {
            Log.i(TAG, "Launch " + pkgName + " successfully!");
        } else {
            Log.e(TAG, "Error in Launching " + pkgName);
        }
    }

    @Override
    public void uninstallApp(String pkg) {

    }

    @Override
    public void setView(IListAppView view) {
        mView = view;
    }

    @Override
    public void start() {

    }
}
