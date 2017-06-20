package pl.itto.packageinspector;

import pl.itto.packageinspector.base.IBaseView;

/**
 * Created by PL_itto on 6/15/2017.
 */

public interface IMainView extends IBaseView<IMainPresenter> {
    void navigateView(int id);

}
