package me.li2.android.architecture.ui.offers.viewmodel;

import android.view.View;

import io.reactivex.functions.Consumer;
import me.li2.android.architecture.data.model.Article;

/**
 * An article that should be displayed as an item in a list of articles.
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class OfferItem {

    private Article mArticle;

    // shared element transition between RecyclerView and Fragment
    // list item click listen, accept the shared element view as para to perform transition later
    private Consumer<View> mOnClickAction;

    public OfferItem(Article article, Consumer<View> clickAction) {
        mArticle = article;
        mOnClickAction = clickAction;
    }

    public Article getArticle() {
        return mArticle;
    }

    /**
     * @return the action to be triggered on click events
     */
    public Consumer<View> getOnClickAction() {
        return mOnClickAction;
    }
}
