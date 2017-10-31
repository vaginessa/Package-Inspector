package pl.itto.packageinspector.filechooser.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.BaseFragmentActivity;

public class FileChooserActivity extends BaseFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new DirectoryFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DirectoryFragment fragment = (DirectoryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            fragment.onBackPress();
            return;
        }
        super.onBackPressed();
    }
}
