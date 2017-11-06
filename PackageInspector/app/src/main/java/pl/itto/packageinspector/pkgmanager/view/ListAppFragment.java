package pl.itto.packageinspector.pkgmanager.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.itto.packageinspector.MainActivity;
import pl.itto.packageinspector.R;
import pl.itto.packageinspector.appdetail.view.AppDetailActivity;
import pl.itto.packageinspector.base.BaseFragment;
import pl.itto.packageinspector.base.IActionCallback;
import pl.itto.packageinspector.data.IDataSource;
import pl.itto.packageinspector.pkgmanager.model.AppItem;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.IPackageManagerContract.IListAppPresenter;
import pl.itto.packageinspector.pkgmanager.presenter.ListAppPresenter;
import pl.itto.packageinspector.utils.AppConstants;

import static pl.itto.packageinspector.utils.AppConstants.PkgManager.*;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class ListAppFragment extends BaseFragment implements IPackageManagerContract.IListAppView {

    private static final String TAG = "PL_itto.ListAppFragment";
    private int position;
    private boolean mSystemApps = false;
    private static final String KEY_POS = "pos";
    private IListAppPresenter mPresenter;
    private IDataSource mDataSource;
    private RecyclerView mListApps;
    private List<AppItem> mAppsList;
    private ListAppAdapter mListAppAdapter;
    //For uninstall app
    private String mRemovedApp = null;
    private int mRemovedAppAdapterPos = -1;
    public static String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";

    NativeAppInstallAd mNativeAd = null;
    AdLoader.Builder builder;

    public static ListAppFragment newInstance(IDataSource dataSource, int pos) {
        ListAppFragment fragment = new ListAppFragment();
        fragment.mDataSource = dataSource;
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_POS, pos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRemovedApp = null;
//        mPresenter = new ListAppPresenter(getContext());
        setPresenter(new ListAppPresenter(getContext()));
        mPresenter.setDataSource(mDataSource);
        mPresenter.setView(this);
        position = getArguments().getInt(KEY_POS, 0);
        Log.i(TAG, "Position: " + position);
        if (position == 1) {
            mSystemApps = false;
        } else {
            mSystemApps = true;
        }
        mAppsList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_list_pkg, container, false);
        mListApps = (RecyclerView) view.findViewById(R.id.list_apps);
        mListApps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line_divider));
        mListApps.addItemDecoration(itemDecoration);
        mListAppAdapter = new ListAppAdapter(getContext());
        mListApps.setAdapter(mListAppAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterData();
//        builder = new AdLoader.Builder(getContext(), ADMOB_AD_UNIT_ID);
        Log.i(TAG, "appSize: " + mAppsList.size());
    }

    @Override
    public void filter(String name) {
        if (mListAppAdapter != null) mListAppAdapter.filter(name);
    }

    @Override
    public void clearFilter() {
        if (mListAppAdapter != null) mListAppAdapter.clearFilter();
    }

    @Override
    public void showMessage(String msg) {
        getMainActivity().showMessage(msg);
    }

    @Override
    public void showMessage(int resId) {
        getParentActivity().showMessage(resId);
    }

    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    @Override
    public void uninstallApp(String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        startActivityForResult(intent, AppConstants.AppDetail.REQUEST_UNINSTALL);
    }

    @Override
    public void showAppDetail(String packageName) {
        Intent intent = new Intent(getContext(), AppDetailActivity.class);
        intent.putExtra(AppConstants.AppDetail.EXTRA_KEY, packageName);
        startActivityForResult(intent, REQUEST_APP_DETAIL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.AppDetail.REQUEST_UNINSTALL:
                    if (mRemovedApp != null && mRemovedAppAdapterPos != -1) {
                        mListAppAdapter.removeApp(mRemovedAppAdapterPos);
                    }
                    break;
                case REQUEST_APP_DETAIL:
                    if (data != null) {
                        int result = data.getIntExtra(AppConstants.AppDetail.RESULT_KEY, -1);
                        if (result == AppConstants.AppDetail.RESULT_UNINSTALL) {
                            String packageName = data.getStringExtra(AppConstants.AppDetail.RESULT_PACKAGE);
                            if (mListAppAdapter != null) {
                                mListAppAdapter.removeApp(packageName);
                            }
                        }
                    }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setPresenter(IListAppPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void extractapk(String path, String name) {
        mPresenter.extractApk(path, name);
    }

    @Override
    public void launchApp(String pkgName) {
        mPresenter.launchApp(pkgName);
    }

    class ListAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        static final int TYPE_AD = 0;
        static final int TYPE_APP = 1;
        private Context mContext;
        private List<AppItem> mItemList = new ArrayList<>();

        public ListAppAdapter(Context context) {
            mContext = context;
        }

        public void replaceData(List<AppItem> list) {
            this.mItemList.clear();
            this.mItemList.addAll(list);
            notifyDataSetChanged();
        }

//        @Override
//        public int getItemViewType(int position) {
//            return position == 0 ? TYPE_AD : TYPE_APP;
//        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
//            if (viewType == TYPE_AD) {
//                view = LayoutInflater.from(mContext).inflate(R.layout.ad_install, parent, false);
//                return new AdHolder(view);
//            }
            view = LayoutInflater.from(mContext).inflate(R.layout.list_app_item, parent, false);
            return new ViewHolder(view);

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder)
                ((ViewHolder) holder).bindApps(mItemList.get(position));
//            else if (holder instanceof AdHolder) {
//                ((AdHolder) holder).bindItem();
//            }
        }

        public void removeApp(int pos) {
            AppItem item = mItemList.get(pos);
            mItemList.remove(pos);
            notifyDataSetChanged();
            mDataSource.removeApp(item.getPkgName());
        }

        public void removeApp(String pkgName) {
            for (int i = 0; i < mItemList.size(); i++) {
                AppItem item = mItemList.get(i);
                if (item.getPkgName().equals(pkgName)) {
                    removeApp(i);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public void filter(String name) {
            name = name.toLowerCase();
            mItemList.clear();
            for (AppItem item : mAppsList) {
                String label = item.getAppName().toLowerCase();
                String pkg = item.getPkgName().toLowerCase();
                if (label.contains(name) || pkg.contains(name))
                    mItemList.add(item);
            }
            notifyDataSetChanged();
        }


        public void clearFilter() {
            mItemList.clear();
            mItemList.addAll(mAppsList);
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
            ImageView mIcon, mOption;
            TextView mLabel, mPackageName;
            LinearLayout mDetailLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                mIcon = (ImageView) itemView.findViewById(R.id.pkg_icon);
                mOption = (ImageView) itemView.findViewById(R.id.pkg_option);
                mLabel = (TextView) itemView.findViewById(R.id.txt_label);
                mPackageName = (TextView) itemView.findViewById(R.id.txt_pkg);
                mDetailLayout = (LinearLayout) itemView.findViewById(R.id.layout_app_detail);
                mDetailLayout.setOnClickListener(this);
                mDetailLayout.setClickable(true);
                mOption.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_app_detail:
                        mPresenter.showAppDetail(mPackageName.getText().toString());
                        break;
                    case R.id.pkg_option:
                        showOptionMenu(mOption, getAdapterPosition());
                        Log.i(TAG, "option button click: ");
                        break;
                }
            }

            private void showOptionMenu(View v, final int pos) {
                final AppItem temp = mItemList.get(pos);
                PopupMenu popupMenu = new PopupMenu(getContext(), v);
                popupMenu.inflate(R.menu.option_list_item);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_extract_apk:
                                Log.i(TAG, "Extract :" + temp.getAppName());
                                if (getParentActivity().isGrantPermisisons())
                                    extractapk(temp.getApkPath(), temp.getAppName());
                                else {
                                    getParentActivity().requestPermissions(new IActionCallback() {
                                        @Override
                                        public void onSuccess(Object result) {
                                            extractapk(temp.getApkPath(), temp.getAppName());
                                        }

                                        @Override
                                        public void onFailed(@Nullable Object error) {
                                            showMessage(R.string.permission_grant_failed);
                                        }
                                    });
                                    break;

                                }
                                break;
                            case R.id.action_launch_app:
                                Log.i(TAG, "LaunchApp :" + temp.getAppName() + "/" + temp.getPkgName());
                                launchApp(temp.getPkgName());
                                break;
                            case R.id.action_remove_app:
                                Log.i(TAG, "Action uninstal app: " + temp.getAppName() + "/" + temp.getPkgName());
                                uninstallApp(temp.getPkgName());
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }


            void bindApps(AppItem item) {
                mIcon.setImageDrawable(item.getDrawableIcon());
                mLabel.setText(item.getAppName());
                mPackageName.setText(item.getPkgName());
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                getActivity().getMenuInflater().inflate(R.menu.option_list_item, menu);
            }
        }

        class AdHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.ad_app_icon)
            ImageView mIcon;
            @BindView(R.id.ad_headline)
            TextView mHeadLines;
            @BindView(R.id.ad_body)
            TextView mBody;
            @BindView(R.id.ad_price)
            TextView mPrice;
            @BindView(R.id.ad_store)
            TextView mStore;
            @BindView(R.id.ad_rating)
            RatingBar mRating;
            @BindView(R.id.ad_root)
            NativeAppInstallAdView mAdRoot;

            public AdHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                mAdRoot.setHeadlineView(mHeadLines);
                mAdRoot.setBodyView(mBody);
                mAdRoot.setIconView(mIcon);
                mAdRoot.setPriceView(mPrice);
                mAdRoot.setStarRatingView(mRating);

            }

            void bindItem() {
                Log.i(TAG, "bindItem: ");
                if (mNativeAd == null) {
                    Log.w(TAG, "bindItem: Loading ads");
                    builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                        @Override
                        public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
                            Log.i(TAG, "onAppInstallAdLoaded: ");
                            if (nativeAppInstallAd != null)
                                mNativeAd = nativeAppInstallAd;
                            bindItem();
                        }
                    });

                    AdLoader loader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                            Log.e(TAG, "onAdFailedToLoad: " + i);
                        }
                    }).build();
                    loader.loadAd(new AdRequest.Builder().build());
                } else {
                    mIcon.setImageDrawable(mNativeAd.getIcon().getDrawable());
                    mHeadLines.setText(mNativeAd.getHeadline());
                    mRating.setRating(mNativeAd.getStarRating().floatValue());
                    mBody.setText(mNativeAd.getBody());
                    mStore.setText(mNativeAd.getStore());
                    mAdRoot.setNativeAd(mNativeAd);
                }
            }
        }
    }

    private void populateInstalldView() {

    }

    private void filterData() {
        mAppsList.clear();
        Log.i(TAG, "pos" + position + " systemApp " + mSystemApps);
        for (AppItem item : mDataSource.getListApps()) {

            if (item.isSystemApp() == mSystemApps)
                mAppsList.add(item);
        }
        if (mListAppAdapter != null) {
            mListAppAdapter.replaceData(mAppsList);
        }
    }

}
