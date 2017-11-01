package pl.itto.packageinspector.base;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.appdetail.IAppDetailContract;
import pl.itto.packageinspector.utils.AppConstants;

import static pl.itto.packageinspector.utils.AppConstants.PERMISSION_REQUEST_CODE;

/**
 * Created by PL_itto on 6/15/2017.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();

    private FragmentManager mFragmentManager;

    public int getResId() {
        return R.layout.activity_main;
    }

    IActionCallback mPermissionCallback = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    public void showMessage(int resId) {
        Snackbar.make(findViewById(android.R.id.content), getString(resId), Snackbar.LENGTH_SHORT).show();
    }

    public boolean isGrantPermisisons() {
        boolean ok = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : AppConstants.PERMISSIONS_LIST)
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                }
        }
        return ok;
    }

    public void requestPermissions(@NonNull IActionCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionCallback = callback;
            requestPermissions(AppConstants.PERMISSIONS_LIST, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                boolean ok = true;
                for (int result : grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    if (mPermissionCallback != null)
                        mPermissionCallback.onSuccess(null);
                } else {
                    if (mPermissionCallback != null)
                        mPermissionCallback.onFailed(null);
                }
                mPermissionCallback = null;
                break;
        }

    }

    public void openAppInStore() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }
}
