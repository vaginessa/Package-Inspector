package pl.itto.packageinspector;

import android.app.Application;
import android.content.Context;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class MainApp extends Application {
    public static volatile Context mApplicationContex = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContex = getApplicationContext();
    }
}
