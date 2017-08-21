package pl.itto.packageinspector.pkgmanager.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.data.PackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.IPackageManagerPresenter;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.ISearchAppsCallback;
import pl.itto.packageinspector.pkgmanager.presenter.PackageManagerPresenter;


/**
 * Created by PL_itto on 6/15/2017.
 */

public class PackageManagerFragment extends Fragment implements IPackageManagerContract.IPackageManagerView {
    private static final String TAG = "PL_itto.PackageManagerFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppsPagerAdapter mAppsPagerAdapter;
    private IPackageManagerDataSource mDataSource;
    private IPackageManagerPresenter mPresenter;
    private ProgressDialog mDialog;
    private boolean mFirstRun = true;
    private ISearchAppsCallback mSearchCallback;

    public static PackageManagerFragment newInstance() {
        PackageManagerFragment fragment = new PackageManagerFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDataSource = new PackageManagerDataSource(getContext());
        mPresenter = new PackageManagerPresenter(getContext());
        mPresenter.setDataSource(mDataSource);
        mPresenter.setView(this);
        mFirstRun = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_pkg_mgr, container, false);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabs_pkg_mgr);
        mViewPager = (ViewPager) view.findViewById(R.id.pager_pkg_mgr);
        mTabLayout.setupWithViewPager(mViewPager);
        if (mFirstRun) {
            mFirstRun = false;
            mPresenter.start();
        }

        return view;
    }

    @Override
    public void notifyDataChanged() {

    }

    @Override
    public void showLoadAppProgress() {
        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage(getString(R.string.pkg_loading_apps));
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setCancelable(false);
        mDialog.show();


    }

    @Override
    public void hideLoadAppProgress() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void setupAdapter() {
        mAppsPagerAdapter = new AppsPagerAdapter(getContext(), getFragmentManager());
        mViewPager.setAdapter(mAppsPagerAdapter);
        mAppsPagerAdapter.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            boolean first = true;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                    onPageSelected(0);
                    first = false;
                }
            }

            @Override
            public void onPageSelected(int position) {
                mSearchCallback = (ISearchAppsCallback) mAppsPagerAdapter.getRegisteredFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void filter(String name) {
        mSearchCallback.search(name);
    }

    @Override
    public void clearFilter() {
        mSearchCallback.clearFilter();
    }

    @Override
    public void setPresenter(IPackageManagerPresenter presenter) {
        mPresenter = presenter;
    }


    class AppsPagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;
        private String[] mTitleList;
        SparseArray<Fragment> registeredFragment = new SparseArray<>();

        public AppsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
            setup();
        }

        private void setup() {
            mTitleList = new String[2];
            mTitleList[0] = mContext.getString(R.string.pkg_system_apps);
            mTitleList[1] = mContext.getString(R.string.pkg_other_apps);
        }

        @Override
        public Fragment getItem(int position) {
            return ListAppFragment.newInstance(mDataSource, position);
        }

        @Override
        public int getCount() {
            return mTitleList.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragment.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragment.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int pos) {
            return registeredFragment.get(pos);
        }
    }


}
