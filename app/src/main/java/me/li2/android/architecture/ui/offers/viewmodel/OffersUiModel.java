package me.li2.android.architecture.ui.offers.viewmodel;

import java.util.List;

/**
 * Model stores view's states for the list of articles screen,
 * then we can use this model to update list view screen.
 *
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public class OffersUiModel {

    private final boolean mIsOffersListVisible;

    private final List<OfferItem> mItemList;

    private final boolean mIsNoOffersViewVisible;

    private final String mNoOffersHint;

    public OffersUiModel(boolean isOffersListVisible, List<OfferItem> itemList, boolean isNoOffersViewVisible, String noOffersHint) {
        mIsOffersListVisible = isOffersListVisible;
        mItemList = itemList;
        mIsNoOffersViewVisible = isNoOffersViewVisible;
        mNoOffersHint = noOffersHint;
    }

    public boolean isOffersListVisible() {
        return mIsOffersListVisible;
    }

    public List<OfferItem> getItemList() {
        return mItemList;
    }

    public boolean isNoOffersViewVisible() {
        return mIsNoOffersViewVisible;
    }

    public String getNoOffersHint() {
        return mNoOffersHint;
    }
}
