package me.li2.android.architecture.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.li2.android.architecture.app.DemoApplication;

/**
 * Created by weiyi on 17/2/18.
 * https://github.com/li2
 */

public class NetworkUtils {

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) DemoApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return  activeNetwork != null && activeNetwork.isConnected();
    }

    public static IntentFilter connectivityChangeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        return filter;
    }

    public static boolean isConnectivityChangeAction(String action) {
        return action.equals(ConnectivityManager.CONNECTIVITY_ACTION);
    }
}
