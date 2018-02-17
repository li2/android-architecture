package me.li2.android.wipro_assessment.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class WiproApplication extends Application {
    private static WiproApplication sApplication;

    public WiproApplication() {
        sApplication = this;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }
}
