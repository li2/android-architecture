package me.li2.android.architecture.ui.articles.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.li2.android.architecture.R
import me.li2.android.architecture.ui.articles.viewmodel.ArticleItem
import me.li2.android.architecture.utils.ImageLoader
import javax.inject.Inject

/**
 * @author Weiyi Li on 6/5/18 | https://github.com/li2
 */

class ArticlesAdapter @Inject
constructor() : RecyclerView.Adapter<ArticleItemViewHolder>() {

    private var mArticles: List<ArticleItem> = ArrayList()

    @Inject
    lateinit var mImageLoader: ImageLoader

    fun refreshData(articles: List<ArticleItem>) {
        mArticles = articles
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mArticles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_view_holder, parent, false)
        return ArticleItemViewHolder(view, mImageLoader)
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.bindArticle(mArticles[position])
    }
}
