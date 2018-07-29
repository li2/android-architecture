package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import me.li2.android.architecture.utils.DateUtils;

/**
 * Created by weiyi on 29/7/18.
 * https://github.com/li2
 */
public class VisibilitySchedule {

    /**
     * id : 3937
     * offer_id_salesforce_external : 0060I00000UJ3cyQAD
     * start : 2018-07-23T06:20:22.000Z
     * end : 2018-07-30T07:20:00.000Z
     * type : list_visibility
     * region : world
     * brand : luxuryescapes
     */

    @SerializedName("id")
    public int id;

    @SerializedName("offer_id_salesforce_external")
    public String offerIdSalesforceExternal;

    @SerializedName("start")
    public Date start;

    @SerializedName("end")
    public Date end;

    @SerializedName("type")
    public String type;

    @SerializedName("region")
    public String region;

    @SerializedName("brand")
    public String brand;

    public VisibilitySchedule(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public long getRemainingDays() {
        return DateUtils.getDaysBetween(new Date(), end);
    }
}
