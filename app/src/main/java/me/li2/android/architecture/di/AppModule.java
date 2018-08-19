package me.li2.android.architecture.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.source.local.ArticlesDao;
import me.li2.android.architecture.data.source.local.AppDatabase;
import me.li2.android.architecture.utils.AppExecutors;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.BaseResourceProvider;
import me.li2.android.architecture.utils.AppConstantsKt;
import me.li2.android.architecture.utils.ImageLoader;
import me.li2.android.architecture.utils.RateLimiter;
import me.li2.android.architecture.utils.ResourceProvider;

/**
 * We provide retrofit, OKHttp, persistence db, shared pref etc here.
 * There is an important detail here. We have to add our sub-components to AppModule.
 * So our dagger graph will understand that.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    BaseResourceProvider provideResourceProvider(ResourceProvider provider) {
        return provider;
    }

    @Provides
    @Singleton
    BaseImageLoader provideBaseImageLoader(ImageLoader imageLoader) {
        return imageLoader;
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new AppExecutors.MainThreadExecutor());
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, AppConstantsKt.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    ArticlesDao provideArticlesDao(AppDatabase database) {
        return database.articlesDao();
    }

    @Provides
    RateLimiter<String> provideRateLimiter() {
        return new RateLimiter<>(2, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    Picasso providePicasso(Context context) {
        return new Picasso.Builder(context.getApplicationContext())
                .loggingEnabled(true)
                .downloader(new OkHttp3Downloader(context.getApplicationContext(), Integer.MAX_VALUE))
                .listener((picasso1, uri, exception) -> exception.printStackTrace())
                .build();
    }
}
