package me.li2.android.architecture.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import me.li2.android.architecture.ui.offers.view.OffersFragment;
import me.li2.android.architecture.ui.offers.viewmodel.OffersViewModel;
import me.li2.android.architecture.ui.offers.viewmodel.OffersViewModelFactory;
import me.li2.android.architecture.utils.BaseNavigator;
import me.li2.android.architecture.utils.Navigator;

/**
 * This module provides the fragment related instances.
 *
 * @author Weiyi Li on 1/4/18 | https://github.com/li2
 */

@Module
public class OffersModule {
    @Provides
    OffersViewModel provideOffersViewModel(OffersFragment fragment, OffersViewModelFactory factory) {
        return ViewModelProviders.of(fragment, factory).get(OffersViewModel.class);
    }

    @Provides
    @OffersScope
    BaseNavigator provideNavigator(OffersFragment fragment) {
        return new Navigator((AppCompatActivity) fragment.getActivity());
    }
}
