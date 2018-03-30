package me.li2.android.architecture.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;

/**
 * Created by weiyi on 15/02/2018.
 * https://github.com/li2
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private Context mContext;
    private List<Article> mArticleList;

    public ArticleListAdapter(Context context) {
        mContext = context;
    }

    public void update(List<Article> articleList) {
        mArticleList = articleList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mArticleList != null) ? mArticleList.size() : 0;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_list_view_holder, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bindArticle(mArticleList.get(position));
    }
}
