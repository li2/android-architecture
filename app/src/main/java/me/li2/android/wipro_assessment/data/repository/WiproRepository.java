package me.li2.android.wipro_assessment.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.li2.android.wipro_assessment.data.database.CountryEntry;
import me.li2.android.wipro_assessment.data.database.CountryIntroDao;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;
import me.li2.android.wipro_assessment.data.network.ApiResponse;
import me.li2.android.wipro_assessment.data.network.Resource;
import me.li2.android.wipro_assessment.data.network.WiproWebService;
import me.li2.android.wipro_assessment.utils.AppExecutors;
import me.li2.android.wipro_assessment.utils.RateLimiter;

/**
 * Handles data operations in Wipro App
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class WiproRepository {
    private static final String LOG_TAG = WiproRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static WiproRepository sInstance;
    private final CountryIntroDao mCountryIntroDao;
    private final WiproWebService mWiproWebService;
    private final AppExecutors mExecutors;
    private final Context mContext;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(2, TimeUnit.MINUTES);

    public WiproRepository(
            Context context, CountryIntroDao countryIntroDao, WiproWebService wiproWebService, AppExecutors executors) {
        mContext = context;
        mCountryIntroDao = countryIntroDao;
        mWiproWebService = wiproWebService;
        mExecutors = executors;
    }

    public synchronized static WiproRepository getInstance(
            Context context, CountryIntroDao countryIntroDao, WiproWebService wiproWebService, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new WiproRepository(context, countryIntroDao, wiproWebService, executors);
                    Log.d(LOG_TAG, "Made new repository");
                }
            }
        }
        return sInstance;
    }

    /**
     *
     * @return
     */
    public LiveData<Resource<List<CountryIntroEntry>>> loadCountryIntros() {
        return new NetworkBoundResource<List<CountryIntroEntry>, CountryEntry>(mExecutors) {
            /**
             * Called to save the result of the API response into the database
             *
             * @param item
             */
            @Override
            protected void saveCallResult(@NonNull CountryEntry item) {
                // Insert new country intro data into the database
                List<CountryIntroEntry> intros = item.getIntros();
                mCountryIntroDao.bulkInsert(intros.toArray(new CountryIntroEntry[intros.size()]));

                // TODO refactor with DB
                setCountryTitle(item.getTitle());
                Log.d(LOG_TAG, "new values inserted");
            }

            /**
             * Called with the data in the database to decide whether it should be fetched from the network.
             *
             * @param data
             * @return
             */
            @Override
            protected boolean shouldFetch(@Nullable List<CountryIntroEntry> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(getCountryTitle());
            }

            /**
             * Called to get the cached data from the database
             *
             * @return
             */
            @NonNull
            @Override
            protected LiveData<List<CountryIntroEntry>> loadFromDb() {
                return mCountryIntroDao.getCountryIntroList();
            }

            /**
             * Called to create the web service API call.
             *
             */
            @NonNull
            @Override
            protected LiveData<ApiResponse<CountryEntry>> createCall() {
                return mWiproWebService.getCountry();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(getCountryTitle());
            }
        }.asLiveData();
    }

    private static final String PREF_KEY_COUNTRY_TITLE = "pref_key_country_title";

    private void setCountryTitle(String title) {
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit()
                .putString(PREF_KEY_COUNTRY_TITLE, title)
                .apply();
    }

    public String getCountryTitle() {
        return PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(PREF_KEY_COUNTRY_TITLE, "");
    }
}
