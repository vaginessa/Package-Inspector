package pl.itto.packageinspector.deviceinfo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.deviceinfo.data.IDeviceInfoDataSource;
import pl.itto.packageinspector.deviceinfo.presenter.DeviceInfoPresenter;
import pl.itto.packageinspector.deviceinfo.presenter.IDeviceInfoPresenter;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class DeviceInfoFragment extends Fragment implements IDeviceInfoView {
    IDeviceInfoPresenter mPresenter;
    IDeviceInfoDataSource mDataSource;

    public static DeviceInfoFragment newInstance() {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        fragment.mPresenter = new DeviceInfoPresenter(fragment.mDataSource, fragment);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void setPresenter(IDeviceInfoPresenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_info, container, false);
        return view;
    }

    @Override
    public void updateViews() {

    }
}
