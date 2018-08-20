package me.li2.android.architecture.utils

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import me.li2.android.architecture.app.DemoApplication

/**
 * Created by weiyi on 17/2/18.
 * https://github.com/li2
 */

class NetworkUtils {

    // TODO is this the right approach to convert Java static method to Kotlin?
    val isConnected: Boolean
        get() {
            val cm = DemoApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }

    fun connectivityChangeFilter(): IntentFilter {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        return filter
    }

    fun isConnectivityChangeAction(action: String): Boolean {
        return action == ConnectivityManager.CONNECTIVITY_ACTION
    }
}
