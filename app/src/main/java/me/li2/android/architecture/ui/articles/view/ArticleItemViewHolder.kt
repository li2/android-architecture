package me.li2.android.architecture.ui.articles.view

import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.article_list_view_holder.view.*
import me.li2.android.architecture.ui.articles.viewmodel.ArticleItem
import me.li2.android.architecture.utils.BaseImageLoader
import me.li2.android.architecture.utils.ViewUtils

/**
 * Created by weiyi on 19/08/2018.
 * https://github.com/li2
 */
class ArticleItemViewHolder(private val mItemView: View, private val mImageLoader: BaseImageLoader)
    : RecyclerView.ViewHolder(mItemView) {

    private var mOnItemClickAction: Consumer<View>? = null

    init {
        itemView.setOnClickListener {
            mOnItemClickAction?.accept(mItemView.articleImageView)
        }
    }

    fun bindArticle(articleItem: ArticleItem) {
        mItemView.articleTitleView.text = articleItem.title
        mItemView.articleDescriptionView.text = articleItem.description
        mItemView.articleImageView.visibility = View.GONE
        mImageLoader.loadImage(mItemView.articleImageView, articleItem.imageHref, null) {
            succeed -> mItemView.articleImageView.visibility = if (succeed) View.VISIBLE else View.GONE
        }
        mOnItemClickAction = articleItem.onClickAction
        // shared element transition between RecyclerView and Fragment. notebyweiyi
        ViewUtils.setArticleTransitionName(mItemView.context, mItemView.articleImageView, articleItem.title)
    }
}
