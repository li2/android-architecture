package me.li2.android.wipro_assessment.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.List;

import me.li2.android.wipro_assessment.data.database.CountryEntry;
import me.li2.android.wipro_assessment.data.database.CountryIntroDao;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;
import me.li2.android.wipro_assessment.data.network.WiproNetworkDataSource;
import me.li2.android.wipro_assessment.utils.AppExecutors;

/**
 * Handles data operations in Wipro App. Acts as a mediator between {@link WiproNetworkDataSource}
 * and {@link CountryIntroDao}
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class WiproRepository {
    private static final String LOG_TAG = WiproRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static WiproRepository sInstance;
    private final CountryIntroDao mCountryIntroDao;
    private final WiproNetworkDataSource mWiproNetworkDataSource;
    private final AppExecutors mExecutors;
    private final Context mContext;
    private boolean mInitialized = false;

    public WiproRepository(
            Context context, CountryIntroDao countryIntroDao, WiproNetworkDataSource wiproNetworkDataSource, AppExecutors executors) {
        mContext = context;
        mCountryIntroDao = countryIntroDao;
        mWiproNetworkDataSource = wiproNetworkDataSource;
        mExecutors = executors;

        LiveData<CountryEntry> networkData = mWiproNetworkDataSource.getCurrentCountry();
        networkData.observeForever(newCountryFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Insert new country intro data into the database
                List<CountryIntroEntry> intros = newCountryFromNetwork.getIntros();
                mCountryIntroDao.bulkInsert(intros.toArray(new CountryIntroEntry[intros.size()]));

                // TODO refactor with DB
                setCountryTitle(newCountryFromNetwork.getTitle());

                Log.d(LOG_TAG, "new values inserted");
            });
        });
    }

    public synchronized static WiproRepository getInstance(
            Context context, CountryIntroDao countryIntroDao, WiproNetworkDataSource wiproNetworkDataSource, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new WiproRepository(context, countryIntroDao, wiproNetworkDataSource, executors);
                    Log.d(LOG_TAG, "Made new repository");
                }
            }
        }
        return sInstance;
    }

    private synchronized void initializeData() {
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) {
            return;
        }
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {
            if (isFetchNeeded()) {
                mWiproNetworkDataSource.startFetchCountryIntroService();
            }
        });
    }

    public LiveData<CountryIntroEntry> getCountrylIntroByTitle(String title) {
        // do "lazy" instantiation of our data - when it's requested, you'll load from the network
        // ensure that every time you getCountryIntroByTitle(), the data initialization is triggered
        initializeData();
        return mCountryIntroDao.getCountryIntro(title);
    }

    public LiveData<List<CountryIntroEntry>> getCountryIntroList() {
        initializeData();
        return mCountryIntroDao.getCountryIntroList();
    }

    public LiveData<List<CountryIntroEntry>> refreshCountryIntroList() {
        mInitialized = false;
        return getCountryIntroList();
    }

    public void deleteOldData() {
        // no need to delete old data, because @Insert(onConflict = OnConflictStrategy.REPLACE)
    }

    private boolean isFetchNeeded() {
        return true; // for debug purpose
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
