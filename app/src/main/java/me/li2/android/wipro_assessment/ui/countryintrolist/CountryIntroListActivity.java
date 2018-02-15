package me.li2.android.wipro_assessment.ui.countryintrolist;

import android.support.v4.app.Fragment;

import me.li2.android.wipro_assessment.ui.basic.SingleFragmentActivity;

public class CountryIntroListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CountryIntroListFragment();
    }
}
