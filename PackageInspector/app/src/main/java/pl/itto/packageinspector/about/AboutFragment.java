package pl.itto.packageinspector.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.base.BaseFragment;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class AboutFragment extends BaseFragment {
    ImageView mRate;

    public static AboutFragment newInstance() {
//        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_about, container, false);
        mRate = (ImageView) v.findViewById(R.id.img_like);
        mRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getParentActivity().openAppInStore();
            }
        });
        return v;
    }


}
