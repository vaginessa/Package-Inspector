package pl.itto.packageinspector.about;

import android.support.v4.app.Fragment;

import pl.itto.packageinspector.base.BaseFragmentActivity;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class AboutActivity extends BaseFragmentActivity {
    @Override
    public Fragment createFragment() {
        return AboutFragment.newInstance();
    }
}
