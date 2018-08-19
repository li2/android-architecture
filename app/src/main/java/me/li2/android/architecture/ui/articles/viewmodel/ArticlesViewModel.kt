package me.li2.android.architecture.ui.articles.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.view.View
import arch.NoNetworkException
import arch.Resource
import arch.Status
import io.reactivex.Completable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import me.li2.android.architecture.R
import me.li2.android.architecture.data.model.Article
import me.li2.android.architecture.data.repository.ArticlesRepository
import me.li2.android.architecture.ui.articles.view.ArticlesNavigator
import me.li2.android.architecture.utils.BaseResourceProvider
import java.util.*

/**
 * ViewModel to expose states for the list of articles view, and handle all user actions.
 *
 * - Ask data from repository [ArticlesViewModel.mRepository], and then
 * construct the source data to view data which contains all UI state.
 *
 * - Expose streams for view to subscribe the UI state,
 * such as [ArticlesViewModel.getUiModel], [ArticlesViewModel.getLoadingIndicatorVisibility]
 *
 * - Expose public methods for view to handle user actions,
 * such as force pull-refresh [ArticlesViewModel.forceUpdateArticles]
 *
 * - For user action in the sub-view, such as click or check in the list view item,
 * implement [Action] or [Consumer] in the ViewModel
 * [ArticlesViewModel.handleArticleTaped], and then
 * pass it as parameter to ViewHolder constructor [ArticleItem.ArticleItem].
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */

private const val TAG = "ArticlesViewModel"

class ArticlesViewModel(private val mRepository: ArticlesRepository,
                        private val mResourceProvider: BaseResourceProvider,
                        private val mNavigator: ArticlesNavigator
) : ViewModel() {

    /**
     * Live stream emits true if the progress indicator should be displayed, false otherwise.
     */
    val loadingIndicator = MutableLiveData<Boolean>()

    /**
     * Live stream emits the string which should be displayed in the snackbar.
     */
    val snackbarMessage = MutableLiveData<String>()

    /**
     * Live stream emits the [ArticlesUiModel] which is the model for the articles list screen.
     */
    val uiModel: LiveData<ArticlesUiModel> =
            Transformations.map(getArticleItems()) { resource ->
                loadingIndicator.value = resource.status == Status.LOADING

                if (resource.status == Status.ERROR) {
                    if (resource.throwable is NoNetworkException) {
                        snackbarMessage.value = mResourceProvider.getString(R.string.status_no_connect)
                    } else {
                        Log.d(TAG, "Failed to get UI model : ${resource.errorMessage}")
                        snackbarMessage.value = resource.errorMessage
                    }
                }

                if (resource.data != null) {
                    constructArticlesUiModel(resource.data)
                } else {
                    null
                }
            }

    /**
     * Convert [Article] (raw data model) to [ArticleItem] (view data model)
     * @return
     */
    private fun getArticleItems(): LiveData<Resource<List<ArticleItem>>> =
            Transformations.map(mRepository.loadArticles()) { resource ->
                var articleItems: MutableList<ArticleItem>? = null
                if (resource.data != null) {
                    articleItems = ArrayList()
                    for (article in resource.data) {
                        articleItems.add(constructArticleItem(article))
                    }
                }
                Resource<List<ArticleItem>>(resource.status, articleItems, resource.errorMessage, resource.code, resource.throwable)
            }

    private fun constructArticlesUiModel(articleItems: List<ArticleItem>): ArticlesUiModel {
        val isArticlesListVisible = !articleItems.isEmpty()
        val isNoArticlesViewVisible = !isArticlesListVisible

        return ArticlesUiModel(
                isArticlesListVisible,
                articleItems,
                isNoArticlesViewVisible,
                mResourceProvider.getString(R.string.no_articles_all)
        )
    }

    /** Convert raw data from server to view data which contains all the UI state.
      because UI doesn't care too much details about the raw data. notebyweiyi  */
    private fun constructArticleItem(article: Article): ArticleItem {
        return ArticleItem(
                article.title,
                article.description,
                article.imageHref,
                Consumer { view -> handleArticleTaped(article, view) }
        )
    }

    private fun handleArticleTaped(article: Article, sharedElement: View) {
        mNavigator.openArticleDetails(article.id, sharedElement)
    }

    /**
     * Trigger a force update of the articles.
     */
    fun forceUpdateArticles(): Completable? {
        loadingIndicator.value = true
        // TODO
        return null
    }
}
