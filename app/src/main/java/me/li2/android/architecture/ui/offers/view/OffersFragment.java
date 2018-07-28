package me.li2.android.architecture.ui.offers.view;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
import me.li2.android.architecture.R;
import me.li2.android.architecture.ui.basic.BaseFragment;
import me.li2.android.architecture.ui.offers.viewmodel.OfferItem;
import me.li2.android.architecture.ui.offers.viewmodel.OffersUiModel;
import me.li2.android.architecture.ui.offers.viewmodel.OffersViewModel;
import me.li2.android.architecture.ui.widget.RecyclerViewMarginDecoration;

public class OffersFragment extends BaseFragment {

    private static final String BUNDLE_RECYCLER_POSITION = "recycler_position";

    @BindView(R.id.offer_list_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.offer_list_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.no_offers_container)
    View mNoOffersView;

    @BindView(R.id.no_offers_title)
    TextView mNoOffersTitleView;

    private MenuItem mRegionMenuItem;

    @Inject
    OffersAdapter mAdapter;

    @Inject
    OffersViewModel mViewModel;


    public OffersFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayout() {
        return R.layout.offers_fragment;
    }

    @Override
    public void setupView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        setActionBarTitle(constructActionBarTile());
        setupOffersListView();
        setupSwipeRefreshLayout();
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

        mViewModel.getOffers().observe(this, offers -> {

        });
    }

    private void updateView(OffersUiModel uiModel) {
        if (uiModel == null) {
            return;
        }
        int offersListVisibility = uiModel.isOffersListVisible() ? View.VISIBLE : View.GONE;
        int noOffersViewVisibility = uiModel.isNoOffersViewVisible() ? View.VISIBLE : View.GONE;
        mRecyclerView.setVisibility(offersListVisibility);
        mNoOffersView.setVisibility(noOffersViewVisibility);

        if (uiModel.isOffersListVisible()) {
            showOffersList(uiModel.getItemList());
        }
        if (uiModel.isNoOffersViewVisible()) {
            showNoOffersView(uiModel.getNoOffersHint());
        }
    }

    private void setupOffersListView() {
        final RecyclerView recyclerView = mRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setScrollContainer(false);
        // notebyweiyi: set true to allows your Toolbar and other views (such as tabs provided by TabLayout) to react to scroll events
        // [CoordinatorLayout and the app bar](https://android-developers.googleblog.com/2015/05/android-design-support-library.html)
        recyclerView.setNestedScrollingEnabled(true);
        // setup RecyclerView item margin
        int margin = (int)getResources().getDimension(R.dimen.default_margin);
        recyclerView.addItemDecoration(new RecyclerViewMarginDecoration(margin));
        // setup adapter
        recyclerView.setAdapter(mAdapter);
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> mViewModel.forceUpdateOffers());
    }

    private void setLoadingIndicatorVisibility(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    private void showOffersList(List<OfferItem> offers) {
        mAdapter.refreshData(offers);
    }

    private void showNoOffersView(String hint) {
        mNoOffersTitleView.setText(hint);
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

    private SpannableStringBuilder constructActionBarTile() {
        String title = "LUXURY\nESCAPES";
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
        ssBuilder.setSpan(
                new StyleSpan(Typeface.BOLD),
                title.indexOf("ESCAPES"),
                title.indexOf("ESCAPES") + String.valueOf("ESCAPES").length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ssBuilder;
    }
}
