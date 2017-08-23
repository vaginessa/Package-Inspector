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
        void showExtractSuccess();

        void showExtractFailed();


    }

    interface IListAppPresenter extends IBasePresenter {
        void showAppDetail();

        void setDataSource(IPackageManagerDataSource dataSource);

        void extractApk(String path, String name);

        void launchApp(int pos);

        void uninstallApp(int pos);

        void setView(IListAppView view);
    }


}
