package pl.itto.packageinspector.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pl.itto.packageinspector.R;

/**
 * Created by PL_itto on 8/28/2017.
 */

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
