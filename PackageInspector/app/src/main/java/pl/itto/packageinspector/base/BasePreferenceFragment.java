package pl.itto.packageinspector.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by PL_itto on 11/1/2017.
 */

public class BasePreferenceFragment extends PreferenceFragmentCompat {
    @NonNull
    public BaseFragmentActivity getParentActivity() {
        return (BaseFragmentActivity) getActivity();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

    }
}
