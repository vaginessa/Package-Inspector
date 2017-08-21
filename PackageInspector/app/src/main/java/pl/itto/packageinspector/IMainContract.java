package pl.itto.packageinspector;

import pl.itto.packageinspector.base.IBasePresenter;
import pl.itto.packageinspector.base.IBaseView;

/**
 * Created by PL_itto on 8/21/2017.
 */

public interface IMainContract {
    /**
     * Created by PL_itto on 6/15/2017.
     */

    interface IMainPresenter extends IBasePresenter {
        void processNavigationSelected(int id);
    }

    /**
     * Created by PL_itto on 6/15/2017.
     */

    interface IMainView extends IBaseView<IMainPresenter> {
        void navigateView(int id);

    }
}
