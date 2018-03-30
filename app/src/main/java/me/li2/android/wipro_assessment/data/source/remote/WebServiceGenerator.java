package me.li2.android.wipro_assessment.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import me.li2.android.wipro_assessment.data.model.Article;
import architecture_components.utils.LiveDataCallAdapterFactory;
import me.li2.android.wipro_assessment.utils.NetworkConnectivityInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
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

public class WebServiceGenerator {
    //private static final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private static final String BASE_URL = "https://raw.githubusercontent.com/li2/li2.github.io/master/assets/file/";

    //registerTypeAdapter with list https://stackoverflow.com/a/7668766/2722270
    private static Type type = new TypeToken<List<Article>>() {}.getType();

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(type, new ArticleListDeserializer())
            .registerTypeAdapter(Article.class, new ArticleDeserializer())
            .create();

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(new NetworkConnectivityInterceptor())
            .addInterceptor(loggingInterceptor);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory()) // notebyweiyi Unable to create call adapter for android.arch.lifecycle.LiveData
                    .client(httpClient.build())
            ;

    private static Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
