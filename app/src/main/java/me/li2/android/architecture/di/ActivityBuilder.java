package me.li2.android.architecture.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.li2.android.architecture.ui.detail.ArticleDetailActivity;
import me.li2.android.architecture.ui.detail.ArticleDetailActivityModule;

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
}
