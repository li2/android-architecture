package me.li2.android.architecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.li2.android.architecture.ui.offerdetail.view.OfferDetailFragment;
import me.li2.android.architecture.ui.offers.view.OffersActivity;
import me.li2.android.architecture.ui.offers.view.OffersFragment;
import me.li2.android.architecture.ui.widget.PhotoFragment;
import me.li2.android.architecture.ui.widget.PhotosActivity;

/**
 * This is a given module to dagger.
 * We map ALL our activities here, then Dagger knows our activities in compile time.
 * Otherwise it causes IllegalArgumentException: No injector factory bound for Class
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    @OffersScope
    abstract OffersActivity bindOffersActivity();

    @ContributesAndroidInjector(modules = OffersModule.class)
    @OffersScope
    abstract OffersFragment bindOffersFragment();

    @ContributesAndroidInjector(modules = OfferDetailModule.class)
    @OfferDetailScope
    abstract OfferDetailFragment bindOfferDetailFragment();

    // IllegalArgumentException: No injector factory bound for this Activity notebyweiyi
    @ContributesAndroidInjector()
    abstract PhotosActivity bindPhotosActivity();

    @ContributesAndroidInjector()
    abstract PhotoFragment bindPhotoFragment();
}
