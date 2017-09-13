package pl.itto.packageinspector.ui.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.itto.packageinspector.R;

public class SettingActivity extends AppCompatActivity implements ISettingContract.ISettingView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    @Override
    public void setPresenter(Object presenter) {

    }


}
