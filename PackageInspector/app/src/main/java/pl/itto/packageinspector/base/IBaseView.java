package pl.itto.packageinspector.base;

/**
 * Created by PL_itto on 6/15/2017.
 */

public interface IBaseView<T> {
    void setPresenter(T presenter);

    void showMessage(String msg);

    void showMessage(int resId);
}
