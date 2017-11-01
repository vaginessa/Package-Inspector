package pl.itto.packageinspector.pkgmanager.view;

import android.app.ProgressDialog;
import android.content.Context;
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

import pl.itto.packageinspector.IMainContract;
import pl.itto.packageinspector.R;
import pl.itto.packageinspector.data.DataManager;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract.IListAppView;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract.IPackageManagerPresenter;
import pl.itto.packageinspector.pkgmanager.presenter.PackageManagerPresenter;


/**
 * Created by PL_itto on 6/15/2017.
 */

public class PackageManagerFragment extends Fragment implements IPackageManagerContract.IPackageManagerView {
    private static final String TAG = "PL_itto.PackageManagerFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppsPagerAdapter mAppsPagerAdapter;
    private IDataSource mDataSource;
    private IPackageManagerPresenter mPresenter;
    private ProgressDialog mDialog;
    private boolean mFirstRun = true;
    private int mCurrentPage = -1;
    private IMainContract.IMainView mParentView;

    public static PackageManagerFragment newInstance(IMainContract.IMainView view) {
        Log.i(TAG, "newInstance: ");
        PackageManagerFragment fragment = new PackageManagerFragment();
        fragment.mParentView = view;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        mDataSource = DataManager.getInstance(getContext());
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mFirstRun) {
            Log.i(TAG, "onCreateView: FirstView" + mFirstRun);
            mFirstRun = false;
            mPresenter.start();
        } else {
            setupAdapter();
            hideLoadAppProgress();
        }
    }

    @Override
    public void notifyDataChanged() {

    }

    @Override
    public void showLoadAppProgress() {
        Log.i(TAG, "showLoadAppProgress: ");
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
        Thread.dumpStack();
        mAppsPagerAdapter = new AppsPagerAdapter(getContext(), getChildFragmentManager());
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
                if (mCurrentPage != -1) {
                    if (mAppsPagerAdapter != null) {
                        IListAppView temp = mAppsPagerAdapter.getRegisteredFragment(mCurrentPage);
                        if (temp != null)
                            temp.clearFilter();
                    }
                }
                mCurrentPage = position;
                if (getParentView() != null) {
                    getParentView().clearSearchView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void filter(String name) {
        IListAppView currentTab = mAppsPagerAdapter.getRegisteredFragment(mViewPager.getCurrentItem());
        if (currentTab != null) {
            currentTab.filter(name);
        }
    }

    @Override
    public void clearFilter() {
        IListAppView currentTab = mAppsPagerAdapter.getRegisteredFragment(mViewPager.getCurrentItem());
        if (currentTab != null) {
            currentTab.clearFilter();
        }
    }

    @Override
    public IMainContract.IMainView getParentView() {
        return mParentView;
    }

    @Override
    public void setPresenter(IPackageManagerPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessage(int resId) {

    }


    class AppsPagerAdapter extends FragmentStatePagerAdapter {
        private Context mContext;
        private String[] mTitleList;
        SparseArray<IListAppView> registeredFragment = new SparseArray<>();

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
            registeredFragment.put(position, (IListAppView) fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragment.remove(position);
            super.destroyItem(container, position, object);
        }

        public IListAppView getRegisteredFragment(int pos) {
            return registeredFragment.get(pos);
        }
    }


}
