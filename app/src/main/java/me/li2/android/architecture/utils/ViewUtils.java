package me.li2.android.architecture.utils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.View;

import me.li2.android.architecture.R;

/**
 * @author Weiyi Li on 15/7/18 | https://github.com/li2
 */
public class ViewUtils {

    public static void setOfferTransitionName(Context context, View sharedElement, String id) {
        String transitionName = context.getString(R.string.transition_offer_cover_image) + "." + id;
        ViewCompat.setTransitionName(sharedElement, transitionName);
    }
}
