package me.li2.android.architecture.ui.list;

import android.support.v4.app.Fragment;

import me.li2.android.architecture.ui.basic.SingleFragmentActivity;

public class ArticleListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ArticleListFragment();
    }
}
