package me.li2.android.architecture.di;

import android.arch.lifecycle.LifecycleOwner;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.articles.view.ArticlesContract;
import me.li2.android.architecture.ui.articles.view.ArticlesFragment;
import me.li2.android.architecture.ui.articles.view.ArticlesPresenter;

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
public class ArticlesModule {

    /**
     * Provide dependency for interface.
     * Interface cannot be annotated with @Inject, otherwise it will cause
     * error: ArticlesContract.Presenter cannot be provided without an @Provides- or @Produces-annotated method.
     */
    @Provides
    @ArticlesScope
    ArticlesContract.Presenter provideArticlesPresenter(ArticlesPresenter presenter) {
        return presenter;
    }

    @Provides
    ArticlesContract.View provideArticlesView(ArticlesFragment fragment) {
        return fragment;
    }

    @Provides
    LifecycleOwner provideArticlesLifecycleOwner(ArticlesFragment fragment) {
        return fragment;
    }
}
