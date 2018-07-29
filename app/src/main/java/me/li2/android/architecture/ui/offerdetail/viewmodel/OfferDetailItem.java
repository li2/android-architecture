package me.li2.android.architecture.ui.offerdetail.viewmodel;

import android.util.ArrayMap;

import io.reactivex.functions.Action;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class OfferDetailItem {

    public String photoCloudinaryId;

    public String name;

    public String description;

    public String location;

    public String minPrice;

    public String maxPrice;

    public Action onShareAction;

    /* notebyweiyi: tour has json member "itinerary", hotel has json member "facilities"
       the common parts for tour & hotel are highlights, finePrint, gettingThere.
       store them in ArrayMap<Title, Content> */
    public ArrayMap<String, String> expandableContent;

    public OfferDetailItem(String photoCloudinaryId,
                           String name,
                           String description,
                           String location,
                           String minPrice,
                           String maxPrice,
                           Action onShareAction,
                           ArrayMap<String, String> expandableContent) {
        this.photoCloudinaryId = photoCloudinaryId;
        this.name = name;
        this.description = description;
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.onShareAction = onShareAction;
        this.expandableContent = expandableContent;
    }
}
