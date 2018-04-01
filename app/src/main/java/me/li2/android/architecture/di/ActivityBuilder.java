package me.li2.android.architecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.li2.android.architecture.ui.detail.ArticleDetailActivity;
import me.li2.android.architecture.ui.detail.ArticleDetailActivityModule;
import me.li2.android.architecture.ui.list.ArticleListActivity;
import me.li2.android.architecture.ui.list.ArticleListFragment;
import me.li2.android.architecture.ui.list.ArticleListFragmentModule;

/**
 * This is a given module to dagger.
 * We map ALL our activities here, then Dagger knows our activities in compile time.
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ArticleDetailActivityModule.class)
    abstract ArticleDetailActivity bindArticleDetailActivity();

    /**
     * ArticleListFragment is hosted by ArticleListActivity, so we need to
     * install the fragment's module to this activity.
     */
    @ContributesAndroidInjector(modules = { ArticleListActivityHostedFragment.class })
    abstract ArticleListActivity bindArticleListActivity();

    /**
     * The reason to create this class is that this activity maybe host many fragments.
     */
    @Module
    abstract class ArticleListActivityHostedFragment {

        @ContributesAndroidInjector(modules = ArticleListFragmentModule.class)
        abstract ArticleListFragment bindArticleListFragment();

        // another fragment ...
    }
}
