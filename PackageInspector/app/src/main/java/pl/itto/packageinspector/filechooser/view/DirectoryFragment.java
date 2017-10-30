package pl.itto.packageinspector.filechooser.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.BaseFragmentActivity;
import pl.itto.packageinspector.filechooser.IFileChooserContract;
import pl.itto.packageinspector.filechooser.model.FolderItem;
import pl.itto.packageinspector.utils.AppConstants;
import pl.itto.packageinspector.utils.Utils;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class DirectoryFragment extends Fragment implements IFileChooserContract.IFileChooserView {
    private static final String TAG = "PL_itto.DirectoryFragment";
    View mView;
    private RecyclerView mListView;
    private ArrayList<FolderItem> items = new ArrayList<FolderItem>();
    FolderListAdapter mAdapter;
    Stack<FolderItem> mParentList = null;
    private Toolbar mToolbar;
    private TextView mSelectBtn;
    private String mApkPath = null;
    private int mCurrentSelectPos = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        setup();
        firstInitData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_file_chooser, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mSelectBtn = (TextView) view.findViewById(R.id.option_select);
        setupActionBar();
        mListView = (RecyclerView) view.findViewById(R.id.folders_view);
        mListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mListView.setAdapter(mAdapter);
        mSelectBtn.setClickable(true);
        mSelectBtn.setOnClickListener(mSelectFolderListener);
        return view;
    }

    private void setupActionBar() {
        BaseFragmentActivity activity = (BaseFragmentActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setup() {
        mParentList = new Stack<>();
        items = new ArrayList<>();
        mAdapter = new FolderListAdapter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setPresenter(IFileChooserContract.IFileChooserPresenter presenter) {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showMessage(int resId) {

    }

    class FolderListAdapter extends RecyclerView.Adapter<FolderListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.folder_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindItem(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView mName;
            RadioButton mSelectRadio;

            public ViewHolder(View itemView) {
                super(itemView);
                mName = (TextView) itemView.findViewById(R.id.folder_name);
                mSelectRadio = (RadioButton) itemView.findViewById(R.id.folder_check);
                itemView.setClickable(true);
                itemView.setOnClickListener(this);
                mSelectRadio.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.i(TAG, "onFolderItemClick: ");
                switch (v.getId()) {
                    case R.id.folder_check:
                        int pos = getAdapterPosition();
                        FolderItem item = items.get(pos);
                        if (!item.isParent()) {
                            mCurrentSelectPos = pos;
                        }
                        return;
                }
                FolderItem item = items.get(getAdapterPosition());
                Log.i(TAG, "onClick: " + getAdapterPosition() + " isParent : " + item.isParent());
                if (item.isParent()) {
                    goToParent();
                } else {
                    enterDirectory(item);
                }
                notifyDataSetChanged();
            }

            void bindItem(int pos) {
                FolderItem item = items.get(pos);
                if (item.isParent()) {
                    mSelectRadio.setVisibility(View.GONE);
                }
                mName.setText(item.getTitle());
            }
        }
    }

    public void firstInitData() {
        items.clear();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        FolderItem folder = new FolderItem("Sd card", file, false);
        items.add(folder);
    }

    public void goToParent() {
        Log.w(TAG, "goToParent: " + mParentList.size());
        mCurrentSelectPos = -1;
        if (mParentList.size() > 0) {
            mParentList.pop();
            if (mParentList.size() == 0) {
                firstInitData();
            } else {
                FolderItem curDir = mParentList.peek();
                Utils.loadDirectory(curDir.getFile(), items);
            }

        }
    }

    public void enterDirectory(FolderItem item) {
        Log.w(TAG, "enterDirectory: " + item.getTitle() + "--" + item.getFile().getAbsolutePath());
        mCurrentSelectPos = -1;
        mParentList.push(item);
        Utils.loadDirectory(item.getFile(), items);
    }

    @Override
    public void selectDirectory(String path) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.Settings.EXTRA_APK_PATH, path);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    View.OnClickListener mSelectFolderListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: Select Folder"+mCurrentSelectPos);
            if (mCurrentSelectPos != -1) {
                FolderItem item = items.get(mCurrentSelectPos);
                if (item != null && !item.isParent())
                    if (item.getFile() != null) {
                        Log.i(TAG, "save path: " + item.getFile().getAbsolutePath());
                        selectDirectory(item.getFile().getAbsolutePath());
                    }
            }

        }
    };

}
