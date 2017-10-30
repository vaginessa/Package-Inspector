package pl.itto.packageinspector.setting.presenter;

import android.support.annotation.Nullable;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.setting.ISettingContract;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class SettingPresenter implements ISettingContract.ISettingPresenter {
    ISettingContract.ISettingView mView;
    IDataSource mDataSource;

    public SettingPresenter(ISettingContract.ISettingView view, IDataSource dataSource) {
        mDataSource = dataSource;
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveApkPath(final String path) {
        mDataSource.saveApkPath(path, new IActionCallback() {
            @Override
            public void onSuccess(Object result) {
                mView.onChangeApkPathSuccess(path);
            }

            @Override
            public void onFailed(@Nullable Object error) {
                mView.showMessage(R.string.save_path_fail);
            }
        });
    }
}
