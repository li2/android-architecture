package me.li2.android.architecture.ui.articles.viewmodel

/**
 * A data class which contains all the UI state on articles list screen.
 *
 * @author Weiyi Li on 19/8/18 | https://github.com/li2
 */
class ArticlesUiModel(

    val isArticlesListVisible: Boolean,

    val itemList: List<ArticleItem>,

    val isNoArticlesViewVisible: Boolean,

    val noArticlesHint: String
)
