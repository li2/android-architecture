package me.li2.android.architecture.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.source.local.AppDatabase;
import me.li2.android.architecture.data.source.local.OffersDao;
import me.li2.android.architecture.utils.AppExecutors;
import me.li2.android.architecture.utils.BaseImageLoader;
import me.li2.android.architecture.utils.BaseResourceProvider;
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
                AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    OffersDao provideOffersDao(AppDatabase database) {
        return database.offersDao();
    }

    @Provides
    RateLimiter<String> provideRateLimiter() {
        return new RateLimiter<>(2, TimeUnit.MINUTES);
    }
}
