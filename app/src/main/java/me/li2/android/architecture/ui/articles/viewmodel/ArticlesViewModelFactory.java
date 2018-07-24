package me.li2.android.architecture.ui.articles.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.li2.android.architecture.data.repository.ArticlesRepository;
import me.li2.android.architecture.ui.articles.view.ArticlesNavigator;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * Created by weiyi on 24/7/18.
 * https://github.com/li2
 *
 * TODO why cannot inject directly in the ViewModel
 */
public class ArticlesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Inject
    ArticlesRepository mRepository;

    @NonNull
    @Inject
    ArticlesNavigator mNavigator;

    @NonNull
    @Inject
    BaseResourceProvider mResourceProvider;

    @Inject
    public ArticlesViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArticlesViewModel(mRepository, mResourceProvider, mNavigator);
    }
}
