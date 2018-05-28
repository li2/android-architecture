package me.li2.android.architecture.app;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import me.li2.android.architecture.di.AppComponent;
import me.li2.android.architecture.di.DaggerAppComponent;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class DemoApplication extends DaggerApplication {
    private static DemoApplication sApplication;

    public DemoApplication() {
        sApplication = this;
    }

    @Inject
    Picasso mPicasso;

    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.setSingletonInstance(mPicasso);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public static Context getAppContext() {
        return sApplication.getApplicationContext();
    }

    /**
     * Configure Picasso singleton instance.
     *
     * Picasso is that it automatically takes care of the request canceling, clearing of the ImageViews, and
     * loading the correct image into the appropriate ImageView.
     *
     * Picasso uses three sources: memory, disk and network (ordered from fastest to slowest). and
     * there is nothing we'll have to do.
     *
     * notebyweiyi: Create an OkHttp3Downloader instance wrapping your OkHttpClient and pass it to downloader to
     * fix image cannot download issue that com.squareup.picasso.Downloader$ResponseException: 301 TLS Redirect
     * https://github.com/JakeWharton/picasso2-okhttp3-downloader
     * https://github.com/square/picasso/issues/463
     *
     * However after .downloader(new OkHttp3Downloader(new OkHttpClient())),
     * Picasso not use Disk Cache anymore.
     * https://github.com/JakeWharton/picasso2-okhttp3-downloader/issues/12
     */
    // have been replaced by Dagger
    @Deprecated
    private void configurePicassoSingletonInstance() {
        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .loggingEnabled(true)
                .downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE))
                .listener((picasso1, uri, exception) -> exception.printStackTrace())
                .build();

        Picasso.setSingletonInstance(picasso);
    }
}
