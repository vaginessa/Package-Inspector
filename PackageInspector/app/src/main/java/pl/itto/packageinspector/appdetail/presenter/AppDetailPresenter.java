package pl.itto.packageinspector.appdetail.presenter;

import android.content.Context;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.appdetail.IAppDetailContract;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.data.DataManager;

/**
 * Created by PL_itto on 10/20/2017.
 */

public class AppDetailPresenter implements IAppDetailContract.IAppDetailPresenter {
    private Context mContext;
    private IAppDetailContract.IAppDetailView mView;
    IDataSource mDataSource;

    @Override
    public void start() {

    }

    public AppDetailPresenter(IAppDetailContract.IAppDetailView view, Context context) {
        mView = view;
        mContext = context;
        mDataSource = DataManager.getInstance(context);
    }

    @Override
    public void launchApp(String packageName) {
        mDataSource.launchApp(packageName);
    }

    @Override
    public void extractApk(final String path, final String name) {
        mDataSource.extractApk(path, name, new IDataSource.IActionApkCallback() {
            @Override
            public void onSuccess() {
                mView.showMessage(R.string.pkg_extract_success1, mDataSource.getSaveApkPath() + "/" + name + ".apk");
            }

            @Override
            public void onError() {
                mView.showMessage(R.string.pkg_extract_failed);
            }
        });
    }
}
