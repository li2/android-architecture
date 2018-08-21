package me.li2.android.architecture.ui.articles.view

import android.support.v4.app.Fragment

import me.li2.android.architecture.ui.basic.BaseSingleFragmentActivity

class ArticlesActivity : BaseSingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return ArticlesFragment()
    }
}
