package me.li2.android.architecture.ui.list;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import me.li2.android.architecture.R;
import me.li2.android.architecture.data.model.Article;
import me.li2.android.architecture.ui.basic.RecyclerViewMarginDecoration;
import me.li2.android.architecture.ui.detail.ArticleDetailActivity;
import me.li2.android.architecture.utils.NetworkUtils;

public class ArticleListFragment extends DaggerFragment implements ArticleSelectListener, ArticlesContract.View {
    private static final String LOG_TAG = ArticleListFragment.class.getSimpleName();
    private static final String BUNDLE_RECYCLER_POSITION = "recycler_position";

    @Inject
    ArticlesContract.Presenter mPresenter;

    @Inject
    ArticleListAdapter mAdapter;

    @BindView(R.id.article_list_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.article_list_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_RECYCLER_POSITION,
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_list_fragment, container, false);
        ButterKnife.bind(this, view);

        final RecyclerView recyclerView = mRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        // setup RecyclerView item margin
        int margin = (int)getResources().getDimension(R.dimen.default_margin);
        recyclerView.addItemDecoration(new RecyclerViewMarginDecoration(margin));
        // setup adapter
        recyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            // notebyweiyi: IllegalArgumentException: Invalid target position -1, scrollToPosition() not works
            int position = savedInstanceState.getInt(BUNDLE_RECYCLER_POSITION);
            if (position > 0) {
                mRecyclerView.smoothScrollToPosition(position);
            }
        }
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mConnectivityChangeReceiver, NetworkUtils.connectivityChangeFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mConnectivityChangeReceiver);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.loadArticles();
        }
    };

    private BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtils.isConnectivityChangeAction(intent.getAction())) {
                showMessage(!NetworkUtils.isConnected() ? R.string.status_no_connect : R.string.status_connected);
            }
        }
    };

    private void showMessage(int stringResId) {
        Snackbar.make(getView(), stringResId, Snackbar.LENGTH_LONG)
                .show();
    }

    // Delegate ViewHolder click event to Fragment, then we can use activity to implement transition animation.
    @Override
    public void onArticleSelect(Article article, View sharedView, String sharedName) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, sharedName);
        startActivity(ArticleDetailActivity.newIntent(getContext(), article.getId()), options.toBundle());
    }

    @Override
    public void showArticles(List<Article> articleList) {
        mAdapter.update(articleList);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void showLoadingArticlesSucceed() {
        showMessage(R.string.status_success);
    }

    @Override
    public void showNoArticles() {
        showMessage(R.string.status_no_response);
    }

    @Override
    public void showLoadingArticlesError() {

    }

    @Override
    public void showNoNetworkError() {
        showMessage(R.string.status_no_connect);
    }
}
