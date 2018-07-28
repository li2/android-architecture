package me.li2.android.architecture.ui.offers.view;

import android.support.v4.app.Fragment;

import me.li2.android.architecture.ui.basic.BaseSingleFragmentActivity;

public class OffersActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new OffersFragment();
    }
}
