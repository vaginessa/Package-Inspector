package pl.itto.packageinspector.filechooser.presenter;

import android.support.annotation.Nullable;

import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.filechooser.IFileChooserContract;
import pl.itto.packageinspector.filechooser.IFileChooserContract.IFileChooserView;

/**
 * Created by PL_itto on 10/28/2017.
 */

public class FileChooserPresenter implements IFileChooserContract.IFileChooserPresenter {
    IFileChooserView mView;
    IDataSource mDataSource;

    FileChooserPresenter(IFileChooserView view, IDataSource dataSource) {
        mDataSource = dataSource;
        mView = view;
    }

    @Override
    public void saveApkDir(String path) {
        mDataSource.saveApkPath(path, new IActionCallback() {
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailed(@Nullable Object error) {
            }
        });
    }

    @Override
    public void start() {

    }
}
