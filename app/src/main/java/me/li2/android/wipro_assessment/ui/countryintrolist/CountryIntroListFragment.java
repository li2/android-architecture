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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.utils.InjectorUtils;
import me.li2.android.wipro_assessment.utils.InternetUtils;

public class CountryIntroListFragment extends Fragment {
    private static final String LOG_TAG = CountryIntroListFragment.class.getSimpleName();

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.country_intro_list_fragment, container, false);
        ButterKnife.bind(this, view);

        final RecyclerView recyclerView = mRecyclerView;
        mAdapter = new CountryIntroListAdapter(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(
                getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setScrollContainer(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mConnectivityChangeReceiver, InternetUtils.connectivityChangeFilter());
        checkConnectivity();
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
            if (checkConnectivity()) {
                loadData();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    private BroadcastReceiver mConnectivityChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (InternetUtils.isConnectivityChangeAction(intent.getAction())) {
                checkConnectivity();
            }
        }
    };

    private void loadData() {
        mViewModel.getCountryIntroList().observe(getActivity(), countryIntroList -> {
            Toast.makeText(getContext(), "loading status: " + countryIntroList.status, Toast.LENGTH_SHORT).show();

            // update recycler view
            mAdapter.update(countryIntroList.data);

            // update actionbar title
            ((CountryIntroListActivity)getActivity()).setTitle(mViewModel.getCountryTitle());
        });
    }

    private boolean checkConnectivity() {
        boolean isConnected = InternetUtils.isConnected(getContext());
        if (!isConnected) {
            Snackbar.make(getView(), R.string.internet_connection_enable_tip, Snackbar.LENGTH_LONG)
                    .show();
        }
        return isConnected;
    }
}
