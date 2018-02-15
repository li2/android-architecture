package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.li2.android.wipro_assessment.R;
import me.li2.android.wipro_assessment.data.database.CountryEntry;
import me.li2.android.wipro_assessment.data.network.WebServiceGenerator;
import me.li2.android.wipro_assessment.data.network.WiproWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryIntroListFragment extends Fragment {
    private static final String LOG_TAG = CountryIntroListFragment.class.getSimpleName();

    private CountryIntroListAdapter mAdapter;

    @BindView(R.id.country_intro_recycler_view)
    RecyclerView mRecyclerView;

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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchCountryIntros();
    }

    // only for testing purpose
    private void fetchCountryIntros() {
        WiproWebService service = WebServiceGenerator.createService(WiproWebService.class);
        service.getCountry().enqueue(new Callback<CountryEntry>() {
            @Override
            public void onResponse(Call<CountryEntry> call, Response<CountryEntry> response) {
                Log.d(LOG_TAG, "title: " + response.body().getTitle());
                mAdapter.update(response.body().getIntros());
            }

            @Override
            public void onFailure(Call<CountryEntry> call, Throwable t) {
                Log.e(LOG_TAG, "failed to get country intros : " + t.getMessage());
            }
        });
    }
}
