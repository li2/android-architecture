package me.li2.android.architecture.di;

import android.arch.lifecycle.ViewModelProviders;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.articledetail.view.ArticleDetailActivity;
import me.li2.android.architecture.ui.articledetail.viewmodel.ArticleDetailViewModel;
import me.li2.android.architecture.ui.articledetail.viewmodel.ArticleDetailViewModelFactory;

/**
 * This module provides the activity related instances.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public class ArticleDetailModule {

    @Provides
    ArticleDetailViewModel provideArticleDetailViewModel(ArticleDetailActivity activity, ArticleDetailViewModelFactory factory) {
        return ViewModelProviders.of(activity, factory).get(ArticleDetailViewModel.class);
    }
}
