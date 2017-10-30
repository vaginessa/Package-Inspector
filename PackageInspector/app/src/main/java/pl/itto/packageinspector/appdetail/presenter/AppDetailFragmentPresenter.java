package pl.itto.packageinspector.appdetail.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import pl.itto.packageinspector.appdetail.model.AppInfo;
import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.utils.Utils;

import static pl.itto.packageinspector.appdetail.IAppDetailContract.*;

/**
 * Created by PL_itto on 9/21/2017.
 */

public class AppDetailFragmentPresenter implements IAppDetailFragmentPresenter {
    private static final String TAG = "PL_itto.AppDetailFragmentPresenter";
    IAppDetailFragmentView mView;
    Context mContext;

    public AppDetailFragmentPresenter(IAppDetailFragmentView view, Context context) {
        mView = view;
        mContext = context;
    }


    @Override
    public void start() {

    }

    @Override
    public void loadAppInfo(String pkgName) {
        Utils.getAppInfo(pkgName, mContext, new ActionCallback() {
            @Override
            public void onSuccess(AppInfo info) {
                mView.updateAppInfo(info);
            }

            @Override
            public void onFailed() {

            }
        });

        Utils.getAppDataSize(pkgName, mContext, new IActionCallback() {
            @Override
            public void onSuccess(Object result) {
                mView.updateAppDataSize((String) result);
            }

            @Override
            public void onFailed(@Nullable Object error) {
                Log.e(TAG, "onGetAppDataSizeFailed: ");
                if (error != null) {
                    Log.e(TAG, error.toString());
                }
            }
        });
    }
}
