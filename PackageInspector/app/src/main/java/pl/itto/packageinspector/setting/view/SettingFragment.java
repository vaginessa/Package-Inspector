package pl.itto.packageinspector.setting.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.BaseFragmentActivity;
import pl.itto.packageinspector.data.DataManager;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.filechooser.view.FileChooserActivity;
import pl.itto.packageinspector.setting.ISettingContract;
import pl.itto.packageinspector.setting.ISettingContract.ISettingPresenter;
import pl.itto.packageinspector.setting.presenter.SettingPresenter;
import pl.itto.packageinspector.utils.AppConstants;


/**
 * Created by PL_itto on 10/26/2017.
 */

public class SettingFragment extends PreferenceFragmentCompat implements ISettingContract.ISettingView, Preference.OnPreferenceClickListener {
    private static final String TAG = "PL_itto.SettingFragment";
    Preference mApkPreference;
    ISettingPresenter mPresenter;
    IDataSource mDataSource;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        Log.i(TAG, "onCreatePreferences: " + s);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDataSource = DataManager.getInstance(getContext());
        addPreferencesFromResource(R.xml.setting_preference);
        setPresenter(new SettingPresenter(this, mDataSource));
        initPreferences();
    }

    private void initPreferences() {
        PreferenceManager preferenceManager = getPreferenceManager();
        mApkPreference = preferenceManager.findPreference(getString(R.string.setting_apk_path_key));
        mApkPreference.setOnPreferenceClickListener(this);
        mApkPreference.setSummary(mDataSource.getSaveApkPath());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    @Override
    public void setPresenter(ISettingPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {
    }

    @Override
    public void showMessage(int resId) {
        ((BaseFragmentActivity) getActivity()).showMessage(resId);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case AppConstants.Settings.SETTING_APK_PATH_KEY:
                openDirectorySelector();
                break;

        }
        return false;
    }

    @Override
    public void openDirectorySelector() {
        Intent intent = new Intent(getContext(), FileChooserActivity.class);
        startActivityForResult(intent, AppConstants.Settings.REQUEST_APK_PATH);
    }

    @Override
    public void onChangeApkPathSuccess(String newPath) {
        showMessage(R.string.save_path_success);
        mApkPreference.setSummary(newPath);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: " + requestCode + "___" + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.Settings.REQUEST_APK_PATH:
                    if (data != null) {
                        String apkPath = data.getStringExtra(AppConstants.Settings.EXTRA_APK_PATH);
                        mPresenter.saveApkPath(apkPath);
                    }
                    break;
            }
        }
    }
}
