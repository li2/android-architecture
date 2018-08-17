package me.li2.android.architecture.data.source.remote;

import android.arch.lifecycle.LiveData;

import java.util.List;

import arch.ApiResponse;
import me.li2.android.architecture.data.model.Article;
import retrofit2.http.GET;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public interface ArticlesServiceApi {

    // https://li2.gitbooks.io/android-programming-journey/content/assets/demo-articles.json
    @GET("demo-articles.json")
    LiveData<ApiResponse<List<Article>>> getArticles();
}
