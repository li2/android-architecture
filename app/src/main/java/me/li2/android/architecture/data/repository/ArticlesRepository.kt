/**
 * Handles data operations in this Demo App
 *
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */
package me.li2.android.architecture.data.repository

import android.arch.lifecycle.LiveData
import android.util.Log
import arch.ApiResponse
import arch.NetworkBoundResource
import arch.Resource
import me.li2.android.architecture.data.model.Article
import me.li2.android.architecture.data.source.local.ArticlesDao
import me.li2.android.architecture.data.source.remote.ArticlesServiceApi
import me.li2.android.architecture.utils.AppExecutors
import me.li2.android.architecture.utils.RateLimiter
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "ArticlesRepository"

@Singleton
class ArticlesRepository @Inject constructor() {

    @Inject
    lateinit var mArticlesDao: ArticlesDao

    @Inject
    lateinit var mArticlesServiceApi: ArticlesServiceApi

    @Inject
    lateinit var mExecutors: AppExecutors

    @Inject
    lateinit var repoListRateLimit: RateLimiter<String>


    fun loadArticles(forceUpdate: Boolean): LiveData<Resource<List<Article>>> {
        return object : NetworkBoundResource<List<Article>, List<Article>>(mExecutors) {
            override fun loadFromDb(): LiveData<List<Article>> {
                return mArticlesDao.getArticles()
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return forceUpdate || data == null || data.isEmpty() || repoListRateLimit.shouldFetch(TAG)
            }

            override fun createCall(): LiveData<ApiResponse<List<Article>>> {
                return mArticlesServiceApi.getArticles()
            }

            override fun saveCallResult(articles: List<Article>) {
                // Insert new article data into the database
                mArticlesDao.bulkInsert(*articles.toTypedArray())
                Log.d(TAG, "new values inserted")
            }

            override fun onFetchFailed() {
                repoListRateLimit.reset(TAG)

            }
        }.asLiveData()
    }

    fun loadArticle(id: Int): LiveData<Article> {
        return mArticlesDao.getArticle(id)
    }
}
