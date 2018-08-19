package me.li2.android.architecture.ui.articles.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.article_list_fragment.*
import me.li2.android.architecture.R
import me.li2.android.architecture.ui.articles.viewmodel.ArticleItem
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesUiModel
import me.li2.android.architecture.ui.articles.viewmodel.ArticlesViewModel
import me.li2.android.architecture.ui.widget.RecyclerViewMarginDecoration
import javax.inject.Inject

private const val BUNDLE_RECYCLER_POSITION = "recycler_position"

class ArticlesFragment : DaggerFragment() {

    @Inject
    lateinit var mAdapter: ArticlesAdapter

    @Inject
    lateinit var mViewModel: ArticlesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.article_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupArticlesListView()

        setupSwipeRefreshLayout()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(BUNDLE_RECYCLER_POSITION,
                (articlesListView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition())
    }

    override fun onResume() {
        super.onResume()

        mViewModel.uiModel.observe(this, Observer { uiModel -> updateView(uiModel) })

        mViewModel.loadingIndicator.observe(this, Observer { visible -> setLoadingIndicatorVisibility(visible!!) }) // TODO why Found String?

        mViewModel.snackbarMessage.observe(this, Observer { message -> showSnackBar(message!!) }) // TODO why Found String?
    }

    private fun updateView(uiModel: ArticlesUiModel?) {
        uiModel?.let {
            val articlesListVisibility = if (uiModel.isArticlesListVisible) View.VISIBLE else View.GONE
            val noArticlesViewVisibility = if (uiModel.isNoArticlesViewVisible) View.VISIBLE else View.GONE
            articlesListView.visibility = articlesListVisibility
            noArticlesContainer.visibility = noArticlesViewVisibility

            if (uiModel.isArticlesListVisible) {
                showArticlesList(uiModel.itemList)
            }
            if (uiModel.isNoArticlesViewVisible) {
                showNoArticlesView(uiModel.noArticlesHint)
            }
        }
    }

    private fun setupArticlesListView() {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        articlesListView.layoutManager = layoutManager
        articlesListView.isScrollContainer = false
        // notebyweiyi: setNestedScrollingEnabled true to allows your Toolbar and other views (such as tabs provided by TabLayout) to react to scroll events
        // [CoordinatorLayout and the app bar](https://android-developers.googleblog.com/2015/05/android-design-support-library.html)
        articlesListView.isNestedScrollingEnabled = false
        // setup RecyclerView item margin
        val margin = resources.getDimension(R.dimen.default_margin).toInt()
        articlesListView.addItemDecoration(RecyclerViewMarginDecoration(margin))
        // setup adapter
        articlesListView.adapter = mAdapter
    }

    private fun setupSwipeRefreshLayout() {
        articlesSwiperefreshLayout.setOnRefreshListener { mViewModel.forceUpdateArticles() }
    }

    private fun setLoadingIndicatorVisibility(active: Boolean) {
        articlesSwiperefreshLayout.isRefreshing = active
    }

    private fun showArticlesList(articles: List<ArticleItem>) {
        mAdapter.refreshData(articles)
    }

    private fun showNoArticlesView(hint: String) {
        noArticlesTitleView.text = hint
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }
}
