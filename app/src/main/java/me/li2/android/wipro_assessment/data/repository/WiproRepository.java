package me.li2.android.wipro_assessment.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import architecture_components.utils.NetworkBoundResource;
import me.li2.android.wipro_assessment.data.model.Article;
import me.li2.android.wipro_assessment.data.source.local.ArticleDao;
import architecture_components.utils.ApiResponse;
import architecture_components.utils.Resource;
import me.li2.android.wipro_assessment.data.source.remote.WiproWebService;
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
    private final ArticleDao mArticleDao;
    private final WiproWebService mWiproWebService;
    private final AppExecutors mExecutors;
    private final Context mContext;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(2, TimeUnit.MINUTES);

    public WiproRepository(
            Context context, ArticleDao articleDao, WiproWebService wiproWebService, AppExecutors executors) {
        mContext = context;
        mArticleDao = articleDao;
        mWiproWebService = wiproWebService;
        mExecutors = executors;
    }

    public synchronized static WiproRepository getInstance(
            Context context, ArticleDao articleDao, WiproWebService wiproWebService, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new WiproRepository(context, articleDao, wiproWebService, executors);
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
    public LiveData<Resource<List<Article>>> loadArticleList() {
        return new NetworkBoundResource<List<Article>, List<Article>>(mExecutors) {
            /**
             * Called to save the result of the API response into the database
             *
             * @param articles
             */
            @Override
            protected void saveCallResult(@NonNull List<Article> articles) {
                // Insert new article data into the database
                mArticleDao.bulkInsert(articles.toArray(new Article[articles.size()]));
                Log.d(LOG_TAG, "new values inserted");
            }

            /**
             * Called with the data in the database to decide whether it should be fetched from the network.
             *
             * @param data
             * @return
             */
            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(LOG_TAG);
            }

            /**
             * Called to get the cached data from the database
             *
             * @return
             */
            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return mArticleDao.getArticleList();
            }

            /**
             * Called to create the web service API call.
             *
             */
            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Article>>> createCall() {
                return mWiproWebService.getArticleList();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(LOG_TAG);
                
            }
        }.asLiveData();
    }
}
