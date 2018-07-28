package me.li2.android.architecture.ui.offers.viewmodel;

import android.view.View;

import io.reactivex.functions.Consumer;

/**
 * An offer that should be displayed as an item in a list of offers.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class OfferItem {

    public String idSalesforceExternal;

    public String photoCloudinaryId;

    public String name;

    public String location;

    public boolean isHotelVisible;

    public String hotelLocation;

    public String hotelNightsRange;

    public String offerEnds;

    public String priceFrom;

    public String priceUpto;

    // shared element transition between RecyclerView and Fragment
    // list item click listen, accept the shared element view as para to perform transition later
    public Consumer<View> onClickAction;

    public OfferItem(String idSalesforceExternal, String photoCloudinaryId, String name, String location, boolean isHotelVisible, String hotelLocation, String hotelNightsRange, String offerEnds, String priceFrom, String priceUpto, Consumer<View> onClickAction) {
        this.idSalesforceExternal = idSalesforceExternal;
        this.photoCloudinaryId = photoCloudinaryId;
        this.name = name;
        this.location = location;
        this.isHotelVisible = isHotelVisible;
        this.hotelLocation = hotelLocation;
        this.hotelNightsRange = hotelNightsRange;
        this.offerEnds = offerEnds;
        this.priceFrom = priceFrom;
        this.priceUpto = priceUpto;
        this.onClickAction = onClickAction;
    }
}
