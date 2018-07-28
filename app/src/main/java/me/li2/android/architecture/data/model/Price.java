package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class Price {
    /**
     * currency_code : ZAR
     * price : 2982
     * value : 7119
     */

    @SerializedName("currency_code")
    public String currencyCode;

    @SerializedName("price")
    public int price;

    @SerializedName("value")
    public int value;
}
