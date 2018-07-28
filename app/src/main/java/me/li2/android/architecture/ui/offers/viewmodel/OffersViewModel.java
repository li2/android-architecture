package me.li2.android.architecture.ui.offers.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import arch.NoNetworkException;
import arch.Resource;
import arch.Status;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Offer;
import me.li2.android.architecture.data.repository.OffersRepository;
import me.li2.android.architecture.ui.offers.view.OffersNavigator;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * ViewModel to expose states for the list of offers view, and handle all user actions.
 *
 * - Ask data from repository {@link OffersViewModel#mRepository}, and then
 *     construct the source data to view data which contains all UI state.
 *
 * - Expose streams for view to subscribe the UI state,
 *     such as {@link OffersViewModel#getUiModel()}, {@link OffersViewModel#getLoadingIndicatorVisibility()}
 *
 * - Expose public methods for view to handle user actions,
 *     such as force pull-refresh {@link OffersViewModel#forceUpdateOffers()}
 *
 * - For user action in the sub-view, such as click or check in the list view item,
 *      implement {@link Action} or {@link Consumer} in the ViewModel
 *      {@link OffersViewModel#handleOfferTaped(Offer, View)}, and then
 *      pass it as parameter to ViewHolder constructor {@link OfferItem)}.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class OffersViewModel extends ViewModel {

    private static final String TAG = OffersViewModel.class.getSimpleName();

    private static final OffersFilterType DEFAULT_OFFERS_FILTER_TYPE = OffersFilterType.ALL_OFFER;

    private static final String LOCATION_FORMAT = "%s, %s";
    private static final String NIGHTS_RANGE_FORMAT = "%d - %d Nights";
    private static final String OFFER_ENDS_IN_FORMAT = "OFFER ENDS IN %d DAYS";
    // TODO list item should not contains so many data {Offer}; 从数据库中选取部分字段填充 ListOffer
    // TODO $299/room $3499/pers 涉及货币单位、offer类型，最好在ViewModel中打包好传给 view
    private static final String HOTEL_PRICE_FROM_FORMAT = "";

    @NonNull
    private OffersRepository mRepository;

    @NonNull
    private BaseResourceProvider mResourceProvider;

    @NonNull
    private OffersNavigator mNavigator;

    private MutableLiveData<OffersFilterType> mFilter = new MutableLiveData<>();

    private MutableLiveData<Boolean> mLoadingIndicator = new MutableLiveData<>();

    private MutableLiveData<String> mSnackbarText = new MutableLiveData<>();

    public OffersViewModel(@NonNull OffersRepository repository,
                           @NonNull BaseResourceProvider resourceProvider,
                           @NonNull OffersNavigator navigator
                             ) {
        mRepository = repository;
        mResourceProvider = resourceProvider;
        mNavigator = navigator;
        mFilter.setValue(DEFAULT_OFFERS_FILTER_TYPE);
    }

    /**
     * Sets the current offers filtering type.
     */
    public void filter(OffersFilterType filter) {
        mFilter.setValue(filter);
    }

    /**
     * @return the model for the offers list screen.
     */
    @NonNull
    public LiveData<OffersUiModel> getUiModel() {
        /* notebyweiyi: switchMap means that the getOfferItems(filterType) method is called when mFilter changes.
         This mechanism allows lower levels of the app to create LiveData objects that are lazily calculated on demand.
         https://developer.android.com/topic/libraries/architecture/livedata */
        LiveData<Resource<List<OfferItem>>> filteredOfferItems  =
                Transformations.switchMap(mFilter, filterType -> getOfferItems(filterType));

        return Transformations.map(filteredOfferItems, resource -> {
                    mLoadingIndicator.setValue(resource.status == Status.LOADING);

                    if (resource.status == Status.ERROR) {
                        if (resource.throwable instanceof NoNetworkException) {
                            mSnackbarText.setValue(mResourceProvider.getString(R.string.status_no_connect));
                        } else {
                            // TODO display meaningful string to user
                            mSnackbarText.setValue(resource.errorMessage);
                        }
                    }

                    if (resource.data != null) {
                        return constructOffersUiModel(resource.data);
                    } else {
                        return null;
                    }
                });
    }

    /**
     * @return a stream of string ids that should be displayed in the snackbar.
     */
    @NonNull
    public LiveData<String> getSnackbarMessage() {
        return mSnackbarText;
    }

    /**
     * @return a stream that emits true if the progress indicator should be displayed, false otherwise.
     */
    @NonNull
    public LiveData<Boolean> getLoadingIndicatorVisibility() {
        return mLoadingIndicator;
    }

    /**
     * Convert {@link Resource<List<>>} of {@link Offer} (data model) to {@link OfferItem} (view data model)
     * @return
     */
    private LiveData<Resource<List<OfferItem>>> getOfferItems(OffersFilterType filterType) {
        return Transformations.map(mRepository.loadOffers(), resource ->
                new Resource<>(resource.status, constructOfferItemList(resource.data, filterType), resource.errorMessage, resource.code, resource.throwable));
    }

    private OffersUiModel constructOffersUiModel(List<OfferItem> offerItems) {
        boolean isOffersListVisible = !offerItems.isEmpty();
        boolean isNoOffersViewVisible = !isOffersListVisible;
        return new OffersUiModel(isOffersListVisible, offerItems,
                isNoOffersViewVisible, mResourceProvider.getString(R.string.no_offers_all));
    }

    private List<OfferItem> constructOfferItemList(List<Offer> offers, OffersFilterType filterType) {
        if (offers == null) {
            return null; // notebyweiyi: init as null coz the resource.data might be null
        }
        List<OfferItem> result = new ArrayList<>();
        for (Offer offer :  offers) {
            switch (filterType) {
                case HOTEL_OFFER:
                    if(offer.isHotel()) {
                        result.add(constructOfferItem(offer));
                    }
                    break;
                case TOUR_OFFER:
                    if (offer.isTour()) {
                        result.add(constructOfferItem(offer));
                    }
                    break;
                default:
                    result.add(constructOfferItem(offer));
                    break;
            }
        }
        return result;
    }

    private OfferItem constructOfferItem(final Offer offer) {
        return new OfferItem(
                offer.idSalesforceExternal,
                offer.images != null && offer.images.size() > 0 ? offer.images.get(0).cloudinaryId : null,
                offer.name,
                !Strings.isNullOrEmpty(offer.locationSubheading) ? String.format(LOCATION_FORMAT, offer.locationHeading, offer.locationSubheading) : offer.locationHeading,
                offer.isHotel(),
                offer.location,
                String.format(NIGHTS_RANGE_FORMAT, offer.minNumNights, offer.maxNumNights),
                OFFER_ENDS_IN_FORMAT,
                "0",
                "0",
                view -> handleOfferTaped(offer, view)
                );
    }

    private void handleOfferTaped(Offer offer, View sharedElement) {
        mNavigator.openOfferDetails(offer.idSalesforceExternal, sharedElement);
    }

    /**
     * Trigger a force update of the offers.
     */
    public Completable forceUpdateOffers() {
        mLoadingIndicator.setValue(true);
        // TODO
        return null;
    }
}
