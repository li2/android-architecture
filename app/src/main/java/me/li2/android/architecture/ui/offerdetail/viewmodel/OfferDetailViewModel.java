package me.li2.android.architecture.ui.offerdetail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.model.Price;
import me.li2.android.architecture.data.repository.OffersRepository;
import me.li2.android.architecture.ui.offers.view.OffersNavigator;
import me.li2.android.architecture.ui.offers.viewmodel.RegionType;
import me.li2.android.architecture.utils.ResourceProvider;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class OfferDetailViewModel extends ViewModel {

    @NonNull
    private OffersRepository mRepository;

    @NonNull
    private ResourceProvider mResourceProvider;

    @NonNull
    private OffersNavigator mNavigator;

    public OfferDetailViewModel(@NonNull OffersRepository repository,
                                @NonNull ResourceProvider resourceProvider,
                                @NonNull OffersNavigator navigator) {
        mRepository = repository;
        mResourceProvider = resourceProvider;
        mNavigator = navigator;
    }

    /**
     * Convert {@link Offer} (data model) to {@link OfferDetailItem} (view data model)
     * @return
     */
    public LiveData<OfferDetailItem> getOfferDetailItem(String id) {
        return Transformations.map(mRepository.loadOffer(id), offer -> constructOfferDetailItem(offer));
    }

    private OfferDetailItem constructOfferDetailItem(Offer offer) {
        String currencyCode = RegionType.AUD.name(); // TODO remove hardcoding
        Price lowestPrice = offer.getLowestPrice(currencyCode);

        ArrayMap<String, String> expandableContent = new ArrayMap<>();
        expandableContent.put(mResourceProvider.getString(R.string.offer_highlights), offer.highlights);
        expandableContent.put(mResourceProvider.getString(R.string.offer_finePrint), offer.finePrint);
        expandableContent.put(mResourceProvider.getString(R.string.offer_gettingThere), offer.gettingThere);

        return new OfferDetailItem(
                offer.getBannerCloudinaryIds(),
                offer.name,
                offer.description,
                offer.getLocationName(),
                mResourceProvider.minPackagePrice(currencyCode, lowestPrice.min, offer.isHotel()),
                mResourceProvider.maxPackagePrice(currencyCode, lowestPrice.max),
                () -> handleShareClicked(),
                expandableContent
        );
    }

    private void handleShareClicked() {

    }
}
