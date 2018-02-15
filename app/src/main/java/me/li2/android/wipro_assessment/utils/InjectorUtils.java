package me.li2.android.wipro_assessment.utils;

import android.content.Context;

import me.li2.android.wipro_assessment.data.WiproRepository;
import me.li2.android.wipro_assessment.data.database.WiproDatabase;
import me.li2.android.wipro_assessment.data.network.WiproNetworkDataSource;
import me.li2.android.wipro_assessment.ui.countryintrolist.CountryIntroListViewModelFactory;

/**
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class InjectorUtils {

    public static WiproRepository provideRepository(Context context) {
        WiproDatabase database = WiproDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        WiproNetworkDataSource networkDataSource = WiproNetworkDataSource.getInstance(context, executors);
        return WiproRepository.getInstance(context, database.countryIntroDao(), networkDataSource, executors);
    }

    public static WiproNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context);
        AppExecutors executors = AppExecutors.getInstance();
        return WiproNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static CountryIntroListViewModelFactory provideCountryListViewModelFactory(Context context) {
        WiproRepository repository = provideRepository(context.getApplicationContext());
        return new CountryIntroListViewModelFactory(repository);
    }
}
