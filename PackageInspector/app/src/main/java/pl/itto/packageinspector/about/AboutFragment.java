package pl.itto.packageinspector.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.itto.packageinspector.R;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class AboutFragment extends Fragment {
    public static AboutFragment newInstance() {
//        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_about,container,false);
        return v;
    }
}
