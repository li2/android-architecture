package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 *
 * https://jsonformatter.org/
 * view-source:https://luxuryescapes.com/au/offer/rendezvous-hotel-christchurch-new-zealand/0060I00000UJ3cyQAD
 */
public class Offer {
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

    @SerializedName("images")
    public ArrayList<BannerImage> mImages;

    @SerializedName("locationHeading")
    public String locationHeading;

    @SerializedName("locationSubheading")
    public String locationSubheading;

    @SerializedName("maxNumNights")
    public int maxNumNights;

    @SerializedName("minNumNights")
    public int minNumNights;

    public Offer(String bookingType,
                 String type,
                 String name,
                 String location,
                 String description,
                 String highlights,
                 String facilities,
                 String finePrint,
                 String gettingThere,
                 String bookingGuarantee,
                 String runDate,
                 String endDate,
                 String locationHeading,
                 String locationSubheading,
                 int maxNumNights,
                 int minNumNights
    ) {
        this.bookingType = bookingType;
        this.type = type;
        this.name = name;
        this.location = location;
        this.description = description;
        this.highlights = highlights;
        this.facilities = facilities;
        this.finePrint = finePrint;
        this.gettingThere = gettingThere;
        this.bookingGuarantee = bookingGuarantee;
        this.runDate = runDate;
        this.endDate = endDate;
        this.locationHeading = locationHeading;
        this.locationSubheading = locationSubheading;
        this.maxNumNights = maxNumNights;
        this.minNumNights = minNumNights;
    }
}
