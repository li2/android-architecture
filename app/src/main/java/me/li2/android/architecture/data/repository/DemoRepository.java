package me.li2.android.architecture.data.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import architecture_components.utils.NetworkBoundResource;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.source.local.ArticleDao;
import architecture_components.utils.ApiResponse;
import architecture_components.utils.Resource;
import me.li2.android.architecture.data.source.remote.DemoWebService;
import me.li2.android.architecture.utils.AppExecutors;
import me.li2.android.architecture.utils.RateLimiter;

/**
 * Handles data operations in this Demo App
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class DemoRepository {
    private static final String LOG_TAG = DemoRepository.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static DemoRepository sInstance;
    private final ArticleDao mArticleDao;
    private final DemoWebService mDemoWebService;
    private final AppExecutors mExecutors;
    private final Context mContext;
    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(2, TimeUnit.MINUTES);

    @Inject
    public DemoRepository(
            Context context, ArticleDao articleDao, DemoWebService demoWebService, AppExecutors executors) {
        mContext = context;
        mArticleDao = articleDao;
        mDemoWebService = demoWebService;
        mExecutors = executors;
    }

    public synchronized static DemoRepository getInstance(
            Context context, ArticleDao articleDao, DemoWebService demoWebService, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new DemoRepository(context, articleDao, demoWebService, executors);
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
                return mDemoWebService.getArticleList();
            }

            @Override
            protected void onFetchFailed() {
                repoListRateLimit.reset(LOG_TAG);
                
            }
        }.asLiveData();
    }

    public LiveData<Article> loadArticle(int id) {
        return mArticleDao.getArticle(id);
    }
}
