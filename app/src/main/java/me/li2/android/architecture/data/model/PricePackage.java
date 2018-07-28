package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class PricePackage {

    /**
     * idSalesforceExternal : a0s0I000001T07hQAC
     * name : CHRISTCHURCH ESCAPE
     * description : - **Inclusions valid for two adults**
     - Daily cooked buffet breakfast at Straits Café
     - NZ$25 food & beverage credit to share for use at any of the on-site food and beverage outlets or on room service during your stay
     - Valet parking
     - Leisurely late midday checkout

     * price : 299
     * value : 714
     * prices : [{"currency_code":"ZAR","price":2982,"value":7119},{"currency_code":"XPF","price":22540,"value":53817},{"currency_code":"VUV","price":23934,"value":57146},{"currency_code":"VND","price":5060789,"value":12083259},{"currency_code":"USD","price":220,"value":526},{"currency_code":"TWD","price":6755,"value":16129},{"currency_code":"THB","price":7364,"value":17582},{"currency_code":"SGD","price":301,"value":719},{"currency_code":"SAR","price":826,"value":1971},{"currency_code":"RUB","price":14017,"value":33468},{"currency_code":"QAR","price":802,"value":1914},{"currency_code":"PHP","price":11805,"value":28185},{"currency_code":"OMR","price":85,"value":202},{"currency_code":"NZD","price":326,"value":779},{"currency_code":"MYR","price":894,"value":2135},{"currency_code":"MOP","price":1781,"value":4252},{"currency_code":"KRW","price":249970,"value":596835},{"currency_code":"JPY","price":24736,"value":59060},{"currency_code":"INR","price":15178,"value":36238},{"currency_code":"ILS","price":802,"value":1914},{"currency_code":"IDR","price":3151098,"value":7523636},{"currency_code":"HKD","price":1728,"value":4126},{"currency_code":"GBP","price":169,"value":404},{"currency_code":"FJD","price":462,"value":1102},{"currency_code":"EUR","price":189,"value":451},{"currency_code":"DKK","price":1408,"value":3361},{"currency_code":"CZK","price":4897,"value":11693},{"currency_code":"CUC","price":220,"value":525},{"currency_code":"CNY","price":1490,"value":3558},{"currency_code":"CHF","price":220,"value":525},{"currency_code":"CAD","price":292,"value":697},{"currency_code":"AUD","price":299,"value":714},{"currency_code":"AED","price":808,"value":1929}]
     * trackingPrice : 299
     * qffBonusPoints : null
     * qffBonusDescription : null
     * hasQffBonusPoints : false
     * hasRoomType : true
     * hasProperty : true
     * hasPropertyReviews : true
     * hasTourOption : false
     * hasTour : false
     * numberOfNights : 2
     * numberOfDays : 0
     */

    @SerializedName("idSalesforceExternal")
    public String idSalesforceExternal;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("price")
    public int minPrice;

    @SerializedName("value")
    public int maxPrice;

    @SerializedName("trackingPrice")
    public int trackingPrice;

    @SerializedName("qffBonusPoints")
    public Object qffBonusPoints;

    @SerializedName("qffBonusDescription")
    public Object qffBonusDescription;

    @SerializedName("hasQffBonusPoints")
    public boolean hasQffBonusPoints;

    @SerializedName("hasRoomType")
    public boolean hasRoomType;

    @SerializedName("hasProperty")
    public boolean hasProperty;

    @SerializedName("hasPropertyReviews")
    public boolean hasPropertyReviews;

    @SerializedName("hasTourOption")
    public boolean hasTourOption;

    @SerializedName("hasTour")
    public boolean hasTour;

    @SerializedName("numberOfNights")
    public int numberOfNights;

    @SerializedName("numberOfDays")
    public int numberOfDays;

    @SerializedName("prices")
    public List<Price> prices;
}
