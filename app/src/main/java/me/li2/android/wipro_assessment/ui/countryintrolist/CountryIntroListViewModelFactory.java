package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import me.li2.android.wipro_assessment.data.repository.WiproRepository;

/**
 * The constructor that is automatically called by ViewModelProvider is the default one -
 * it takes no arguments. If you want to create a different constructor for a view model,
 * you'll need to make a view model provider factory.
 *
 * Created by weiyi on 04/02/2018.
 * https://github.com/li2
 */

public class CountryIntroListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final WiproRepository mRepository;

    public CountryIntroListViewModelFactory(WiproRepository repository) {
        mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CountryIntroListFragmentViewModel(mRepository);
    }
}
