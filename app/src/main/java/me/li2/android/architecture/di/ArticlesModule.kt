package me.li2.android.architecture.di

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

import dagger.Module
import dagger.Provides
import me.li2.android.architecture.ui.articles.view.ArticlesFragment
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesViewModel
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesViewModelFactory
import me.li2.android.architecture.utils.BaseNavigator
import me.li2.android.architecture.utils.Navigator

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
class ArticlesModule {
    @Provides
    internal fun provideArticlesViewModel(fragment: ArticlesFragment, factory: ArticlesViewModelFactory): ArticlesViewModel {
        return ViewModelProviders.of(fragment, factory).get(ArticlesViewModel::class.java)
    }

    @Provides
    @ArticlesScope
    internal fun provideNavigator(fragment: ArticlesFragment): BaseNavigator {
        return Navigator(fragment.activity as AppCompatActivity)
    }
}
