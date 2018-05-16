package me.li2.android.architecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.li2.android.architecture.ui.detail.ArticleDetailActivity;
import me.li2.android.architecture.ui.detail.ArticleDetailActivityModule;
import me.li2.android.architecture.ui.list.ArticleListActivity;
import me.li2.android.architecture.ui.list.ArticleListFragment;
import me.li2.android.architecture.ui.list.ArticleListFragmentModule;
import me.li2.android.architecture.ui.list.ArticlesScope;

/**
 * This is a given module to dagger.
 * We map ALL our activities here, then Dagger knows our activities in compile time.
 * Otherwise it causes IllegalArgumentException: No injector factory bound for Class
 *
 * @author Weiyi Li on 31/3/18 | https://github.com/li2
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = ArticleDetailActivityModule.class)
    abstract ArticleDetailActivity bindArticleDetailActivity();

    @ContributesAndroidInjector()
    abstract ArticleListActivity bindArticleListActivity();

    @ContributesAndroidInjector(modules = ArticleListFragmentModule.class)
    @ArticlesScope
    abstract ArticleListFragment bindArticleListFragment();
}
