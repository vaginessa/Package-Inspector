package pl.itto.packageinspector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import pl.itto.packageinspector.base.BaseFragmentActivity;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "PL_itto.MainActivity";

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: " + (savedInstanceState != null));
        super.onCreate(null);
    }
}
