package pl.itto.packageinspector.pkgmanager;


import pl.itto.packageinspector.IMainContract;
import pl.itto.packageinspector.MainActivity;
import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;
import pl.itto.packageinspector.data.IDataSource;

/**
 * Created by PL_itto on 6/30/2017.
 */

public interface IPackageManagerContract {
    interface IPackageManagerPresenter extends IBasePresenter {
        void loadApps(ILoadAppsCallback callback);

        void setDataSource(IDataSource dataSource);

        void setView(IPackageManagerView view);
    }

    interface IPackageManagerView extends IBaseView<IPackageManagerPresenter> {
        void notifyDataChanged();

        void showLoadAppProgress();

        void hideLoadAppProgress();

        void setupAdapter();

        void filter(String name);

        void clearFilter();

        IMainContract.IMainView getParentView();


    }

    interface ILoadAppsCallback {
        void onLoaded();
    }



    //    for List App
    interface IListAppView extends IBaseView<IListAppPresenter> {
        void filter(String name);

        void clearFilter();

        void showMessage(String msg);

        void extractapk(String path, String name);

        void launchApp(String pkgName);

        MainActivity getMainActivity();

        void uninstallApp(String packageName);

        void showAppDetail(String packageName);

    }

    interface IListAppPresenter extends IBasePresenter {

        void showAppDetail(String packageName);

        void setDataSource(IDataSource dataSource);

        void extractApk(String path, String name);

        void launchApp(String pkgName);


        void setView(IListAppView view);
    }


}
