package me.li2.android.architecture.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.data.repository.DemoRepository;

/**
 * This module provides the activity related instances.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public class ArticleDetailActivityModule {

    @Provides
    ArticleDetailViewModel provideArticleDetailViewModel(ArticleDetailActivity activity, ArticleDetailViewModelFactory factory) {
        return ViewModelProviders.of(activity, factory).get(ArticleDetailViewModel.class);
    }
}
