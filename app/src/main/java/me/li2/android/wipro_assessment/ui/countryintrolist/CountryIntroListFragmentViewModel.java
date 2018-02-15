package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import me.li2.android.wipro_assessment.data.WiproRepository;
import me.li2.android.wipro_assessment.data.database.CountryIntroEntry;

/**
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class CountryIntroListFragmentViewModel extends ViewModel {

    private WiproRepository mRepository;
    private LiveData<List<CountryIntroEntry>> mCountryIntros;

    public CountryIntroListFragmentViewModel(WiproRepository repository) {
        mRepository = repository;
        mCountryIntros = mRepository.getAllCountryIntro();
    }

    public LiveData<List<CountryIntroEntry>> getAllCountryIntro() {
        return mCountryIntros;
    }

    public LiveData<List<CountryIntroEntry>> refreshAllCountryIntro() {
        return mRepository.refreshAllCountryIntro();
    }

    public String getCountryTitle() {
        return mRepository.getCountryTitle();
    }
}
