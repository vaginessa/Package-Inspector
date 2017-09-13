package pl.itto.packageinspector.ui.deviceinfo.view;

import pl.itto.packageinspector.ui.base.IBaseView;
import pl.itto.packageinspector.ui.deviceinfo.presenter.IDeviceInfoPresenter;

/**
 * Created by PL_itto on 6/15/2017.
 */

public interface IDeviceInfoView extends IBaseView<IDeviceInfoPresenter> {
    void updateViews();
}
