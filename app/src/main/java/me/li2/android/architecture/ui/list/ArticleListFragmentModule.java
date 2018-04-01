package me.li2.android.architecture.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.repository.DemoRepository;

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
public class ArticleListFragmentModule {

    @Provides
    ArticleListAdapter provideArticleListAdapter(Context context, ArticleListFragment fragment) {
        return new ArticleListAdapter(context, fragment);
    }

    @Provides
    ArticleListFragmentViewModel provideArticleListFragmentViewModel(ArticleListFragment fragment, ArticleListViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(ArticleListFragmentViewModel.class);
    }

    @Provides
    ArticleListViewModelFactory provideArticleListViewModelFactory(Context context, DemoRepository repository) {
        return new ArticleListViewModelFactory(repository);
    }
}
