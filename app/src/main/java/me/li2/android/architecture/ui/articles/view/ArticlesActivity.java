package me.li2.android.architecture.ui.articles.view;

import android.support.v4.app.Fragment;

import me.li2.android.architecture.ui.basic.BaseSingleFragmentActivity;

public class ArticlesActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ArticlesFragment();
    }
}
