package me.li2.android.architecture.utils;

import android.content.Context;

import me.li2.android.architecture.data.source.local.DemoDatabase;
import me.li2.android.architecture.data.source.remote.WebServiceGenerator;
import me.li2.android.architecture.data.source.remote.DemoWebService;
import me.li2.android.architecture.data.repository.DemoRepository;
import me.li2.android.architecture.ui.list.ArticleListViewModelFactory;

/**
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class InjectorUtils {

    public static DemoRepository provideRepository(Context context) {
        DemoDatabase database = DemoDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        DemoWebService demoWebService = WebServiceGenerator.createService(DemoWebService.class);
        return DemoRepository.getInstance(context, database.articleDao(), demoWebService, executors);
    }

    public static ArticleListViewModelFactory provideArticleListViewModelFactory(Context context) {
        DemoRepository repository = provideRepository(context.getApplicationContext());
        return new ArticleListViewModelFactory(repository);
    }
}
