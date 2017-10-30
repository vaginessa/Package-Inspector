package pl.itto.packageinspector.base;

import android.support.annotation.Nullable;

/**
 * Created by PL_itto on 10/24/2017.
 */

public interface IActionCallback {
    void onSuccess(Object result);

    void onFailed(@Nullable Object error);
}
