//package pl.itto.packageinspector.appdetail.view;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import pl.itto.packageinspector.R;
//import pl.itto.packageinspector.appdetail.IAppDetailContract;
//import pl.itto.packageinspector.appdetail.IAppDetailContract.IAppDetailPresenter;
//import pl.itto.packageinspector.appdetail.model.AppInfo;
//import pl.itto.packageinspector.base.BaseFragmentActivity;
//
//import static pl.itto.packageinspector.utils.AppConstants.AppDetail.EXTRA_KEY;
//
///**
// * Created by PL_itto on 9/20/2017.
// */
//
//public class AppDetailFragment1 extends Fragment implements IAppDetailContract.IAppDetailView {
//    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
//    @BindView(R.id.img_icon)
//    ImageView mIcon;
//    @BindView(R.id.txt_app_name)
//    TextView mAppName;
//    @BindView(R.id.txt_version)
//    TextView mVersion;
//    @BindView(R.id.btn_uninstall)
//    Button mUninstall;
//    @BindView(R.id.btn_stop)
//    Button mForceStop;
//    @BindView(R.id.btn_launch)
//    Button mLaunch;
//    @BindView(R.id.txt_package)
//    TextView mPackagename;
//    @BindView(R.id.txt_size)
//    TextView mSize;
//    @BindView(R.id.txt_permission)
//    TextView mPermissions;
//    @BindView(R.id.txt_date)
//    TextView mInstalledDate;
//    @BindView(R.id.txt_date_modify)
//    TextView mModifiedDate;
//
//    private String mAppPackageName;
//    private IAppDetailPresenter mPresenter;
//
//    public static AppDetailFragment1 newInstance(String pkgName) {
//        Bundle args = new Bundle();
//        args.putString(EXTRA_KEY, pkgName);
//        AppDetailFragment1 fragment = new AppDetailFragment1();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//        setHasOptionsMenu(true);
//        mAppPackageName = getArguments().getString(EXTRA_KEY);
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.frag_app_detail, container, false);
//
//        ButterKnife.bind(this, view);
////        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
//        activity.setSupportActionBar(mToolbar);
//        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (mAppPackageName != null) {
//            mPresenter.loadAppInfo(mAppPackageName);
//        }
//    }
//
//
//    @Override
//    public void setPresenter(IAppDetailPresenter presenter) {
//        mPresenter = presenter;
//    }
//
//    @Override
//    public void updateAppInfo(AppInfo info) {
//        mAppName.setText(info.getAppName());
//        mVersion.setText(info.getVersionName());
//        mPackagename.setText(mAppPackageName);
//        double length = (double) info.getAppSize() / (1024 * 1024);
//        String x = String.format("%.2f", length).replace(',', '.');
//        mSize.setText(x + " MB");
//        mPermissions.setText(info.getPermissions(getContext()));
//        mInstalledDate.setText(info.getInstalledDateString());
//        mModifiedDate.setText(info.getModifiedDateString());
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                getActivity().onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
