
package me.li2.android.architecture.ui.articles.view;

import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.articledetail.view.ArticleDetailFragment;
import me.li2.android.architecture.utils.BaseNavigator;

/**
 * Defines the navigation actions that can be called from the articles list screen.
 */
public class ArticlesNavigator {

    @Inject
    BaseNavigator mNavigator;

    @Inject
    public ArticlesNavigator() {

    }

    /**
     * Open the details of an article.
     */
    public void openArticleDetails(int articleId, @NonNull View sharedElement) {
        mNavigator.addFragment(ArticleDetailFragment.newInstance(articleId), R.id.fragmentContainer, sharedElement);
    }
}
