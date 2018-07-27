package me.li2.android.architecture.ui.offers.viewmodel;

import android.view.View;

import io.reactivex.functions.Consumer;
import me.li2.android.architecture.data.model.Offer;

/**
 * An offer that should be displayed as an item in a list of offers.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class OfferItem {

    private Offer mOffer;

    // shared element transition between RecyclerView and Fragment
    // list item click listen, accept the shared element view as para to perform transition later
    private Consumer<View> mOnClickAction;

    public OfferItem(Offer offer, Consumer<View> clickAction) {
        mOffer = offer;
        mOnClickAction = clickAction;
    }

    public Offer getOffer() {
        return mOffer;
    }

    /**
     * @return the action to be triggered on click events
     */
    public Consumer<View> getOnClickAction() {
        return mOnClickAction;
    }
}
