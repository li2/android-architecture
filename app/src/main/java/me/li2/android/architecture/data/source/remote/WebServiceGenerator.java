package me.li2.android.architecture.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import arch.LiveDataCallAdapterFactory;
import arch.NetworkConnectivityInterceptor;
import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.source.remote.deserializer.ArticleDeserializer;
import me.li2.android.architecture.data.source.remote.deserializer.ArticlesDeserializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Centralize Retrofit client.
 *
 * It's common practice to just use one OkHttpClient instance to reuse open socket connections.
 * We chose to use the static field, and because the httpClient is used throughout this class,
 * we need to make all fields and methods static.
 *
 * Created by weiyi on 26/03/2017.
 * https://github.com/li2
 */

@Module
public class WebServiceGenerator {
    private static final String BASE_URL = "https://li2.gitbooks.io/android-programming-journey/content/assets/";

    @Provides
    @Singleton
    Gson provideGson(ArticlesDeserializer articlesDeserializer,
                     ArticleDeserializer articleDeserializer) {
        //registerTypeAdapter with list https://stackoverflow.com/a/7668766/2722270
        Type type = new TypeToken<List<Article>>() {}.getType();

        return new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(type, articlesDeserializer)
                .registerTypeAdapter(Article.class, articleDeserializer)
                .create();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(HttpLoggingInterceptor loggingInterceptor,
                                                    NetworkConnectivityInterceptor networkConnectivityInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(networkConnectivityInterceptor)
                .addInterceptor(loggingInterceptor);
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(Gson gson, OkHttpClient.Builder httpClient, LiveDataCallAdapterFactory liveDataCallAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // An Adapter for adapting RxJava 2.x types
                .addCallAdapterFactory(liveDataCallAdapterFactory) // notebyweiyi Unable to create call adapter for android.arch.lifecycle.LiveData
                .client(httpClient.build());
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.build();
    }

    @Provides
    @Singleton
    ArticlesServiceApi provideDemoWebService(Retrofit retrofit) {
        return retrofit.create(ArticlesServiceApi.class);
    }
}
