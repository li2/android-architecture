package me.li2.android.architecture.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import arch.ApiResponse;
import arch.NetworkBoundResource;
import arch.Resource;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.source.local.OffersDao;
import me.li2.android.architecture.data.source.remote.OffersServiceApi;
import me.li2.android.architecture.utils.AppExecutors;
import me.li2.android.architecture.utils.RateLimiter;

/**
 * Handles data operations in this Demo App
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

@Singleton
public class OffersRepository {
    private static final String TAG = OffersRepository.class.getSimpleName();

    @Inject
    OffersDao mOffersDao;

    @Inject
    OffersServiceApi mOffersServiceApi;

    @Inject
    AppExecutors mExecutors;

    @Inject
    Context mContext;

    @Inject
    RateLimiter<String> repoListRateLimit;

    @Inject
    public OffersRepository(){
    }

    public LiveData<Offer> loadOffer(String id) {
        return mOffersDao.getOffer(id);
    }

    /**
     * Load offers from disk or network
     * @param forceUpdate True to trigger a force refresh
     * @return
     */
    public LiveData<Resource<List<Offer>>> loadOffers(boolean forceUpdate) {
        return new NetworkBoundResource<List<Offer>, List<Offer>>(mExecutors) {
            @NonNull
            @Override
            protected LiveData<List<Offer>> loadFromDb() {
                return mOffersDao.getOffers();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Offer> data) {
                return forceUpdate || data == null || data.isEmpty() || repoListRateLimit.shouldFetch(TAG);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Offer>>> createCall() {
                return mOffersServiceApi.getOffers();
            }

            @Override
            protected void saveCallResult(@NonNull List<Offer> offers) {
                mOffersDao.bulkInsert(offers.toArray(new Offer[offers.size()]));
                Log.d(TAG, "new values inserted");
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(TAG);
            }
        }.asLiveData();
    }
}
