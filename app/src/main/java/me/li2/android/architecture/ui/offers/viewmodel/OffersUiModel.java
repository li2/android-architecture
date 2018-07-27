package me.li2.android.architecture.ui.offers.viewmodel;

import java.util.List;

/**
 * Model stores view's states for the list of articles screen,
 * then we can use this model to update list view screen.
 *
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public class OffersUiModel {

    private final boolean mIsArticlesListVisible;

    private final List<OfferItem> mItemList;

    private final boolean mIsNoArticlesViewVisible;

    private final String mNoArticlesHint;

    public OffersUiModel(boolean isArticlesListVisible, List<OfferItem> itemList, boolean isNoArticlesViewVisible, String noArticlesHint) {
        mIsArticlesListVisible = isArticlesListVisible;
        mItemList = itemList;
        mIsNoArticlesViewVisible = isNoArticlesViewVisible;
        mNoArticlesHint = noArticlesHint;
    }

    public boolean isArticlesListVisible() {
        return mIsArticlesListVisible;
    }

    public List<OfferItem> getItemList() {
        return mItemList;
    }

    public boolean isNoArticlesViewVisible() {
        return mIsNoArticlesViewVisible;
    }

    public String getNoArticlesHint() {
        return mNoArticlesHint;
    }
}
