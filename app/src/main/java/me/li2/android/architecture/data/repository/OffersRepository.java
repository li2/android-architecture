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
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.source.local.ArticlesDao;
import me.li2.android.architecture.data.source.local.OffersDao;
import me.li2.android.architecture.data.source.remote.ArticlesServiceApi;
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
    private static final String LOG_TAG = OffersRepository.class.getSimpleName();

    @Inject
    ArticlesDao mArticlesDao;

    @Inject
    OffersDao mOffersDao;

    @Inject
    ArticlesServiceApi mArticlesServiceApi;

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

    /**
     *
     * @return
     */
    public LiveData<Resource<List<Article>>> loadArticles() {
        return new NetworkBoundResource<List<Article>, List<Article>>(mExecutors) {
            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticlesDao.getArticles();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(LOG_TAG);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Article>>> createCall() {
                return mArticlesServiceApi.getArticles();
            }

            @Override
            protected void saveCallResult(@NonNull List<Article> articles) {
                // Insert new article data into the database
                mArticlesDao.bulkInsert(articles.toArray(new Article[articles.size()]));
                Log.d(LOG_TAG, "new values inserted");
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(LOG_TAG);
                
            }
        }.asLiveData();
    }

    public LiveData<Offer> loadOffer(String id) {
        return mOffersDao.getOffer(id);
    }

    public LiveData<Resource<List<Offer>>> loadOffers() {
        return new NetworkBoundResource<List<Offer>, List<Offer>>(mExecutors) {
            @NonNull
            @Override
            protected LiveData<List<Offer>> loadFromDb() {
                return mOffersDao.getOffers();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Offer> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(LOG_TAG);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Offer>>> createCall() {
                return mOffersServiceApi.getOffers();
            }

            @Override
            protected void saveCallResult(@NonNull List<Offer> offers) {
                mOffersDao.bulkInsert(offers.toArray(new Offer[offers.size()]));
                Log.d(LOG_TAG, "new values inserted");
            }
        }.asLiveData();
    }
}
