package me.li2.android.architecture.ui.articles.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arch.Resource;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.subjects.PublishSubject;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.data.repository.ArticlesRepository;
import me.li2.android.architecture.ui.articles.view.ArticlesNavigator;
import me.li2.android.architecture.utils.BaseResourceProvider;

/**
 * ViewModel to expose states for the list of articles view, and handle all user actions.
 *
 * - Ask data from repository {@link ArticlesViewModel#mRepository}, and then
 *     construct the source data to view data which contains UI state.
 *
 * - Expose streams for view to subscribe the UI state,
 *     such as {@link ArticlesViewModel#getUiModel()}, {@link ArticlesViewModel#getLoadingIndicatorVisibility()}
 *
 * - Expose public methods for view to handle user actions,
 *     such as force pull-refresh {@link ArticlesViewModel#forceUpdateArticles()}
 *
 * - For user action in the sub-view, such as click or check in the list view item,
 *      define {@link Action} and implement the action {@link ArticlesViewModel#handleArticleTaped(Article)}, and
 *      pass to sub-view model {@link ArticleItem#ArticleItem(Article, Action)}
 *
 * @author Weiyi Li on 13/7/18 | https://github.com/li2
 */
public class ArticlesViewModel extends ViewModel {

    private static final String TAG = ArticlesViewModel.class.getSimpleName();

    @NonNull
    @Inject
    ArticlesRepository mRepository;

    @NonNull
    @Inject
    ArticlesNavigator mNavigator;

    @NonNull
    @Inject
    BaseResourceProvider mResourceProvider;

    private final PublishSubject<Boolean> mLoadingIndicatorSubject;

    @NonNull
    private final PublishSubject<Integer> mSnackbarText;

    @Inject
    public ArticlesViewModel() {
        mLoadingIndicatorSubject = PublishSubject.create();
        mSnackbarText = PublishSubject.create();
    }

    /**
     * @return the model for the articles list screen.
     */
    @NonNull
    public Observable<ArticlesUiModel> getUiModel() {
        return getArticleItems()
                .doOnSubscribe(__ -> mLoadingIndicatorSubject.onNext(true))
                .doOnNext(__ -> mLoadingIndicatorSubject.onNext(false))
                .doOnError(__ -> mSnackbarText.onNext(R.string.loading_articles_error))
                .map(this::constructArticlesUiModel);
    }

    /**
     * @return a stream of string ids that should be displayed in the snackbar.
     */
    @NonNull
    public Observable<Integer> getSnackbarMessage() {
        return mSnackbarText;
    }

    /**
     * @return a stream that emits true if the progress indicator should be displayed, false otherwise.
     */
    @NonNull
    public Observable<Boolean> getLoadingIndicatorVisibility() {
        return mLoadingIndicatorSubject;
    }

    /**
     * Convert {@link Resource<List<>>} of {@link Article} (data model) to {@link ArticleItem} (view data model)
     * @return
     */
    private LiveData<Resource<List<ArticleItem>>> getArticleItems() {
        return mRepository.getArticles()
                .flatMap(list -> Observable.fromIterable(list)
                        .map(article -> constructArticleItem(article))
                        .toList()
                        .toObservable());
    }

    private ArticlesUiModel constructArticlesUiModel(List<ArticleItem> articleItems) {
        boolean isArticlesListVisible = !articleItems.isEmpty();
        boolean isNoArticlesViewVisible = !isArticlesListVisible;
        return new ArticlesUiModel(isArticlesListVisible, articleItems,
                isNoArticlesViewVisible, mResourceProvider.getString(R.string.no_articles_all));
    }

    private ArticleItem constructArticleItem(final Article article) {
        return new ArticleItem(article, view -> handleArticleTaped(article, view));
    }

    private void handleArticleTaped(Article article, View sharedElement) {
        mNavigator.openArticleDetails(article, sharedElement);
    }

    /**
     * Trigger a force update of the articles.
     */
    public Completable forceUpdateArticles() {
        mLoadingIndicatorSubject.onNext(true);
        // TODO
        return null;
    }
}
