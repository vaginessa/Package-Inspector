package pl.itto.packageinspector.ui.pkgmanager.presenter;


import pl.itto.packageinspector.MainActivity;
import pl.itto.packageinspector.ui.base.IBasePresenter;
import pl.itto.packageinspector.ui.base.IBaseView;
import pl.itto.packageinspector.ui.pkgmanager.data.IPackageManagerDataSource;

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

        void filter(String name);

        void clearFilter();


    }

    interface ILoadAppsCallback {
        void onLoaded();
    }

    interface ISearchAppsCallback {
        void search(String name);

        void clearFilter();
    }


    //    for List App
    interface IListAppView extends IBaseView<IListAppPresenter> {
        void showMessage(String msg);

        void extractapk(String path, String name);

        void launchApp(String pkgName);

        MainActivity getMainActivity();
    }

    interface IListAppPresenter extends IBasePresenter {
        void showAppDetail();

        void setDataSource(IPackageManagerDataSource dataSource);

        void extractApk(String path, String name);

        void launchApp(String pkgName);

        void uninstallApp(String pkg);

        void setView(IListAppView view);
    }


}
