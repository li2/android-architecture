package me.li2.android.architecture.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 *
 * https://li2.gitbooks.io/android-programming-journey/content/assets/demo-le-offers.json
 * https://jsonformatter.org/
 * view-source:https://luxuryescapes.com/au/offer/rendezvous-hotel-christchurch-new-zealand/0060I00000UJ3cyQAD
 */
@Entity(tableName = "OfferTable", indices = {@Index(value = {"idSalesforceExternal"}, unique = true)})
public class Offer {

    @NonNull
    @PrimaryKey
    @SerializedName("idSalesforceExternal")
    public String idSalesforceExternal;

    @SerializedName("bookingType")
    public String bookingType;

    @SerializedName("type")
    public String type;

    @SerializedName("name")
    public String name;

    /** it's hotel location */
    @SerializedName("location")
    public String location;

    @SerializedName("description")
    public String description;

    @SerializedName("highlights")
    public String highlights;

    @SerializedName("facilities")
    public String facilities;

    @SerializedName("finePrint")
    public String finePrint;

    @SerializedName("gettingThere")
    public String gettingThere;

    @SerializedName("bookingGuarantee")
    public String bookingGuarantee;

    @SerializedName("runDate")
    public String runDate;

    @SerializedName("endDate")
    public String endDate;

    @SerializedName("lowestPricePackage")
    public PricePackage lowestPricePackage;

    @SerializedName("images")
    public List<BannerImage> images;

    @SerializedName("visibilitySchedules")
    public VisibilitySchedule visibilitySchedule;

    /** Destination location */
    @SerializedName("locationHeading")
    public String locationHeading;

    @SerializedName("locationSubheading")
    public String locationSubheading;

    @SerializedName("maxNumNights")
    public int maxNumNights;

    @SerializedName("minNumNights")
    public int minNumNights;


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean isHotel() {
        return type != null ? type.equals("hotel") : false;
    }

    public boolean isTour() {
        return type != null ? type.equals("tour") : false;
    }

    /**
     * Get price of lowest package.
     * @param currencyCode an ISO 4217 3-letter code
     * @return Price which match the currency code
     */
    @Nonnull
    public Price getLowestPrice(String currencyCode) {
        if (lowestPricePackage != null && lowestPricePackage.prices != null) { // NullPointerException found by UT notebyweiyi
            for (Price price : lowestPricePackage.prices) {
                if (currencyCode.equals(price.currencyCode)) {
                    return price;
                }
            }
        }
        return new Price(currencyCode, -1, -1);
    }

    @Nullable
    public String getFirstBannerImageCloudinaryId() {
        return images != null && images.size() > 0 ? images.get(0).cloudinaryId : null;
    }

    public List<String> getBannerCloudinaryIds() {
        List<String> result = new ArrayList<>();
        if (images != null) {
            for (BannerImage bannerImage : images) {
                result.add(bannerImage.cloudinaryId);
            }
        }
        return result;
    }

    /** Get destination location */
    @Nonnull
    public String getLocationName() {
        return !Strings.isNullOrEmpty(locationSubheading)
                ? (locationHeading + ", " + locationSubheading)
                : locationHeading;
    }

    public long getScheduleRemainingDays() {
        return visibilitySchedule != null ? visibilitySchedule.getRemainingDays() : 0;
    }
}
