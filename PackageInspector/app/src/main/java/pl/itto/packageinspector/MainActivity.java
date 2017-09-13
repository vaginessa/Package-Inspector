package pl.itto.packageinspector;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import pl.itto.packageinspector.ui.base.BaseFragmentActivity;
import pl.itto.packageinspector.ui.deviceinfo.data.DeviceManager;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceManager.getInfo();
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

}
