package pl.itto.packageinspector.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import pl.itto.packageinspector.R;

/**
 * Created by PL_itto on 6/15/2017.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
