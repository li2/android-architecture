package me.li2.android.architecture.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.articles.view.ArticlesFragment;
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
}
