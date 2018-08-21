package me.li2.android.architecture.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import me.li2.android.architecture.data.source.local.AppDatabase
import me.li2.android.architecture.data.source.local.ArticlesDao
import me.li2.android.architecture.utils.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * We provide retrofit, OKHttp, persistence db, shared pref etc here.
 * There is an important detail here. We have to add our sub-components to AppModule.
 * So our dagger graph will understand that.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideResourceProvider(provider: ResourceProvider): BaseResourceProvider {
        return provider
    }

    @Provides
    @Singleton
    internal fun provideBaseImageLoader(imageLoader: ImageLoader): BaseImageLoader {
        return imageLoader
    }

    @Provides
    @Singleton
    internal fun provideAppExecutors(): AppExecutors {
        return AppExecutors(
                Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                AppExecutors.MainThreadExecutor()
        )
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    internal fun provideArticlesDao(database: AppDatabase): ArticlesDao {
        return database.articlesDao()
    }

    @Provides
    internal fun provideRateLimiter(): RateLimiter<String> {
        return RateLimiter(2, TimeUnit.MINUTES)
    }

    @Provides
    @Singleton
    internal fun providePicasso(context: Context): Picasso {
        return Picasso.Builder(context.applicationContext)
                .loggingEnabled(true)
                .downloader(OkHttp3Downloader(context.applicationContext, Integer.MAX_VALUE.toLong()))
                .listener { _, _, exception -> exception.printStackTrace() }
                .build()
    }
}
