package me.li2.android.architecture.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.DemoRepository;
import me.li2.android.architecture.utils.NoNetworkException;

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
    DemoRepository mRepository;

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

        mRepository.loadArticleList().observe(mLifecycleOwner, resource -> {
            Log.d(LOG_TAG, "loading status: " + resource.status + ", code " + resource.code);

            switch (resource.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    if (resource.data != null) {
                        // TODO bug need to be fixed: SUCCESS only relates to webservice query,
                        // if no internet, but has data in local, we cannot go to here.
                        mArticles = resource.data;
                        mView.setLoadingIndicator(false);
                        mView.showArticleList();
                    } else {
                        mView.showNoArticlesView();
                    }
                    break;

                case ERROR:
                    if (resource.throwable instanceof NoNetworkException) {
                        mView.showNoNetworkError();
                    }
                    break;
            }
        });
    }
}
