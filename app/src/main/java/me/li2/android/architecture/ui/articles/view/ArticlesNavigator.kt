package me.li2.android.architecture.ui.articles.view

import android.view.View
import me.li2.android.architecture.R
import me.li2.android.architecture.ui.articledetail.view.ArticleDetailFragment
import me.li2.android.architecture.utils.BaseNavigator
import javax.inject.Inject

/**
 * Defines the navigation actions that can be called from the articles list screen.
 */
class ArticlesNavigator @Inject
constructor() {

    @Inject
    lateinit var mNavigator: BaseNavigator

    /**
     * Open the details of an article.
     */
    fun openArticleDetails(articleId: Int, sharedElement: View) {
        mNavigator.addFragment(ArticleDetailFragment.newInstance(articleId), R.id.fragmentContainer, sharedElement)
    }
}
