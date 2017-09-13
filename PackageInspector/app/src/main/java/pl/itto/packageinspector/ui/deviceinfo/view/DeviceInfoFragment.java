package pl.itto.packageinspector.ui.deviceinfo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.ui.deviceinfo.data.DeviceManager;
import pl.itto.packageinspector.ui.deviceinfo.data.IDeviceInfoDataSource;
import pl.itto.packageinspector.ui.deviceinfo.data.model.AndroidInfo;
import pl.itto.packageinspector.ui.deviceinfo.data.model.BuildInfo;
import pl.itto.packageinspector.ui.deviceinfo.data.model.DisplayInfo;
import pl.itto.packageinspector.ui.deviceinfo.data.model.PackageInfo;
import pl.itto.packageinspector.ui.deviceinfo.data.model.ProductInfo;
import pl.itto.packageinspector.ui.deviceinfo.data.model.StorageInfo;
import pl.itto.packageinspector.ui.deviceinfo.presenter.DeviceInfoPresenter;
import pl.itto.packageinspector.ui.deviceinfo.presenter.IDeviceInfoPresenter;
import pl.itto.packageinspector.utils.Utils;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class DeviceInfoFragment extends Fragment implements IDeviceInfoView {
    IDeviceInfoPresenter mPresenter;
    IDeviceInfoDataSource mDataSource;
    private boolean mFirstStart = true;
    /* Views */

    // Package Info
    TextView mTxtTotalApk, mTxtSystemApk, mTxtOtherApk;

    // Product Info
    TextView mTxtManufacturer, mTxtProductName, mTxtModelName, mtxtPlatform;

    // Display Info
    TextView mTxtScrenSize, mTxtDensity, mTxtResources;

    // Build Info
    TextView mTxtBuildType, mTxtBuildDate, mTxtBuildVersion;

    // Android Info
    TextView mTxtAndroidVer, mTxtAndroidVerInt;

    // Storage
    TextView mTxtRam, mTxtStorage;

    public static DeviceInfoFragment newInstance() {
        DeviceInfoFragment fragment = new DeviceInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDataSource = new DeviceManager(getContext());
        mPresenter = new DeviceInfoPresenter(mDataSource, this);

    }

    @Override
    public void setPresenter(IDeviceInfoPresenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_info, container, false);
        mTxtTotalApk = (TextView) view.findViewById(R.id.txt_total_apk);
        mTxtSystemApk = (TextView) view.findViewById(R.id.txt_system_apk);
        mTxtOtherApk = (TextView) view.findViewById(R.id.txt_other_apk);

        mTxtManufacturer = (TextView) view.findViewById(R.id.txt_manufacturer);
        mTxtProductName = (TextView) view.findViewById(R.id.txt_product_name);
        mTxtModelName = (TextView) view.findViewById(R.id.txt_model_name);
        mtxtPlatform = (TextView) view.findViewById(R.id.txt_platform);

        mTxtScrenSize = (TextView) view.findViewById(R.id.txt_screen_size);
        mTxtDensity = (TextView) view.findViewById(R.id.txt_density);
        mTxtResources = (TextView) view.findViewById(R.id.txt_usable_res);

        mTxtBuildVersion = (TextView) view.findViewById(R.id.txt_build_version);
        mTxtBuildDate = (TextView) view.findViewById(R.id.txt_build_date);
        mTxtBuildType = (TextView) view.findViewById(R.id.txt_build_type);

        mTxtAndroidVer = (TextView) view.findViewById(R.id.txt_android_ver);
        mTxtAndroidVerInt = (TextView) view.findViewById(R.id.txt_android_sdk);

        mTxtRam = (TextView) view.findViewById(R.id.txt_ram);
        mTxtStorage = (TextView) view.findViewById(R.id.txt_storage);


        if (mFirstStart) {
            mPresenter.start();
            mFirstStart = false;
        }
        return view;
    }

    @Override
    public void updateViews() {
        updateAndroidInfo();
        updateBuildInfo();
        updateDisplayInfo();
        updatePackageInfo();
        updateProductInfo();
        updateStorageInfo();
    }

    private void updateStorageInfo() {
        StorageInfo storageInfo = mDataSource.getStorageInfo();
        mTxtRam.setText(getString(R.string.info_result_content, Utils.convertSize(storageInfo.getTotalRam(), Utils.TYPE_GB)));
        mTxtStorage.setText(getString(R.string.info_result_content, Utils.convertSize(storageInfo.getTotalStorage(), Utils.TYPE_GB)));
    }

    private void updatePackageInfo() {
        PackageInfo packageInfo = mDataSource.getPackageInfo();
        mTxtTotalApk.setText(getString(R.string.info_result_content, String.valueOf(packageInfo.getTotalApk())));
        mTxtSystemApk.setText(getString(R.string.info_result_content, String.valueOf(packageInfo.getSystemApk())));
        mTxtOtherApk.setText(getString(R.string.info_result_content, String.valueOf(packageInfo.getNoneSystemApk())));

    }

    private void updateProductInfo() {
        ProductInfo productInfo = mDataSource.getProductInfo();
        mTxtManufacturer.setText(getString(R.string.info_result_content, productInfo.getManufacturer()));
        mTxtProductName.setText(getString(R.string.info_result_content, productInfo.getProductName()));
        mTxtModelName.setText(getString(R.string.info_result_content, productInfo.getModelName()));
        mtxtPlatform.setText(getString(R.string.info_result_content, productInfo.getPlatform()));
    }

    private void updateDisplayInfo() {
        DisplayInfo displayInfo = mDataSource.getDisplayInfo();
        mTxtScrenSize.setText(getString(R.string.info_result_content, displayInfo.getResolution()));
        mTxtDensity.setText(getString(R.string.info_result_content, String.valueOf(displayInfo.getDensity())));
    }

    private void updateBuildInfo() {
        BuildInfo buildInfo = mDataSource.getBuildInfo();
        mTxtBuildDate.setText(getString(R.string.info_result_content, buildInfo.getBuildDate()));
//        mTxtBuildVersion.setText(buildInfo.getBuildType());
        mTxtBuildType.setText(getString(R.string.info_result_content, buildInfo.getBuildType()));
    }

    private void updateAndroidInfo() {
        AndroidInfo androidInfo = mDataSource.getAndroidInfo();
        mTxtAndroidVer.setText(getString(R.string.info_result_content, androidInfo.getVersion()));
        mTxtAndroidVerInt.setText(getString(R.string.info_result_content, String.valueOf(androidInfo.getSdkVer())));
    }

}