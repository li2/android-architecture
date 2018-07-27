package me.li2.android.architecture.ui.offers.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;
import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.ui.offers.viewmodel.OffersUiModel;
import me.li2.android.architecture.ui.offers.viewmodel.OffersViewModel;
import me.li2.android.architecture.ui.widget.RecyclerViewMarginDecoration;

public class OffersFragment extends DaggerFragment {

    private static final String BUNDLE_RECYCLER_POSITION = "recycler_position";

    @BindView(R.id.article_list_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.article_list_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.no_articles_container)
    View mNoArticlesView;

    @BindView(R.id.no_articles_title)
    TextView mNoArticlesTitleView;

    private MenuItem mRegionMenuItem;

    @Inject
    OffersAdapter mAdapter;

    @Inject
    OffersViewModel mViewModel;


    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.offers_fragment, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
        setupArticlesListView();
        setupSwipeRefreshLayout();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_RECYCLER_POSITION,
                ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
    }

    @Override
    public void onResume() {
        super.onResume();

        mViewModel.getUiModel().observe(this, uiModel -> updateView(uiModel));

        mViewModel.getLoadingIndicatorVisibility().observe(this, visible -> setLoadingIndicatorVisibility(visible));

        mViewModel.getSnackbarMessage().observe(this, message -> showSnackBar(message));
    }

    private void updateView(OffersUiModel uiModel) {
        if (uiModel == null) {
            return;
        }
        int articlesListVisibility = uiModel.isArticlesListVisible() ? View.VISIBLE : View.GONE;
        int noArticlesViewVisibility = uiModel.isNoArticlesViewVisible() ? View.VISIBLE : View.GONE;
        mRecyclerView.setVisibility(articlesListVisibility);
        mNoArticlesView.setVisibility(noArticlesViewVisibility);

        if (uiModel.isArticlesListVisible()) {
            showArticlesList(uiModel.getItemList());
        }
        if (uiModel.isNoArticlesViewVisible()) {
            showNoArticlesView(uiModel.getNoArticlesHint());
        }
    }

    private void setupArticlesListView() {
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
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> mViewModel.forceUpdateArticles());
    }

    private void setLoadingIndicatorVisibility(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    private void showArticlesList(List<OfferItem> articles) {
        mAdapter.refreshData(articles);
    }

    private void showNoArticlesView(String hint) {
        mNoArticlesTitleView.setText(hint);
    }

    private void showSnackBar(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_options, menu);
        mRegionMenuItem = menu.findItem(R.id.main_option_region);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_option_phone:
                break;

            case R.id.main_option_account:
                break;

            case R.id.main_option_region:
                showRegionFilteringPopUpMenu();
                break;
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void showRegionFilteringPopUpMenu() {
        View anchorView = getActivity().findViewById(R.id.main_option_region);
        PopupMenu popup = new PopupMenu(getContext(), anchorView);
        popup.getMenuInflater().inflate(R.menu.region, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            mRegionMenuItem.setIcon(item.getIcon());
            switch (item.getItemId()) {
                case R.id.region_menu_cn:
                    break;

                case R.id.region_menu_au:
                default:
                    break;
            }
            return true;
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), (MenuBuilder) popup.getMenu(), anchorView);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }
}
