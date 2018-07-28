package me.li2.android.architecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.li2.android.architecture.ui.offerdetail.view.OfferDetailFragment;
import me.li2.android.architecture.ui.offers.view.OffersActivity;
import me.li2.android.architecture.ui.offers.view.OffersFragment;

/**
 * This is a given module to dagger.
 * We map ALL our activities here, then Dagger knows our activities in compile time.
 * Otherwise it causes IllegalArgumentException: No injector factory bound for Class
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = OfferDetailModule.class)
    abstract OfferDetailFragment bindOfferDetailFragment();

    @ContributesAndroidInjector()
    abstract OffersActivity bindOffersActivity();

    @ContributesAndroidInjector(modules = OffersModule.class)
    @OffersScope
    abstract OffersFragment bindOffersFragment();
}
