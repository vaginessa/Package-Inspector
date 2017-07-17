package pl.itto.packageinspector.pkgmanager.presenter;


import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;
import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;

/**
 * Created by PL_itto on 6/30/2017.
 */

public interface IPackageManagerContract {
    interface IPackageManagerPresenter extends IBasePresenter {
        void loadApps(ILoadAppsCallback callback);

        void setDataSource(IPackageManagerDataSource dataSource);

        void setView(IPackageManagerView view);
    }

    interface IPackageManagerView extends IBaseView<IPackageManagerPresenter> {
        void notifyDataChanged();

        void showLoadAppProgress();

        void hideLoadAppProgress();

        void setupAdapter();

    }

    interface ILoadAppsCallback {
        void onLoaded();
    }

    //    for List App
    interface IListAppPresenter extends IBasePresenter {
        void showAppDetail();

        void setDataSource(IPackageManagerDataSource dataSource);

        void extractApk(int pos);

        void launchApp(int pos);

        void uninstallApp(int pos);

    }


}
