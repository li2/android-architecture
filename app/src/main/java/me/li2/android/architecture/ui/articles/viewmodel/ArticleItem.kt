package me.li2.android.architecture.ui.articles.viewmodel

import android.view.View
import io.reactivex.functions.Consumer

/**
 * An article item which contains all the UI state to be displayed as an item in a list of articles.
 *
 * @author Weiyi Li on 19/8/18 | https://github.com/li2
 */
data class ArticleItem(
        
    val title: String?,

    val description: String?,

    val imageHref: String?,

    /**
     * list item click listen, accept the shared element view as para to perform transition later
     * @param View shared element transition between RecyclerView and Fragment
     * @return the action to be triggered on click events
     */
    val onClickAction: Consumer<View>
)
