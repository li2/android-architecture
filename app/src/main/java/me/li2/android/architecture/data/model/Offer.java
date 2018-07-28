package me.li2.android.architecture.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 *
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

    @SerializedName("locationHeading")
    public String locationHeading;

    @SerializedName("locationSubheading")
    public String locationSubheading;

    @SerializedName("maxNumNights")
    public int maxNumNights;

    @SerializedName("minNumNights")
    public int minNumNights;

    public boolean isHotel() {
        return type != null ? type.equals("hotel") : false;
    }

    public boolean isTour() {
        return type != null ? type.equals("tour") : false;
    }
}
