package me.li2.android.architecture.ui.articledetail.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.ArticlesRepository;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class ArticleDetailViewModel extends ViewModel {

    private ArticlesRepository mRepository;

    public ArticleDetailViewModel(ArticlesRepository repository) {
        mRepository = repository;
    }

    public LiveData<Article> getArticle(int id) {
        return mRepository.loadArticle(id);
    }
}
