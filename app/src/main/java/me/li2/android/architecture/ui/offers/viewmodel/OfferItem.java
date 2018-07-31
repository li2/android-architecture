package me.li2.android.architecture.ui.offers.viewmodel;

import android.view.View;

import io.reactivex.functions.Consumer;

/**
 * An offer that should be displayed as an item in a list of offers.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class OfferItem {

    public final String idSalesforceExternal;

    public final String photoCloudinaryId;

    public final String name;

    public final String location;

    public final boolean isHotelVisible;

    public final String hotelLocation;

    public final String hotelNightsRange;

    public final String offerEnds;

    public final String minPrice;

    public final String maxPrice;

    /** shared element transition between RecyclerView and Fragment
     list item click listen, accept the shared element view as para to perform transition later */
    public final Consumer<View> onClickAction;

    public OfferItem(String idSalesforceExternal, String photoCloudinaryId, String name, String location, boolean isHotelVisible, String hotelLocation, String hotelNightsRange, String offerEnds, String minPrice, String maxPrice, Consumer<View> onClickAction) {
        this.idSalesforceExternal = idSalesforceExternal;
        this.photoCloudinaryId = photoCloudinaryId;
        this.name = name;
        this.location = location;
        this.isHotelVisible = isHotelVisible;
        this.hotelLocation = hotelLocation;
        this.hotelNightsRange = hotelNightsRange;
        this.offerEnds = offerEnds;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.onClickAction = onClickAction;
    }
}
