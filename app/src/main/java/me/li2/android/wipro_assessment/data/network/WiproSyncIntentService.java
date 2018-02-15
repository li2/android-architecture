package me.li2.android.wipro_assessment.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import me.li2.android.wipro_assessment.utils.InjectorUtils;

/**
 * Schedule a sync immediately with the server off of the main thread.
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class WiproSyncIntentService extends IntentService {
    private static final String LOG_TAG = WiproSyncIntentService.class.getSimpleName();

    public WiproSyncIntentService() {
        super("WiproSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "Intent service started");
        WiproNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(getApplicationContext());
        networkDataSource.fetchCountryIntros();
    }
}
