package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class Price {
    /**
     * currency_code : AUD
     * price : 2982
     * value : 7119
     */

    @SerializedName("currency_code")
    public String currencyCode;

    @SerializedName("price")
    public int min;

    @SerializedName("value")
    public int max;

    public Price(String currencyCode, int min, int max) {
        this.currencyCode = currencyCode;
        this.min = min;
        this.max = max;
    }
}
