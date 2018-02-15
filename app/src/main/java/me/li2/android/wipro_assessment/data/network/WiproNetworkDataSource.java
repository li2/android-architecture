package me.li2.android.wipro_assessment.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import me.li2.android.wipro_assessment.data.database.CountryEntry;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;
import me.li2.android.wipro_assessment.utils.AppExecutors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Provides APIs for doing all operations with the server data.
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class WiproNetworkDataSource {
    private static final String LOG_TAG = WiproNetworkDataSource.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static WiproNetworkDataSource sInstance;
    private final Context mContext;
    private final AppExecutors mExecutors;

    // LiveData storing the latest downloaded countryIntro
    private MutableLiveData<List<CountryIntroEntry>> mDownloadedCountryIntros;

    private WiproNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedCountryIntros = new MutableLiveData<>();
    }

    public static WiproNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new WiproNetworkDataSource(context, executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<List<CountryIntroEntry>> getCurrentCountryIntros() {
        return mDownloadedCountryIntros;
    }

    /**
     * Starts an intent service to fetch the country intro.
     */
    public void startFetchCountryIntroService() {
        Intent intentToFetch = new Intent(mContext, WiproSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }

    /**
     * Fetch the newest country intro
     */
    void fetchCountryIntros() {
        WiproWebService service = WebServiceGenerator.createService(WiproWebService.class);
        service.getCountry().enqueue(new Callback<CountryEntry>() {
            @Override
            public void onResponse(Call<CountryEntry> call, Response<CountryEntry> response) {
                Log.d(LOG_TAG, "title: " + response.body().getTitle());
                mDownloadedCountryIntros.postValue(response.body().getIntros());
            }

            @Override
            public void onFailure(Call<CountryEntry> call, Throwable t) {
                Log.e(LOG_TAG, "failed to get country intros : " + t.getMessage());
            }
        });
    }
}
