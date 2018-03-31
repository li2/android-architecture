package me.li2.android.architecture.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import me.li2.android.architecture.app.DemoApplication;

/**
 * Android apps have one application class. That is why we have one application component.
 * This component is responsible for providing application scope instances (eg. OkHttp, Database, SharedPrefs.).
 * This Component is root of our dagger graph. Application component is providing 3 module in our app:
 *
 *     - {@link AndroidInjectionModule} We didn't create this. It is an internal class in Dagger 2.10.
 *           Provides our activities and fragments with given module,
 *           to ensure that all bindings necessary for these base types are available.
 *
 *     - {@link ActivityBuilder} We created this module and map all our activities here.
 *
 *     - {@link AppModule} We created this module to provide retrofit, persistence db, shared pref etc here.
 *
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(DemoApplication app);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
}
