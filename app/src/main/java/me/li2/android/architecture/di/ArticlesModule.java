package me.li2.android.architecture.di;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.articles.view.ArticlesContract;
import me.li2.android.architecture.ui.articles.view.ArticlesFragment;
import me.li2.android.architecture.ui.articles.view.ArticlesPresenter;
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesViewModel;
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesViewModelFactory;
import me.li2.android.architecture.utils.BaseNavigator;
import me.li2.android.architecture.utils.Navigator;

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
public class ArticlesModule {

    @Provides
    ArticlesViewModel provideArticlesViewModel(ArticlesFragment fragment, ArticlesViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(ArticlesViewModel.class);
    }

    // Fix BaseNavigator cannot be provided without an @Provides-annotated method.
    @Provides
    @ArticlesScope
    BaseNavigator provideNavigator(ArticlesFragment fragment) {
        return new Navigator((AppCompatActivity) fragment.getActivity());
    }

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
