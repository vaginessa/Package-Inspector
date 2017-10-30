package pl.itto.packageinspector.appdetail;

import android.support.annotation.NonNull;

import pl.itto.packageinspector.appdetail.model.AppInfo;
import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;

/**
 * Created by PL_itto on 9/21/2017.
 */

public interface IAppDetailContract {
    interface ActionCallback {
        void onSuccess(AppInfo info);

        void onFailed();
    }

    interface IAppDetailView extends IBaseView<IAppDetailPresenter> {
        void showMessage(String msg);

        void showMessage(int resId);

        void showMessage(int resId, String value);

        void openDetailIntent(String packageName);

        void setAppInfo(@NonNull String path, @NonNull String name);

        void uninstallApp(@NonNull String packageName);
    }

    interface IAppDetailPresenter extends IBasePresenter {
        void launchApp(String packageName);

        void extractApk(String path, String name);
    }

    interface IAppDetailFragmentView extends IBaseView<IAppDetailFragmentPresenter> {
        void updateAppInfo(AppInfo info);

        void updateAppDataSize(String result);

        IAppDetailView getParent();
    }

    interface IAppDetailFragmentPresenter extends IBasePresenter {
        void loadAppInfo(String pkgName);
    }
}
