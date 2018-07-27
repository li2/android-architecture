package me.li2.android.architecture.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weiyi on 27/7/18.
 * https://github.com/li2
 *
 * https://res.cloudinary.com/lux-group/image/upload/f_auto,fl_progressive,q_auto:eco,c_fill,g_auto,w_1920,ar_16:9/sxc8sdjm44qa9muxgrko
 */
class BannerImage {
    /**
     * id : c49c6980-8bd5-11e8-a054-f31b62f324dd
     * cloudinaryId : sxc8sdjm44qa9muxgrko
     * order : 1
     */
    @SerializedName("id")
    public String id;
    @SerializedName("cloudinaryId")
    public String cloudinaryId;
    @SerializedName("order")
    public int order;
}
