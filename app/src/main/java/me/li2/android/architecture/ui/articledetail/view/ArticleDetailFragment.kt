package me.li2.android.architecture.ui.articledetail.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.transition.TransitionInflater
import android.view.View
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.article_detail_fragment.*
import me.li2.android.architecture.R
import me.li2.android.architecture.data.model.Article
import me.li2.android.architecture.ui.articledetail.viewmodel.ArticleDetailViewModel
import me.li2.android.architecture.ui.basic.BaseFragment
import me.li2.android.architecture.utils.BaseImageLoader
import javax.inject.Inject

private const val EXTRA_ARTICLE_ID = "article_id"

fun newArticleDetailFragmentInstance(articleId: Int): ArticleDetailFragment {
    val args = Bundle()
    args.putInt(EXTRA_ARTICLE_ID, articleId)
    val fragment = ArticleDetailFragment()
    fragment.arguments = args
    return fragment
}

fun setArticleTransitionName(context: Context, sharedElement: View, id: String?) {
    val transitionName = context.getString(R.string.transition_article_cover_image) + "." + id
    ViewCompat.setTransitionName(sharedElement, transitionName)
}


class ArticleDetailFragment : BaseFragment() {

    override val layout: Int = R.layout.article_detail_fragment

    @Inject
    lateinit var mViewModel: ArticleDetailViewModel

    @Inject
    lateinit var mImageLoader: BaseImageLoader

    private val articleId: Int
        get() {
            return if (arguments != null) arguments!!.getInt(EXTRA_ARTICLE_ID) else -1
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // shared element transition between RecyclerView and Fragment
        // tell the Fragment to wait to load until we tell it
        postponeEnterTransition()

        // tell it what type of Transition we want
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }

    override fun onResume() {
        super.onResume()

        mViewModel.getArticle(articleId).observe(this, Observer { article ->
            article?.let {
                bindArticle(it)
            }
        })
    }

    private fun bindArticle(article: Article) {
        articleTitleView.text = article.title
        articleDescriptionView.text = article.description
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        setArticleTransitionName(context!!, articleImageView, article.title)
        mImageLoader.loadImage(articleImageView, article.imageHref, null, Consumer { startPostponedEnterTransition() })
    }
}
