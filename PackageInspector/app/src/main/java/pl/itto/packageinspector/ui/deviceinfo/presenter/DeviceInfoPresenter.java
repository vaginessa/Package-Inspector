package pl.itto.packageinspector.ui.deviceinfo.presenter;

import pl.itto.packageinspector.ui.deviceinfo.data.IDeviceInfoDataSource;
import pl.itto.packageinspector.ui.deviceinfo.view.IDeviceInfoView;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class DeviceInfoPresenter implements IDeviceInfoPresenter {
    IDeviceInfoDataSource mDataSource;
    IDeviceInfoView mInfoView;

    public DeviceInfoPresenter(IDeviceInfoDataSource dataSource, IDeviceInfoView infoView) {
        mInfoView = infoView;
        mDataSource = dataSource;
        mInfoView.setPresenter(this);
    }

    @Override
    public void start() {
        mDataSource.loadDeviceInfo(new IDeviceInfoDataSource.LoadInfoCallback() {
            @Override
            public void onLoaded() {
                mInfoView.updateViews();
            }
        });
    }
}
