package me.li2.android.wipro_assessment.utils;

import android.content.Context;

import me.li2.android.wipro_assessment.data.database.WiproDatabase;
import me.li2.android.wipro_assessment.data.network.WebServiceGenerator;
import me.li2.android.wipro_assessment.data.network.WiproWebService;
import me.li2.android.wipro_assessment.data.repository.WiproRepository;
import me.li2.android.wipro_assessment.ui.countryintrolist.CountryIntroListViewModelFactory;

/**
 * Created by weiyi on 16/02/2018.
 * https://github.com/li2
 */

public class InjectorUtils {

    public static WiproRepository provideRepository(Context context) {
        WiproDatabase database = WiproDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        WiproWebService wiproWebService = WebServiceGenerator.createService(WiproWebService.class);
        return WiproRepository.getInstance(context, database.countryIntroDao(), wiproWebService, executors);
    }

    public static CountryIntroListViewModelFactory provideCountryListViewModelFactory(Context context) {
        WiproRepository repository = provideRepository(context.getApplicationContext());
        return new CountryIntroListViewModelFactory(repository);
    }
}
