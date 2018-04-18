package me.li2.android.architecture.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
public class ArticleListFragmentModule {

    @Provides
    ArticleListFragment.ArticlesAdapter provideArticleListAdapter(Context context, ArticleListFragment fragment) {
        return fragment.new ArticlesAdapter(context, fragment);
    }

    @Provides
    ArticleListFragmentViewModel provideArticleListFragmentViewModel(ArticleListFragment fragment, ArticleListViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(ArticleListFragmentViewModel.class);
    }

    /*
    error: ArticlesContract.Presenter cannot be provided without an @Provides- or @Produces-annotated method.
    the reason is that we do this in fragment, however, this is a interface which cannot use @Inject.
        @Inject
        ArticlesContract.Presenter mPresenter;
     */
    @Provides
    ArticlesContract.Presenter provideArticlesPresenter(ArticlesPresenter presenter) {
        return presenter;
    }

    @Provides
    ArticlesContract.View provideArticlesView(ArticleListFragment fragment) {
        return fragment;
    }

    @Provides
    LifecycleOwner provideArticlesLifecycleOwner(ArticleListFragment fragment) {
        return fragment;
    }
}
