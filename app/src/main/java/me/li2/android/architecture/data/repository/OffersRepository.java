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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.source.local.ArticlesDao;
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
        Observable<List<String>> offers = loadOffers();
        offers.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<String>>() {
                    @Override
                    public void onNext(List<String> strings) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

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

    public LiveData<Article> loadArticle(int id) {
        return mArticlesDao.getArticle(id);
    }

    public Observable<List<String>> loadOffers() {
        return mOffersServiceApi.getOffers()
                .flatMap(list -> Observable.fromIterable(list)
                        .map(offer -> print(offer))
                        .toList()
                        .toObservable());
    }

    private String print(Offer offer) {
        Log.d(LOG_TAG, "Offer " + offer.name);
        return offer.name;
    }
}
