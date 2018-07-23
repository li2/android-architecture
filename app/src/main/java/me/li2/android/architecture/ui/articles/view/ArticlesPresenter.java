package me.li2.android.architecture.ui.articles.view;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import arch.NoNetworkException;
import arch.Status;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.ArticlesRepository;

/**
 *
 * Refer to TasksPresenter.java
 * https://github.com/googlesamples/android-architecture/tree/todo-mvp/
 *
 * @author Weiyi Li on 3/4/18 | https://github.com/li2
 */
public class ArticlesPresenter implements ArticlesContract.Presenter {
    private static final String LOG_TAG = ArticlesPresenter.class.getSimpleName();

    @Inject
    ArticlesContract.View mView;
    // Could i put LifecycleOwner in Presenter? Does it violate the principle of MVP?
    @Inject
    LifecycleOwner mLifecycleOwner;
    @Inject
    ArticlesRepository mRepository;

    private List<Article> mArticles;

    @Inject
    public ArticlesPresenter() {
    }

    @Override
    public void start() {
        loadArticles();
    }

    @Override
    public void onUserRefresh() {
        loadArticles();
    }

    @Override
    public void onUserSelectArticle(Article article) {
        mView.showArticleDetailView(article);
    }

    @Override
    public int getArticlesCount() {
        return mArticles != null ? mArticles.size() : 0;
    }

    @Override
    public Article getArticle(int position) {
        return mArticles.get(position);
    }

    private void loadArticles() {
        mView.setLoadingIndicator(true);

        mRepository.loadArticles().observe(mLifecycleOwner, resource -> {
            Log.d(LOG_TAG, "loading status: " + resource.status + ", code " + resource.code);
            
            mView.setLoadingIndicator(resource.status == Status.LOADING);

            if (resource.data != null) {
                if (resource.data.size() > 0) {
                    mArticles = resource.data;
                    mView.showArticleList();
                } else {
                    mView.showNoArticlesView();
                }
            }

            if (resource.status == Status.ERROR) {
                if (resource.throwable instanceof NoNetworkException) {
                    mView.showNoNetworkError();
                }
            }
        });
    }
}
