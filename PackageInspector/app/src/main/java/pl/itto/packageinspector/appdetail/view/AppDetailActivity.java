package pl.itto.packageinspector.appdetail.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.itto.packageinspector.R;
import pl.itto.packageinspector.appdetail.IAppDetailContract;
import pl.itto.packageinspector.appdetail.presenter.AppDetailPresenter;
import pl.itto.packageinspector.base.BaseFragmentActivity;
import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.utils.AppConstants;

import static pl.itto.packageinspector.utils.AppConstants.AppDetail.EXTRA_KEY;

public class AppDetailActivity extends BaseFragmentActivity implements IAppDetailContract.IAppDetailView {
    private static final String TAG = "PL_itto.AppDetailActivity";
    @BindView(R.id.multiple_actions)
    FloatingActionsMenu mFabMenu;
    String mPackageName = null;
    String mPath = null, mAppName = null;
    IAppDetailContract.IAppDetailPresenter mPresenter;

    @Override
    public Fragment createFragment() {
        AppDetailFragment fragment = AppDetailFragment.newInstance(getIntent().getStringExtra(EXTRA_KEY));
        return fragment;
    }

    @Override
    public int getResId() {
        return R.layout.activity_app_detail;
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setPresenter(new AppDetailPresenter(this, getBaseContext()));
        mPackageName = getIntent().getStringExtra(EXTRA_KEY);
        setupActionBar();
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(IAppDetailContract.IAppDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @OnClick({R.id.action_launch_app, R.id.action_uninstall, R.id.action_extract_apk, R.id.action_open_intent})
    void onClick(View v) {
        mFabMenu.collapse();
        switch (v.getId()) {
            case R.id.action_launch_app:
                if (mPackageName != null)
                    mPresenter.launchApp(mPackageName);
                break;
            case R.id.action_uninstall:
                if (mPackageName != null)
                    uninstallApp(mPackageName);
                break;
            case R.id.action_extract_apk:
                if (mPath != null && mAppName != null) {
                    if (isGrantPermisisons())
                        mPresenter.extractApk(mPath, mAppName);
                    else {
                        requestPermissions(new IActionCallback() {
                            @Override
                            public void onSuccess(Object result) {
                                mPresenter.extractApk(mPath, mAppName);
                            }

                            @Override
                            public void onFailed(@Nullable Object error) {
                                showMessage(R.string.permission_grant_failed);
                            }
                        });
                        break;

                    }

                }
                break;
            case R.id.action_open_intent:
                if (mPackageName != null)
                    openDetailIntent(mPackageName);
                break;
        }
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        Snackbar.make(findViewById(android.R.id.content), getString(resId), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId, String value) {
        Snackbar.make(findViewById(android.R.id.content), getString(resId, value), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void openDetailIntent(String packageName) {
        Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.parse("package:" + packageName));
        startActivity(i);
    }

    @Override
    public void setAppInfo(@NonNull String path, @NonNull String name) {
        mPath = path;
        mAppName = name;
    }

    @Override
    public void uninstallApp(@NonNull String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        startActivityForResult(intent, AppConstants.AppDetail.REQUEST_UNINSTALL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: requestCode:" + requestCode + " resultCode: " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AppConstants.AppDetail.REQUEST_UNINSTALL:
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.AppDetail.RESULT_KEY, AppConstants.AppDetail.RESULT_UNINSTALL);
                    intent.putExtra(AppConstants.AppDetail.RESULT_PACKAGE, mPackageName);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
            }
        } else {
            switch (requestCode) {
                case AppConstants.AppDetail.REQUEST_UNINSTALL:
                    showMessage(R.string.pkg_uninstall_failed);
                    break;
            }
        }
    }
}
