package me.li2.android.architecture.ui.list;

import java.util.List;

import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.ui.basic.BasePresenter;
import me.li2.android.architecture.ui.basic.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 * How to define the method in the interface?
 *
 * Refer to TasksContract.java
 * https://github.com/googlesamples/android-architecture/tree/todo-mvp/
 *
 * @author Weiyi Li on 2/4/18 | https://github.com/li2
 */

public class ArticlesContract {

    interface View extends BaseView<Presenter> {

        void showArticles(List<Article> articleList);

        void setLoadingIndicator(boolean active);

        void showLoadingArticlesSucceed();

        void showNoArticles();

        void showLoadingArticlesError();

        void showNoNetworkError();
    }

    interface Presenter extends BasePresenter {

        void loadArticles();
    }
}
