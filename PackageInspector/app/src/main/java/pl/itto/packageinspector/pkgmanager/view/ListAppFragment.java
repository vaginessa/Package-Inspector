package pl.itto.packageinspector.pkgmanager.view;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.itto.packageinspector.MainActivity;
import pl.itto.packageinspector.R;
import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract.IListAppPresenter;
import pl.itto.packageinspector.pkgmanager.presenter.ListAppPresenter;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class ListAppFragment extends Fragment implements IPackageManagerContract.IListAppView, IPackageManagerContract.ISearchAppsCallback {

    private static final String TAG = "PL_itto.ListAppFragment";
    private int position;
    private boolean mSystemApps = false;
    private static final String KEY_POS = "pos";
    private IListAppPresenter mPresenter;
    private IPackageManagerDataSource mDataSource;
    private RecyclerView mListApps;
    private List<AppItem> mAppsList;
    private ListAppAdapter mListAppAdapter;

    public static ListAppFragment newInstance(IPackageManagerDataSource dataSource, int pos) {
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
        Log.i(TAG, "appSize: " + mAppsList.size());
    }

    @Override
    public void search(String name) {
        if (mListAppAdapter != null) mListAppAdapter.filter(name);
    }

    @Override
    public void clearFilter() {
        if (mListAppAdapter != null) mListAppAdapter.clearFilter();
    }

//    @Override
//    public void showExtractSuccess() {
//        getMainActivity().showMessage(getString(R.string.pkg_extract_success));
//    }
//
//    @Override
//    public void showExtractFailed() {
//        getMainActivity().showMessage(getString(R.string.pkg_extract_failed));
//    }

    @Override
    public void showMessage(String msg) {
        getMainActivity().showMessage(msg);
    }

    @Override
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
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

    class ListAppAdapter extends RecyclerView.Adapter<ListAppAdapter.ViewHolder> {


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

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_app_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindApps(mItemList.get(position));
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
                mOption.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_app_detail:
                        mPresenter.showAppDetail();
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
                                extractapk(temp.getApkPath(), temp.getAppName());
                                break;
                            case R.id.action_launch_app:
                                Log.i(TAG, "LaunchApp :" + temp.getAppName() + "/" + temp.getPkgName());
                                launchApp(temp.getPkgName());
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
