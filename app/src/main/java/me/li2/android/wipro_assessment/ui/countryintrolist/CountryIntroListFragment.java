package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.utils.InjectorUtils;
import me.li2.android.wipro_assessment.utils.NetworkUtils;
import me.li2.android.wipro_assessment.utils.NoNetworkException;

public class CountryIntroListFragment extends Fragment {
    private static final String LOG_TAG = CountryIntroListFragment.class.getSimpleName();
    private static final String BUNDLE_RECYCLER_POSITION = "recycler_position";

    private CountryIntroListAdapter mAdapter;
    private CountryIntroListFragmentViewModel mViewModel;

    @BindView(R.id.country_intro_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.country_intro_swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public CountryIntroListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CountryIntroListViewModelFactory factory = InjectorUtils.provideCountryListViewModelFactory(getContext());
        mViewModel = ViewModelProviders.of(this, factory).get(CountryIntroListFragmentViewModel.class);
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
        View view = inflater.inflate(R.layout.country_intro_list_fragment, container, false);
        ButterKnife.bind(this, view);

        final RecyclerView recyclerView = mRecyclerView;
        mAdapter = new CountryIntroListAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
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
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mConnectivityChangeReceiver, NetworkUtils.connectivityChangeFilter());
        loadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mConnectivityChangeReceiver);
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (!NetworkUtils.isConnected()) {
                showMessage(R.string.status_no_connect);
            } else {
                loadData();
            }
            mSwipeRefreshLayout.setRefreshing(false);
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

    private void loadData() {
        mViewModel.getCountryIntroList().observe(getActivity(), resource -> {
            Log.d(LOG_TAG, "loading status: " + resource.status + ", code " + resource.code);

            switch (resource.status) {
                case LOADING:
                    showMessage(R.string.status_loading);
                    break;

                case SUCCESS:
                    showMessage(R.string.status_success);
                    if (resource.data == null) {
                        showMessage(R.string.status_no_response);
                    }
                    break;

                case ERROR:
                    if (resource.throwable instanceof NoNetworkException) {
                        showMessage(R.string.status_no_connect);
                    }
                    break;
            }

            // update recycler view
            mAdapter.update(resource.data);

            // update actionbar title
            if (!TextUtils.isEmpty(mViewModel.getCountryTitle())) {
                ((CountryIntroListActivity)getActivity()).setTitle(mViewModel.getCountryTitle());
            }
        });
    }

    private void showMessage(int stringResId) {
        Snackbar.make(getView(), stringResId, Snackbar.LENGTH_LONG)
                .show();
    }
}
