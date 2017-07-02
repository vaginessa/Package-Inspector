package pl.itto.packageinspector.pkgmanager.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.pkgmanager.data.IPackageManagerDataSource;
import pl.itto.packageinspector.pkgmanager.data.model.AppItem;
import pl.itto.packageinspector.pkgmanager.presenter.IPackageManagerContract;
import pl.itto.packageinspector.pkgmanager.presenter.ListAppPresenter;

/**
 * Created by PL_itto on 6/29/2017.
 */

public class ListAppFragment extends Fragment {

    private static final String TAG = "PL_itto.ListAppFragment";
    private int position;
    private boolean mSystemApps = false;
    private static final String KEY_POS = "pos";
    private IPackageManagerContract.IListAppPresenter mPresenter;
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
        mPresenter = new ListAppPresenter(getContext());
        mPresenter.setDataSource(mDataSource);
        position = getArguments().getInt(KEY_POS, 0);
        Log.i(TAG,"Position: "+position);
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
        mListAppAdapter = new ListAppAdapter(getContext(), mAppsList);
        mListApps.setAdapter(mListAppAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterData();
        mListAppAdapter.notifyDataSetChanged();
        Log.i(TAG, "appSize: " + mAppsList.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
                    Log.i(TAG, "option button click: ");
                    break;
            }
        }

        void bindApps(AppItem item) {
            mIcon.setImageDrawable(item.getDrawableIcon());
            mLabel.setText(item.getAppName());
            mPackageName.setText(item.getPkgName());
        }
    }

    class ListAppAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context mContext;
        private List<AppItem> mItemList;

        public ListAppAdapter(Context context, List<AppItem> list) {
            this.mItemList = list;
            mContext = context;
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
    }

    private void filterData() {
        mAppsList.clear();
        Log.i(TAG,"pos" +position+" systemApp "+mSystemApps);
        for (AppItem item : mDataSource.getListApps()) {

            if (item.isSystemApp() == mSystemApps)
                mAppsList.add(item);
        }
    }

}