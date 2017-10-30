package pl.itto.packageinspector.deviceinfo.presenter;

import pl.itto.packageinspector.deviceinfo.data.IDeviceInfoDataSource;
import pl.itto.packageinspector.deviceinfo.view.IDeviceInfoView;

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
