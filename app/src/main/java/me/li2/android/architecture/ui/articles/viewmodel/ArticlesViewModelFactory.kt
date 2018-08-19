package me.li2.android.architecture.ui.articles.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.li2.android.architecture.data.repository.ArticlesRepository
import me.li2.android.architecture.ui.articles.view.ArticlesNavigator
import me.li2.android.architecture.utils.BaseResourceProvider
import javax.inject.Inject

/**
 * Created by weiyi on 24/7/18.
 * https://github.com/li2
 *
 * TODO why cannot inject directly in the ViewModel
 */
class ArticlesViewModelFactory @Inject
constructor() : ViewModelProvider.NewInstanceFactory() {

    @Inject
    lateinit var mRepository: ArticlesRepository

    @Inject
    lateinit var mNavigator: ArticlesNavigator

    @Inject
    lateinit var mResourceProvider: BaseResourceProvider

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticlesViewModel(mRepository, mResourceProvider, mNavigator) as T
    }
}
