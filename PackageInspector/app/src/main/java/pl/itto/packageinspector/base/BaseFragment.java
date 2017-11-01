package pl.itto.packageinspector.base;

import android.support.v4.app.Fragment;

/**
 * Created by PL_itto on 11/1/2017.
 */

public class BaseFragment extends Fragment {
    public BaseFragmentActivity getParentActivity() {
        return (BaseFragmentActivity) getActivity();
    }
}
