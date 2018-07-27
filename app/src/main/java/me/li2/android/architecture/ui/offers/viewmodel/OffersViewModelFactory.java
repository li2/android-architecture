package me.li2.android.architecture.ui.offers.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.li2.android.architecture.data.repository.OffersRepository;
import me.li2.android.architecture.ui.offers.view.OffersNavigator;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * Created by weiyi on 24/7/18.
 * https://github.com/li2
 *
 * TODO why cannot inject directly in the ViewModel
 */
public class OffersViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Inject
    OffersRepository mRepository;

    @NonNull
    @Inject
    OffersNavigator mNavigator;

    @NonNull
    @Inject
    BaseResourceProvider mResourceProvider;

    @Inject
    public OffersViewModelFactory() {

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OffersViewModel(mRepository, mResourceProvider, mNavigator);
    }
}
