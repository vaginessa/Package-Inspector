package pl.itto.packageinspector;

/**
 * Created by PL_itto on 6/15/2017.
 */

public class MainPresenter implements IMainContract.IMainPresenter {
    IMainContract.IMainView mMainView;
    public MainPresenter(IMainContract.IMainView mainView) {
        mMainView = mainView;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        processNavigationSelected(MainFragment.FLAG_DEVICE_INFO);
    }

    @Override
    public void processNavigationSelected(int id) {
        mMainView.navigateView(id);
    }
}
