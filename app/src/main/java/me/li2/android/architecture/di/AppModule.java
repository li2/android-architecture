package me.li2.android.architecture.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.repository.DemoRepository;
import me.li2.android.architecture.data.source.local.DemoDatabase;
import me.li2.android.architecture.data.source.remote.DemoWebService;
import me.li2.android.architecture.data.source.remote.WebServiceGenerator;
import me.li2.android.architecture.utils.AppExecutors;

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
    DemoRepository provideRepository(Context context) {
        DemoDatabase database = DemoDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        DemoWebService demoWebService = WebServiceGenerator.createService(DemoWebService.class);
        return DemoRepository.getInstance(context, database.articleDao(), demoWebService, executors);
    }
}
