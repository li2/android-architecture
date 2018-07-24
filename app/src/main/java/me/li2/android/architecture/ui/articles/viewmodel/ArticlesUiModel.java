package me.li2.android.architecture.ui.articles.viewmodel;

import java.util.List;

/**
 * Model stores view's states for the list of articles screen,
 * then we can use this model to update list view screen.
 *
 * @author Weiyi Li on 14/7/18 | https://github.com/li2
 */
public class ArticlesUiModel {

    private final boolean mIsArticlesListVisible;

    private final List<ArticleItem> mItemList;

    private final boolean mIsNoArticlesViewVisible;

    private final String mNoArticlesHint;

    public ArticlesUiModel(boolean isArticlesListVisible, List<ArticleItem> itemList, boolean isNoArticlesViewVisible, String noArticlesHint) {
        mIsArticlesListVisible = isArticlesListVisible;
        mItemList = itemList;
        mIsNoArticlesViewVisible = isNoArticlesViewVisible;
        mNoArticlesHint = noArticlesHint;
    }

    public boolean isArticlesListVisible() {
        return mIsArticlesListVisible;
    }

    public List<ArticleItem> getItemList() {
        return mItemList;
    }

    public boolean isNoArticlesViewVisible() {
        return mIsNoArticlesViewVisible;
    }

    public String getNoArticlesHint() {
        return mNoArticlesHint;
    }
}
