package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import me.li2.android.wipro_assessment.data.network.Resource;
import me.li2.android.wipro_assessment.data.repository.WiproRepository;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class CountryIntroListFragmentViewModel extends ViewModel {

    private WiproRepository mRepository;
    private LiveData<Resource<List<CountryIntroEntry>>> mCountryIntros;

    public CountryIntroListFragmentViewModel(WiproRepository repository) {
        mRepository = repository;
        mCountryIntros = mRepository.loadCountryIntros();
    }

    public LiveData<Resource<List<CountryIntroEntry>>> getCountryIntroList() {
        return mCountryIntros;
    }

    public String getCountryTitle() {
        return mRepository.getCountryTitle();
    }
}
