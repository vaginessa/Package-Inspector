package pl.itto.packageinspector.appdetail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.appdetail.presenter.AppDetailFragmentPresenter;
import pl.itto.packageinspector.appdetail.IAppDetailContract;
import pl.itto.packageinspector.appdetail.IAppDetailContract.IAppDetailFragmentPresenter;
import pl.itto.packageinspector.appdetail.model.AppInfo;
import pl.itto.packageinspector.base.BasePreferenceFragent;
import pl.itto.packageinspector.utils.Utils;

import static pl.itto.packageinspector.utils.AppConstants.AppDetail.EXTRA_KEY;

/**
 * Created by PL_itto on 10/20/2017.
 */

public class AppDetailFragment extends BasePreferenceFragent implements IAppDetailContract.IAppDetailFragmentView, Preference.OnPreferenceClickListener {
    private static final String TAG = "PL_itto.AppDetailFragment1";
    private Preference mName, mPackage, mApkSize, mDataSize, mPermission, mInstallDate, mModifyDate;
    private String mPackageName = null;
    private IAppDetailFragmentPresenter mPresenter;

    public static AppDetailFragment newInstance(String pkgName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_KEY, pkgName);
        AppDetailFragment fragment = new AppDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
//        setRetainInstance(true);
        addPreferencesFromResource(R.xml.app_detail_preference);
        mPackageName = getArguments().getString(EXTRA_KEY);
        setPresenter(new AppDetailFragmentPresenter(this, getActivity().getBaseContext()));
        initPreferences();
    }

    private void initPreferences() {
        PreferenceManager preferenceManager = getPreferenceManager();
        mName = preferenceManager.findPreference(getString(R.string.app_detail_title));
        mName.setOnPreferenceClickListener(this);
        mPackage = preferenceManager.findPreference(getString(R.string.app_detail_package));
        mPackage.setOnPreferenceClickListener(this);
        mApkSize = preferenceManager.findPreference(getString(R.string.app_detail_apk_size));
        mApkSize.setOnPreferenceClickListener(this);
        mDataSize = preferenceManager.findPreference(getString(R.string.app_detail_data_size));
        mDataSize.setOnPreferenceClickListener(this);
        mPermission = preferenceManager.findPreference(getString(R.string.app_detail_permissions));
        mPermission.setOnPreferenceClickListener(this);
        mInstallDate = preferenceManager.findPreference(getString(R.string.app_detail_date_installed));
        mInstallDate.setOnPreferenceClickListener(this);
        mModifyDate = preferenceManager.findPreference(getString(R.string.app_detail_date_modify));
        mModifyDate.setOnPreferenceClickListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.i(TAG, "onCreatePreferences: ");
    }

    @Override
    public void setPresenter(IAppDetailFragmentPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPackageName != null) mPresenter.loadAppInfo(mPackageName);
    }

    @Override
    public void updateAppInfo(AppInfo info) {
        mName.setTitle(info.getAppName());
        mName.setSummary(getString(R.string.app_version_name, info.getVersionName()));
        mName.setIcon(info.getIcon());
        mPackage.setSummary(info.getPackageName());
        mApkSize.setSummary(Utils.formatSize(info.getAppSize(), Utils.TYPE_BYTE));
        mInstallDate.setSummary(info.getInstalledDateString());
        mModifyDate.setSummary(info.getModifiedDateString());
        mPermission.setSummary(info.getPermissions(getContext()));
        if (info.getApkPath() != null && info.getAppName() != null) {
            getParent().setAppInfo(info.getApkPath(), info.getAppName());
        }
//        PreferenceManager preferenceManager = getPreferenceManager();
//        preferenceManager.findPreference(getString(R.string.app_deta))
    }

    @Override
    public void updateAppDataSize(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDataSize.setSummary(result);
            }
        });

    }

    @Override
    public IAppDetailContract.IAppDetailView getParent() {
        return (IAppDetailContract.IAppDetailView) getActivity();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

}
