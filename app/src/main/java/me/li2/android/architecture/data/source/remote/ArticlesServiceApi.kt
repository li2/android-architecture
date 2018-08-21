package me.li2.android.architecture.data.source.remote

import android.arch.lifecycle.LiveData

import arch.ApiResponse
import me.li2.android.architecture.data.model.Article
import retrofit2.http.GET

/**
 * Created by weiyi on 20/08/2018.
 * https://github.com/li2
 */

interface ArticlesServiceApi {

    // https://li2.gitbooks.io/android-programming-journey/content/assets/demo-articles.json
    @GET("demo-articles.json")
    fun getArticles(): LiveData<ApiResponse<List<Article>>>
}
