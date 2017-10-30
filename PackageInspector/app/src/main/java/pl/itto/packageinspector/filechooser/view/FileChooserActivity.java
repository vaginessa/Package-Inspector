package pl.itto.packageinspector.filechooser.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;

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
}
