package pl.itto.packageinspector;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import pl.itto.packageinspector.about.AboutActivity;
import pl.itto.packageinspector.base.BaseFragment;
import pl.itto.packageinspector.deviceinfo.view.DeviceInfoFragment;
import pl.itto.packageinspector.deviceinfo.view.IDeviceInfoView;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract.IPackageManagerView;
import pl.itto.packageinspector.pkgmanager.view.PackageManagerFragment;
import pl.itto.packageinspector.setting.view.SettingActivity;
import pl.itto.packageinspector.utils.AppConstants;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class MainFragment extends BaseFragment implements IMainContract.IMainView {
    public static final String TAG = "PL_itto.MainFragment";
    public static final int FLAG_DEVICE_INFO = 0;
    public static final int FLAG_PACKAGE_MANAGER = 1;
    public static final int FLAG_ABOUT = 2;
    public static final int FLAG_RATING = 3;
    public static final int FLAG_MORE_APPS = 4;
    public static final int FLAG_SETTING = 5;
    public static final int FLAG_UNKNOWN = -1;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private IMainContract.IMainPresenter mPresenter;
    private boolean mFirstStart = true;
    private MainActivity mActivity;
    private NavigationView mNavigationView;
    private Menu mOptionMenu;
    private IPackageManagerView mPackageManagerView;
    private IDeviceInfoView mDeviceInfoView;
    SearchView searchView;
    private int mCurrentSelectPos = 0;

    public static MainFragment newInstance() {
//        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.mPresenter = new MainPresenter(fragment);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.frag_main, container, false);
        // Setup the toolbar
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mActivity = (MainActivity) getActivity();
        mActivity.setSupportActionBar(mToolbar);
        ActionBar ab = mActivity.getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup naviagation drawer
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.layout_drawer);
//        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        mNavigationView = (NavigationView) view.findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);
        return view;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "onNavigationItemSelected: " + item.getTitle());
                int navId = FLAG_UNKNOWN;
                switch (item.getItemId()) {
                    case R.id.nav_device_info:
                        navId = FLAG_DEVICE_INFO;
                        break;
                    case R.id.nav_pkg_manager:
                        navId = FLAG_PACKAGE_MANAGER;
                        break;
//                    case R.id.nav_about:
//                        navId = FLAG_ABOUT;
//                        Intent i = new Intent(getContext(), AboutActivity.class);
//                        startActivity(i);
//                        break;
                    case R.id.nav_rate:
                        navId = FLAG_RATING;
                        break;
//                    case R.id.nav_more_apps:
//                        navId = FLAG_MORE_APPS;
//                        break;
                    case R.id.nav_settings:
                        navId = FLAG_SETTING;
                        openSettings();
                        break;
                }
                mPresenter.processNavigationSelected(navId);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: " + mFirstStart);
        if (mFirstStart) {
            mFirstStart = false;
            if (mPresenter != null) mPresenter.start();
        } else {
            navigateView(mCurrentSelectPos);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        mOptionMenu = menu;
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) mOptionMenu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mPackageManagerView != null) {
                    if (newText != "") mPackageManagerView.filter(newText);
                    else {
                        mPackageManagerView.clearFilter();
                    }
                }
                return true;
            }

        });
        searchManager.setOnDismissListener(new SearchManager.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPackageManagerView != null) {
                    mPackageManagerView.clearFilter();
                }
            }
        });
        if (!mFirstStart) {
            switchSearchView(mCurrentSelectPos == FLAG_PACKAGE_MANAGER);
        }
        Log.i(TAG, "onCreateOptionsMenu");
    }

    @Override
    public void openSettings() {
        Intent intent = new Intent(getContext(), SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateView(int id) {
        Log.i(TAG, "navigateView: " + id);
        mCurrentSelectPos = id;
        mDrawerLayout.closeDrawers();
        switch (id) {
            case FLAG_DEVICE_INFO:
                mNavigationView.setCheckedItem(R.id.nav_device_info);
                openDeviceInfo();
                break;
//            case FLAG_ABOUT:
//                openAbout();
//                break;
            case FLAG_PACKAGE_MANAGER:
                mNavigationView.setCheckedItem(R.id.nav_pkg_manager);
                openPackageManager();
                break;
            case FLAG_RATING:
                getParentActivity().openAppInStore();
                break;
        }
    }

    @Override
    public void setPresenter(IMainContract.IMainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessage(int resId) {

    }

    public void openDeviceInfo() {
        switchSearchView(false);
        mActivity.getSupportActionBar().setTitle(getString(R.string.action_device_info));
        DeviceInfoFragment fragment;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragment = (DeviceInfoFragment) fm.findFragmentByTag(AppConstants.Tab.FLAG_DEVICE_INFO);
        if (fragment == null) {
            Log.i(TAG, "openDeviceInfo: Create New");
            fragment = DeviceInfoFragment.newInstance(this);
            transaction.add(R.id.content_frame, fragment, AppConstants.Tab.FLAG_DEVICE_INFO);
        }
        Fragment fragment1 = fm.findFragmentByTag(AppConstants.Tab.FLAG_PKG_MGR);
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        transaction.show(fragment);
        transaction.commit();


    }

    public void openPackageManager() {
        switchSearchView(true);
        mActivity.getSupportActionBar().setTitle(getString(R.string.action_package_manager));
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        PackageManagerFragment fragment = (PackageManagerFragment) fm.findFragmentByTag(AppConstants.Tab.FLAG_PKG_MGR);
        if (fragment == null) {
            Log.i(TAG, "openPackageManager: Create New");
            fragment = PackageManagerFragment.newInstance(this);
            mPackageManagerView = fragment;
            transaction.add(R.id.content_frame, fragment, AppConstants.Tab.FLAG_PKG_MGR);
        }
        Fragment fragment1 = fm.findFragmentByTag(AppConstants.Tab.FLAG_DEVICE_INFO);
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        transaction.show(fragment);
        transaction.commit();

    }

    public void openAbout() {
        Intent i = new Intent(getContext(), AboutActivity.class);
        startActivity(i);
    }

    public void openRateApp() {

    }

    @Override
    public void clearSearchView() {
        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.clearFocus();
        }
    }

    private void openMoreApps() {

    }

    private void switchSearchView(boolean visibled) {
        Log.i(TAG, "switchSearchView: " + visibled);
        if (mOptionMenu != null) mOptionMenu.findItem(R.id.action_search).setVisible(visibled);
    }

}
