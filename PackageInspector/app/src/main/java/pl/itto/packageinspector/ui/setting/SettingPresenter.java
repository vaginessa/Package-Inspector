package pl.itto.packageinspector.ui.setting;

import android.content.Context;

/**
 * Created by PL_itto on 8/28/2017.
 */

public class SettingPresenter implements ISettingContract.ISettingPresenter {
    ISettingContract.ISettingView mISettingView;
    Context mContext;

    public SettingPresenter(ISettingContract.ISettingView ISettingView, Context context) {
        mISettingView = ISettingView;
        mContext = context;
        mISettingView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
