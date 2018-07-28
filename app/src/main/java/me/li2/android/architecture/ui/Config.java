package me.li2.android.architecture.ui;

/**
 * Created by weiyi on 28/7/18.
 * https://github.com/li2
 */
public class Config {

    private static final String BASE_URL = "https://res.cloudinary.com/lux-group/image/upload/f_auto,fl_progressive,q_auto:eco,c_fill,g_auto,w_1920,ar_16:9/";

    public static String photoUrl(String cloudinaryId) {
        return BASE_URL + cloudinaryId;
    }

}
