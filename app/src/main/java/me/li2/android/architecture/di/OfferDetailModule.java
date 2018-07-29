package me.li2.android.architecture.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.offerdetail.view.OfferDetailFragment;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailViewModel;
import me.li2.android.architecture.ui.offerdetail.viewmodel.OfferDetailViewModelFactory;
import me.li2.android.architecture.utils.BaseNavigator;
import me.li2.android.architecture.utils.Navigator;

/**
 * This module provides the activity related instances.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public class OfferDetailModule {
    @Provides
    OfferDetailViewModel provideOfferDetailViewModel(OfferDetailFragment fragment, OfferDetailViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(OfferDetailViewModel.class);
    }

    @Provides
    @OfferDetailScope
    BaseNavigator provideNavigator(OfferDetailFragment fragment) {
        return new Navigator((AppCompatActivity) fragment.getActivity());
    }
}
