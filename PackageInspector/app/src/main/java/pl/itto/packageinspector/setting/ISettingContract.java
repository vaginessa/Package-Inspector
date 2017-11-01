package pl.itto.packageinspector.setting;

import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;

/**
 * Created by PL_itto on 10/26/2017.
 */

public interface ISettingContract {
    interface ISettingView extends IBaseView<ISettingPresenter> {
        void openDirectorySelector();

        void onChangeApkPathSuccess(String newPath);

        void openAbout();
    }

    interface ISettingPresenter extends IBasePresenter {
        void saveApkPath(String path);
    }
}
