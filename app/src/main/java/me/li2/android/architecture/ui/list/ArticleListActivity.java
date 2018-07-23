package me.li2.android.architecture.ui.list;

import android.support.v4.app.Fragment;

import me.li2.android.architecture.ui.basic.BaseSingleFragmentActivity;

public class ArticleListActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ArticleListFragment();
    }
}
