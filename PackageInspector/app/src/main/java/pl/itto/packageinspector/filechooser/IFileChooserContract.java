package pl.itto.packageinspector.filechooser;

import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;
import pl.itto.packageinspector.filechooser.model.FolderItem;

/**
 * Created by PL_itto on 10/28/2017.
 */

public interface IFileChooserContract {
    interface IFileChooserView extends IBaseView<IFileChooserPresenter> {
        void firstInitData();

        void goToParent();

        void enterDirectory(FolderItem item);

        void selectDirectory(String path);

        void onBackPress();
    }

    interface IFileChooserPresenter extends IBasePresenter {
        void saveApkDir(String path);
    }
}
