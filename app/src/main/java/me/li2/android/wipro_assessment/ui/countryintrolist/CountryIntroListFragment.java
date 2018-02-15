package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.utils.InjectorUtils;

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
        CountryIntroListViewModelFactory factory = InjectorUtils.provideCountryListViewModelFactory(getContext());
        mViewModel = ViewModelProviders.of(this, factory).get(CountryIntroListFragmentViewModel.class);
        mViewModel.getAllCountryIntro().observe(this, allCountryIntro -> {
            mAdapter.update(allCountryIntro);
        });
    }

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mViewModel.refreshAllCountryIntro().observe(CountryIntroListFragment.this, allCountryIntro -> {
                mAdapter.update(allCountryIntro);
                mSwipeRefreshLayout.setRefreshing(false);
            });
        }
    };
}
