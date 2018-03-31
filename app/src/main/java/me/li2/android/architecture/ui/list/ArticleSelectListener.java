package me.li2.android.architecture.ui.list;

import android.view.View;

import me.li2.android.architecture.data.model.Article;

public interface ArticleSelectListener {
    void onArticleSelect(Article article, View sharedView, String sharedName);
}
