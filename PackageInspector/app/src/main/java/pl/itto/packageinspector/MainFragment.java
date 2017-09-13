package pl.itto.packageinspector;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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

import pl.itto.packageinspector.ui.about.AboutActivity;
import pl.itto.packageinspector.ui.deviceinfo.view.DeviceInfoFragment;
import pl.itto.packageinspector.ui.pkgmanager.presenter.IPackageManagerContract.IPackageManagerView;
import pl.itto.packageinspector.ui.pkgmanager.view.PackageManagerFragment;
import pl.itto.packageinspector.ui.setting.SettingActivity;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class MainFragment extends Fragment implements IMainContract.IMainView {
    public static final String TAG = "PL_itto.MainFragment";
    public static final int FLAG_DEVICE_INFO = 0;
    public static final int FLAG_PACKAGE_MANAGER = 1;
    public static final int FLAG_ABOUT = 2;
    public static final int FLAG_RATING = 3;
    public static final int FLAG_MORE_APPS = 4;
    public static final int FLAG_UNKNOWN = -1;

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private IMainContract.IMainPresenter mPresenter;
    private boolean mFirstStart = true;
    private MainActivity mActivity;
    private NavigationView mNavigationView;
    private Menu mOptionMenu;
    private IPackageManagerView mPackageManagerView;

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
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                int navId = FLAG_UNKNOWN;
                switch (item.getItemId()) {
                    case R.id.nav_device_info:
                        navId = FLAG_DEVICE_INFO;
                        break;
                    case R.id.nav_pkg_manager:
                        navId = FLAG_PACKAGE_MANAGER;
                        break;
                    case R.id.nav_about:
                        navId = FLAG_ABOUT;
//                        Intent i = new Intent(getContext(), AboutActivity.class);
//                        startActivity(i);
                        break;
                    case R.id.nav_rate:
                        navId = FLAG_RATING;
                        break;
                    case R.id.nav_more_apps:
                        navId = FLAG_MORE_APPS;
                        break;
                    case R.id.nav_setting:
                        Intent i = new Intent(getContext(), SettingActivity.class);
                        startActivity(i);
                        break;
                }
//                item.setChecked(true);
//                mDrawerLayout.closeDrawers();
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
        Log.i(TAG, "onViewCreated");
        if (mFirstStart) {
            mFirstStart = false;
            mPresenter.start();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        mOptionMenu = menu;
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) mOptionMenu.findItem(R.id.action_search).getActionView();
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
        Log.i(TAG, "onCreateOptionsMenu");
    }

    @SuppressWarnings("unused")
    @Override
    public void navigateView(int id) {
        mDrawerLayout.closeDrawers();
        switch (id) {
            case FLAG_DEVICE_INFO:
                mNavigationView.setCheckedItem(R.id.nav_device_info);
                openDeviceInfo();
                break;
            case FLAG_ABOUT:
                openAbout();
                break;
            case FLAG_PACKAGE_MANAGER:
                mNavigationView.setCheckedItem(R.id.nav_pkg_manager);
                openPackageManager();
                break;
        }
    }

    @Override
    public void setPresenter(IMainContract.IMainPresenter presenter) {
        mPresenter = presenter;
    }

    private void openDeviceInfo() {
        switchSearchView(false);
        mActivity.getSupportActionBar().setTitle(getString(R.string.action_device_info));
        DeviceInfoFragment fragment = DeviceInfoFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.contentFrame, fragment).commit();
    }

    private void openPackageManager() {
        switchSearchView(true);
        mActivity.getSupportActionBar().setTitle(getString(R.string.action_package_manager));
        PackageManagerFragment fragment = new PackageManagerFragment();
        mPackageManagerView = fragment;
        getFragmentManager().beginTransaction().replace(R.id.contentFrame, fragment).commit();
    }

    private void openAbout() {
        Intent i = new Intent(getContext(), AboutActivity.class);
        startActivity(i);
    }

    private void openRateApp() {

    }

    private void openMoreApps() {

    }

    private void switchSearchView(boolean visibled) {
        if (mOptionMenu != null) mOptionMenu.findItem(R.id.action_search).setVisible(visibled);
    }

}
