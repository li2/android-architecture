package me.li2.android.architecture.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.repository.DemoRepository;
import me.li2.android.architecture.data.source.local.ArticleDao;
import me.li2.android.architecture.data.source.local.DemoDatabase;
import me.li2.android.architecture.data.source.remote.DemoWebService;
import me.li2.android.architecture.data.source.remote.WebServiceGenerator;
import me.li2.android.architecture.utils.AppExecutors;
import me.li2.android.architecture.utils.RateLimiter;

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
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new AppExecutors.MainThreadExecutor());
    }

    @Provides
    @Singleton
    DemoDatabase provideDemoDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                DemoDatabase.class, DemoDatabase.DATABASE_NAME).build();
    }

    @Provides
    ArticleDao provideArticleDao(DemoDatabase database) {
        return database.articleDao();
    }

    @Provides
    RateLimiter<String> provideRateLimiter() {
        return new RateLimiter<>(2, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    DemoWebService provideDemoWebService() {
        return WebServiceGenerator.createService(DemoWebService.class);
    }
}
