package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.li2.android.wipro_assessment.data.database.CountryEntry;
import me.li2.android.wipro_assessment.data.network.WebServiceGenerator;
import me.li2.android.wipro_assessment.data.network.WiproWebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryIntroListActivity extends AppCompatActivity {
    private static final String LOG_TAG = CountryIntroListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingAccounts();
    }

    // only for testing purpose
    private void loadingAccounts() {
        WiproWebService service = WebServiceGenerator.createService(WiproWebService.class);
        service.getCountry().enqueue(new Callback<CountryEntry>() {
            @Override
            public void onResponse(Call<CountryEntry> call, Response<CountryEntry> response) {
                Log.d(LOG_TAG, "title: " + response.body().getTitle());
            }

            @Override
            public void onFailure(Call<CountryEntry> call, Throwable t) {
                Log.e(LOG_TAG, "failed to get country intros : " + t.getMessage());
            }
        });
    }
}
