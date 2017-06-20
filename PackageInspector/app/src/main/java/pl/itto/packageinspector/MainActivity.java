package pl.itto.packageinspector;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import pl.itto.packageinspector.base.BaseFragmentActivity;
import pl.itto.packageinspector.deviceinfo.data.DeviceManager;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public Fragment createFragment() {
        MainFragment fragment=MainFragment.newInstance();

        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceManager.getInfo();
    }
}
