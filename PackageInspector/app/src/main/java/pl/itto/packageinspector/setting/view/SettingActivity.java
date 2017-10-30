package pl.itto.packageinspector.setting.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.BaseFragmentActivity;

public class SettingActivity extends BaseFragmentActivity {
    private Toolbar mToolbar;

    @Override
    public Fragment createFragment() {
        return new SettingFragment();
    }

    @Override
    public int getResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
