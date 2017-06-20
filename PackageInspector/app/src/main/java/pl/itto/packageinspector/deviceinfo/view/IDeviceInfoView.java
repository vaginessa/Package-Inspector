package pl.itto.packageinspector.deviceinfo.view;

import pl.itto.packageinspector.base.IBaseView;
import pl.itto.packageinspector.deviceinfo.presenter.IDeviceInfoPresenter;

/**
 * Created by PL_itto on 6/15/2017.
 */

public interface IDeviceInfoView extends IBaseView<IDeviceInfoPresenter> {
    void updateViews();
}
