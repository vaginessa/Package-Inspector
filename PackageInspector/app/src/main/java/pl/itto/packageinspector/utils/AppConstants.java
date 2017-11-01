package pl.itto.packageinspector.utils;

import android.Manifest;

/**
 * Created by PL_itto on 8/23/2017.
 */

public class AppConstants {
    public class Settings {
        public static final String SETTING_PREFS = "settings";
        public static final String SETTING_APK_PATH_KEY = "apk_path";
        public static final String SETTING_ABOUT_KEY = "about";
        public static final int REQUEST_APK_PATH = 100;

        public static final String EXTRA_APK_PATH = "extra_apk_path";
    }

    public class Tab {
        public static final String FLAG_DEVICE_INFO = "device_info";
        public static final String FLAG_PKG_MGR = "package_manager";
    }

    public class PkgManager {
        public static final int REQUEST_APP_DETAIL = 1;
    }

    public class AppDetail {
        public static final String EXTRA_KEY = "extra";
        public static final String RESULT_KEY = "result_key";
        public static final String RESULT_PACKAGE = "result_package";

        public static final int RESULT_UNINSTALL = 1;

        public static final int REQUEST_UNINSTALL = 111;
    }

    public static final String[] PERMISSIONS_LIST = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int PERMISSION_REQUEST_CODE = 100;
}
